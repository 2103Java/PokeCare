package com.revature.pokecare.repository;

import com.revature.pokecare.models.Pokemon;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository("PokemonDatabase")
public class PokemonRepository {

    @Autowired
    private SessionFactory sf;

    //INSERT METHOD
    public boolean insertPokemon(Pokemon pk){
        Transaction tx = null;
        try (Session nSession = sf.openSession()) {

            tx = nSession.beginTransaction();
            nSession.save(pk);
            nSession.flush();

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            return false;
        }
        return true;
    }

    //SELECT METHODS
    public Pokemon findPokemonById(int pkId){
        return sf.getCurrentSession().get(Pokemon.class, pkId);
    }
    public List<Pokemon> findPokemonByTrainerId(int trainer_id){
        TypedQuery<Pokemon> query = sf.getCurrentSession().createQuery("FROM pokemon WHERE trainer_id = " + trainer_id, Pokemon.class);
        return (List<Pokemon>) query.getResultList();

    }

    //UPDATE METHOD
    public boolean updatePokemon(Pokemon pk){
        Transaction tx = sf.getCurrentSession().beginTransaction();
        sf.getCurrentSession().saveOrUpdate(pk);
        tx.commit();
        return true;
    }

    //DELETE METHOD
    public boolean deletePokemon(int pkId){
        Query query = sf.getCurrentSession().createQuery("DELETE pokemon WHERE id = " + pkId);
        int result = query.executeUpdate();

        return result == 1;
    }
}