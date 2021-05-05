package com.revature.pokecare.dao;

import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.util.Connection;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;

import java.util.List;

import static com.revature.pokecare.util.Connection.session;

public class PokemonDAO {

    //INSERT METHOD
    public boolean insertPokemon(Pokemon pk){
        Transaction tx = session.beginTransaction();
        session.save(pk);
        tx.commit();
        return true;
    }

    //SELECT METHODS
    public Pokemon findPokemonById(int pkId){
        return session.get(Pokemon.class, pkId);
    }
    public List<Pokemon> findPokemonByTrainerId(int trainer_id){
        TypedQuery<Pokemon> query = session.createQuery("FROM pokemon WHERE trainer_id = " + trainer_id, Pokemon.class);
        return (List<Pokemon>) query.getResultList();

    }

    //UPDATE METHOD
    public boolean updatePokemon(Pokemon pk){
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(pk);
        tx.commit();
        return true;
    }

    //DELETE METHOD
    public boolean deletePokemon(int pkId){
        Query query = session.createQuery("DELETE pokemon WHERE id = " + pkId);
        int result = query.executeUpdate();

        return result == 1;
    }


}
