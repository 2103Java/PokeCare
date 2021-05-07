package com.revature.pokecare.controllers;

import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.service.PokemonService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pokemon")
public class PokemonController {

    private final SessionFactory sessionFactory;

    private final PokemonService ps;


    @Autowired
    public PokemonController(SessionFactory sessionFactory, PokemonService ps) {
        this.sessionFactory = sessionFactory;
        this.ps = ps;
    }

    //get a pokemon from the DB based on their unique id assigned by the DB
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Pokemon findMyPokemon(@PathVariable("id") int id) {
        if(sessionFactory.getCurrentSession().isOpen()) return ps.findMyPokemon(id);
        return null;
    }

    //delete a pokemon based on their unique ID
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int id) {
        if (sessionFactory.getCurrentSession().isOpen()) {
            boolean pkDeleted = ps.deletePokemon(id);
            if (pkDeleted) {
                sessionFactory.getCurrentSession().close();
                return new ResponseEntity<String>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return null;
    }

    //use a post method call to update pokemon
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> postPokemon(@RequestBody Pokemon pk){
        if(sessionFactory.getCurrentSession().isOpen()) {
            boolean update = ps.updatePokemon(pk);
            if(update){
                return new ResponseEntity<String>(HttpStatus.ACCEPTED);
            }
            else{
                return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return null;
    }


    //Unsure how we actually associate the new pk with current session trainer
    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public void newPokemon(){
        if(sessionFactory.getCurrentSession().isOpen()){
            Pokemon newRandom = ps.getNewPokemon();
        }

    }
}
