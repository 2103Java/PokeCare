package com.revature.pokecare.repository;

import com.revature.pokecare.models.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainerRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TrainerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public boolean insertTrainer(Trainer trainer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tran = session.beginTransaction();

            session.save(trainer);
            session.flush();
            tran.commit();
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    //SELECT METHODS
    public Trainer findTrainerById(int trainerId) {
        Session find = sessionFactory.openSession();
        Trainer trainer = find.get(Trainer.class, trainerId);

        find.close();

        return trainer;
    }

    public Trainer findTrainerByUsername(String username) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteria = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> query = criteria.createQuery(Trainer.class);
        Root<Trainer> trainers = query.from(Trainer.class);

        query.select(trainers).where(criteria.or(criteria.equal(trainers.get("username"), username), criteria.equal(trainers.get("email"), username)));

        Trainer trainer = session.createQuery(query).getSingleResult();

        session.close();

        return trainer;
    }

    public List<Trainer> getAllTrainers() {
        Session session = sessionFactory.openSession();
        TypedQuery<Trainer> query = session.createQuery("from poketrainer", Trainer.class);
        List<Trainer> trList = query.getResultList();

        session.close();
        return trList;
    }

    //UPDATE METHOD
    public boolean updateTrainer(Trainer trainer) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        session.saveOrUpdate(trainer);
        tx.commit();
        session.close();
        return true;
    }

    //DELETE METHOD
    public boolean deleteTrainer(int trainerId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("DELETE poketrainer WHERE id = " + trainerId);
        int result = query.executeUpdate();

        session.close();

        return result == 1;
    }

    public List<Trainer> findMyFriends(Trainer trainer) {
        Session session = sessionFactory.openSession();
        String sql = "SELECT * FROM poketrainer inner join friends on (poketrainer.id = friender and "+trainer.getId()+"= friendee and friends.status = 'ACCEPTED') where poketrainer.id != " + trainer.getId();
        String sql2 = "SELECT * FROM poketrainer inner join friends on (poketrainer.id = friendee and "+trainer.getId()+"= friender and friends.status = 'ACCEPTED') where poketrainer.id != " + trainer.getId();

        TypedQuery<Trainer> query = session.createNativeQuery(sql, Trainer.class);
        List<Trainer> fAddedByMe = query.getResultList();

        TypedQuery<Trainer> query2 = session.createNativeQuery(sql2, Trainer.class);
        List<Trainer> fThatAddedMe = query2.getResultList();

        List<Trainer> resultList = new ArrayList<Trainer>();
        resultList.addAll(fAddedByMe);
        resultList.addAll(fThatAddedMe);

        session.close();
        return resultList;
    }

    public List<Trainer> myFriendRequests(Trainer trainer){
        Session session = sessionFactory.openSession();
        String sql = "SELECT * FROM poketrainer inner join friends on (poketrainer.id = friender and "+trainer.getId()+"= friendee and friends.status = 'PENDING') where poketrainer.id != " + trainer.getId();
        TypedQuery<Trainer> query = session.createNativeQuery(sql, Trainer.class);
        List<Trainer> resultList = query.getResultList();
        session.close();
        return resultList;
    }

    public boolean sendFriendRequest(Trainer friender, Trainer friendee) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createNativeQuery("INSERT INTO friends (friender,friendee,status) values ("+friender.getId()+","+friendee.getId()+",'PENDING')");

        int result = 0;
        try {
            result = query.executeUpdate();
        }
        catch(PersistenceException p){
            p.printStackTrace();
        }
        tx.commit();
        session.close();

        if (result != 0) {
            return true;
        }
        return false;
    }

    public void processFriendRequest(Trainer friender, Trainer friendee, String process){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        String sql = "";

        if(process.compareTo("ACCEPTED") == 0) sql = "UPDATE friends SET status = '"+process+"' WHERE friender = "+friender.getId()+" AND friendee = "+ friendee.getId();
        else sql = "DELETE FROM friends WHERE friender = "+friender.getId()+" AND friendee = "+friendee.getId();

        Query query = session.createNativeQuery(sql);

        try {
            query.executeUpdate();
        }
        catch(PersistenceException p){
            p.printStackTrace();
        }
        tx.commit();
        session.close();

    }
}