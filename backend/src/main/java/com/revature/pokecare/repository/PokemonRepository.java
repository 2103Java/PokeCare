package com.revature.pokecare.repository;

import com.revature.pokecare.models.Pokemon;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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
        Pokemon pk = null;
        Session find = sf.openSession();
        pk = find.get(Pokemon.class, pkId);
        System.out.println(pk);
        find.close();
        return pk;
    }
    public List<Pokemon> findPokemonByTrainerId(int trainer_id){
        Session session = sf.openSession();
        TypedQuery<Pokemon> query = session.createQuery("FROM pokemon WHERE trainer_id = " + trainer_id, Pokemon.class);
        List<Pokemon> pkList = query.getResultList();
        session.close();
        return pkList;

    }

    //UPDATE METHOD
    public boolean updatePokemon(Pokemon pk){
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(pk);
        tx.commit();
        session.close();
        return true;
    }

    //DELETE METHOD
    public boolean deletePokemon(int pkId){
        Session session = sf.openSession();
        Query query = session.createQuery("DELETE pokemon WHERE id = " + pkId);
        int result = query.executeUpdate();
        session.close();

        return result == 1;
    }
}