package com.revature.pokecare.dao;

import com.revature.pokecare.models.Trainer;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;

import java.util.List;

import static com.revature.pokecare.util.Connection.session;

public class TrainerDatabase {

    //INSERT METHOD
    public boolean insertTrainer(Trainer pkTrainer) {
        Transaction tx = session.beginTransaction();
        session.save(pkTrainer);
        tx.commit();
        return true;
    }

    //SELECT METHODS
    public Trainer findTrainerById(int pkTrainer_id) {
        return session.get(Trainer.class, pkTrainer_id);
    }

    public List<Trainer> getAllTrainers() {
        TypedQuery<Trainer> query = session.createQuery("FROM poketrainer", Trainer.class);
        return query.getResultList();
    }

    //UPDATE METHOD
    public boolean updateTrainer(Trainer pkTrainer) {
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(pkTrainer);
        tx.commit();
        return true;
    }

    //DELETE METHOD
    public boolean deleteTrainer(int pkTrainer_id) {
        Query query = session.createQuery("DELETE poketrainer WHERE id = " + pkTrainer_id);
        int result = query.executeUpdate();

        return result == 1;
    }
}