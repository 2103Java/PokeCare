package com.revature.pokecare.dao;

import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.models.PokemonTrainer;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;

import java.util.List;

import static com.revature.pokecare.util.Connection.session;

public class PokemonTrainerDAO {

    //INSERT METHOD
    public boolean insertTrainer(PokemonTrainer pkTrainer){
        Transaction tx = session.beginTransaction();
        session.save(pkTrainer);
        tx.commit();
        return true;
    }

    //SELECT METHODS
    public PokemonTrainer findTrainerById(int pkTrainer_id){
        return session.get(PokemonTrainer.class, pkTrainer_id);
    }
    public List<PokemonTrainer> getAllTrainers(){
        TypedQuery<PokemonTrainer> query = session.createQuery("FROM poketrainer", PokemonTrainer.class);
        return (List<PokemonTrainer>) query.getResultList();
    }

    //UPDATE METHOD
    public boolean updateTrainer(PokemonTrainer pkTrainer){
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(pkTrainer);
        tx.commit();
        return true;
    }

    //DELETE METHOD
    public boolean deleteTrainer(int pkTrainer_id){
        Query query = session.createQuery("DELETE poketrainer WHERE id = " + pkTrainer_id);
        int result = query.executeUpdate();

        return result == 1;
    }
}
