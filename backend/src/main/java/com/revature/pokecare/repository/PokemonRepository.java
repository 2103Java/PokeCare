package com.revature.pokecare.repository;

import com.revature.pokecare.models.Pokemon;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PokemonRepository {
    private final SessionFactory sessionFactory;
    private static final Logger loggy = Logger.getLogger(PokemonRepository.class);

    @Autowired
    public PokemonRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //INSERT METHOD
    public boolean insertPokemon(Pokemon pk) {
        Transaction tx = null;
        try (Session nSession = sessionFactory.openSession()) {

            tx = nSession.beginTransaction();
            nSession.save(pk);
            nSession.flush();

            tx.commit();
        } catch (RuntimeException e) {
            loggy.warn(e);
            if (tx != null) { tx.rollback(); }
            return false;
        }
        return true;
    }

    //SELECT METHODS
    public Pokemon findPokemonById(int pkId) {
        Pokemon pk = null;
        Session find = sessionFactory.openSession();
        pk = find.get(Pokemon.class, pkId);
        System.out.println(pk);
        find.close();
        return pk;
    }

    public List<Pokemon> findPokemonByTrainerId(int trainer_id) {
        Session session = sessionFactory.openSession();
        TypedQuery<Pokemon> query = session.createQuery("FROM Pokemon WHERE trainer_id = " + trainer_id, Pokemon.class);
        List<Pokemon> pkList = query.getResultList();
        session.close();
        return pkList;

    }

    public boolean updatePokemon(Pokemon pk) {
        loggy.info("Updating pokemon: " + pk.getId());
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(pk);
        tx.commit();
        session.close();
        return true;
    }

    public boolean deletePokemon(Pokemon pokemon) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tran = session.beginTransaction();

            session.delete(pokemon);
            tran.commit();
        }
        return true;
    }
}