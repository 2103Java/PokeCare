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
    public Trainer findTrainerById(int pkTrainer_id) {
        return sf.getCurrentSession().get(Trainer.class, pkTrainer_id);
    }

    public Trainer findTrainerByUsername(String username) {
        Session session = sf.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Trainer> cq = cb.createQuery(Trainer.class);
        Root<Trainer> root = cq.from(Trainer.class);

        cq.select(root).where(cb.equal(root.get("username"), username));

        Trainer trainer = session.createQuery(cq).getSingleResult();

        session.close();

        return trainer;
    }

    public List<Trainer> getAllTrainers() {
        TypedQuery<Trainer> query = sf.openSession().createQuery("FROM poketrainer", Trainer.class);
        List<Trainer> trList = query.getResultList();
        sf.getCurrentSession().close();
        return trList;
    }

    //UPDATE METHOD
    public boolean updateTrainer(Trainer pkTrainer) {
        Transaction tx = sf.getCurrentSession().beginTransaction();
        sf.getCurrentSession().saveOrUpdate(pkTrainer);
        tx.commit();
        return true;
    }

    //DELETE METHOD
    public boolean deleteTrainer(int pkTrainer_id) {
        Query query = sf.openSession().createQuery("DELETE poketrainer WHERE id = " + pkTrainer_id);
        int result = query.executeUpdate();
        sf.getCurrentSession().close();

        return result == 1;
    }
}