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

@Repository("TrainerDatabase")
public class TrainerRepository {

    @Autowired
    private SessionFactory sf;

    @Transactional
    public boolean insertTrainer(Trainer pkTrainer) {
        try (Session session = sf.openSession()) {
            Transaction tran = session.beginTransaction();

            session.save(pkTrainer);
            session.flush();
            tran.commit();
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    //SELECT METHODS
    public Trainer findTrainerById(int pkTrainer_id)
    {
        Trainer pkTrainer = null;
        Session find = sf.openSession();
        pkTrainer = find.get(Trainer.class, pkTrainer_id);
        find.close();
        return pkTrainer;
    }

    public Trainer findTrainerByUsername(String username) {
        Session session = sf.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> cq = cb.createQuery(Trainer.class);
        Root<Trainer> root = cq.from(Trainer.class);

        cq.select(root).where(cb.equal(root.get("username"), username)).where(cb.equal(root.get("email"), username));

        Trainer trainer = session.createQuery(cq).getSingleResult();

        session.close();

        return trainer;
    }

    public List<Trainer> getAllTrainers() {
        Session session = sf.openSession();
        TypedQuery<Trainer> query = session.createQuery("FROM poketrainer", Trainer.class);
        List<Trainer> trList = query.getResultList();
        session.close();
        return trList;
    }

    //UPDATE METHOD
    public boolean updateTrainer(Trainer pkTrainer) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(pkTrainer);
        tx.commit();
        session.close();
        return true;
    }

    //DELETE METHOD
    public boolean deleteTrainer(int pkTrainer_id) {
        Session session = sf.openSession();
        Query query = session.createQuery("DELETE poketrainer WHERE id = " + pkTrainer_id);
        int result = query.executeUpdate();
        session.close();

        return result == 1;
    }
}