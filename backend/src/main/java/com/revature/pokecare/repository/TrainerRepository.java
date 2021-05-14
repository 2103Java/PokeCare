package com.revature.pokecare.repository;

import com.revature.pokecare.models.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        TypedQuery<Trainer> query = session.createQuery("FROM poketrainer", Trainer.class);
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
}