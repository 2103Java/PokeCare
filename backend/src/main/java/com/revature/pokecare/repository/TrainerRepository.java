package com.revature.pokecare.repository;

import com.revature.pokecare.models.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository("TrainerDatabase")
public class TrainerRepository {

    @Autowired
    private SessionFactory sf;

    //INSERT METHOD
    public boolean insertTrainer(Trainer pkTrainer) {
        Transaction tx = null;
        try (Session nSession = sf.openSession()) {

            tx = nSession.beginTransaction();
            nSession.save(pkTrainer);
            nSession.flush();

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            return false;
        }

        return true;

    }

    //SELECT METHODS
    public Trainer findTrainerById(int pkTrainer_id) {
        return sf.getCurrentSession().get(Trainer.class, pkTrainer_id);
    }

    public Trainer findTrainerByUsername(String pkTrainer_username) {
        return sf.getCurrentSession().get(Trainer.class, pkTrainer_username);
    }

    public List<Trainer> getAllTrainers() {
        TypedQuery<Trainer> query = sf.getCurrentSession().createQuery("FROM poketrainer", Trainer.class);
        return query.getResultList();
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
        Query query = sf.getCurrentSession().createQuery("DELETE poketrainer WHERE id = " + pkTrainer_id);
        int result = query.executeUpdate();

        return result == 1;
    }
}