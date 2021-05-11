package com.revature.pokecare.controllers;

import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService ps;


    @Autowired
    public PokemonController(PokemonService ps) {
        this.ps = ps;
    }

    //get a pokemon from the DB based on their unique id assigned by the DB
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Pokemon findMyPokemon(@PathVariable("id") int id, HttpSession session) {
        if(session.getAttribute("PokeTrainer") != null){
            return ps.findMyPokemon(id);
        }
        return null;
    }
    //Training
    //I don't know what the front end wants back from this? Please change as needed.
    @RequestMapping(value = "/training", method = RequestMethod.PUT)
    public ResponseEntity<String> trainPokemon(@RequestBody Pokemon pk, HttpSession session){
    	if(session.getAttribute("PokeTrainer") != null) {
    		ps.trainPokemon(pk);
    		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    	}
    	
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
    }
    //Feed your pokemon!
    @RequestMapping(value = "/feed", method = RequestMethod.PUT)
    public void feedPokemon(@RequestBody Pokemon pk, HttpSession session) {
    	if(session.getAttribute("PokeTrainer") != null) {
    		ps.feedPokemon(pk);
    	}
    }
    
    //Play with your pokemon
    @RequestMapping(value = "/play", method = RequestMethod.PUT)
    public void playWithPokemon(@RequestBody Pokemon pk, HttpSession session) {
    	if(session.getAttribute("PokeTrainer") != null) {
    		ps.playWithPokemon(pk);
    	}
    }
    

    //delete a pokemon based on their unique ID
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int id, HttpSession session) {
        if (session.getAttribute("PokeTrainer") != null) {
            boolean pkDeleted = ps.deletePokemon(id);
            if (pkDeleted) {
                return new ResponseEntity<String>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return null;
    }

    //use a post method call to update pokemon
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> postPokemon(@RequestBody Pokemon pk, HttpSession session){
        if(session.getAttribute("PokeTrainer") != null) {
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
    //Easy! We get our trainer ID from the session and throw it in the constructor. I hope this works!
    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public void newPokemon(@RequestBody Trainer trainer, HttpSession session){
        if(session.getAttribute("PokeTrainer") != null){
            Pokemon newRandom = ps.getNewPokemon(trainer.getId());
        }

    }
}
