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
    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    //get a pokemon from the DB based on their unique id assigned by the DB
    @GetMapping(value = "/{id}")
    public @ResponseBody
    Pokemon findMyPokemon(@PathVariable("id") int id, HttpSession session) {
        if (session.getAttribute("PokeTrainer") != null) {
            return pokemonService.findMyPokemon(id);
        }
        return null;
    }

    //Training
    //I don't know what the front end wants back from this? Please change as needed.
    @PutMapping(value = "/training")
    public ResponseEntity<String> trainPokemon(@RequestBody Pokemon pokemon, @PathVariable int type, HttpSession session) {
        if (session.getAttribute("PokeTrainer") != null) {
            pokemonService.trainPokemon(pokemon, type);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    //Feed your pokemon!
    @PutMapping(value = "/feed")
    public ResponseEntity<String> feedPokemon(@RequestBody Pokemon pokemon, HttpSession session) {
        if (session.getAttribute("PokeTrainer") != null) {
            pokemonService.feedPokemon(pokemon);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    @PutMapping(value = "/rest")
    public ResponseEntity<String> restPokemon(@RequestBody Pokemon pokemon, HttpSession session) {
        if (session.getAttribute("PokeTrainer") != null) {
            pokemonService.restPokemon(pokemon);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    //Play with your pokemon
    @PutMapping(value = "/play")
    public void playWithPokemon(@RequestBody Pokemon pokemon, HttpSession session) {
        if (session.getAttribute("PokeTrainer") != null) {
            pokemonService.playWithPokemon(pokemon);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> returnPokemon(@PathVariable("id") int id, HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        if (trainer != null) {
            int money = pokemonService.returnPokemon(trainer, id);

            if (money > 0) {
                return new ResponseEntity<>(money, HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<String> postPokemon(@RequestBody Pokemon pokemon, HttpSession session) {
        if (session.getAttribute("PokeTrainer") != null) {
            if (pokemonService.updatePokemon(pokemon)) {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return null;
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Pokemon> newPokemon(HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        if (trainer == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Pokemon newRandom = pokemonService.getNewPokemon(trainer);

        return new ResponseEntity<>(newRandom, HttpStatus.OK);
    }
}