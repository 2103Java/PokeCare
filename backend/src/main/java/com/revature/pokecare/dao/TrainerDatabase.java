package com.revature.pokecare.dao;

import com.revature.pokecare.models.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.List;

@Repository("TrainerDatabase")
@Transactional
public class TrainerDatabase {

    @Autowired
    private SessionFactory sf;

    //INSERT METHOD
    public Boolean insertTrainer(Trainer pkTrainer) {
        Session newSession = sf.openSession();
        Transaction tx = newSession.beginTransaction();

        System.out.println("Inserting a new trainer");

        newSession.save(pkTrainer);
        tx.commit();
        newSession.close();
        return true;

    }

    //SELECT METHODS
    public Trainer findTrainerById(int pkTrainer_id) {
        return sf.getCurrentSession().get(Trainer.class, pkTrainer_id);
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