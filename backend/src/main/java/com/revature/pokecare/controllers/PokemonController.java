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
@RequestMapping("/api/pokemon")
public class PokemonController {
    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PutMapping(value = "/training")
    public ResponseEntity<String> trainPokemon(@RequestBody Pokemon pokemon, @PathVariable int type, HttpSession session) {
        if (session.getAttribute("PokeTrainer") != null) {
            pokemonService.trainPokemon(pokemon, type);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping(value = "/feed/{id}")
    public ResponseEntity<?> feedPokemon(@PathVariable int id, HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        if (trainer != null && pokemonService.feedPokemon(trainer, id)) {
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

    @PutMapping(value = "/play")
    public void playWithPokemon(@RequestBody Pokemon pokemon, HttpSession session) {
        if (session.getAttribute("PokeTrainer") != null) {
            pokemonService.playWithPokemon(pokemon);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> returnPokemon(@PathVariable int id, HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        if (trainer != null) {
            int money = pokemonService.returnPokemon(trainer, id);

            if (money > 0) {
                return new ResponseEntity<>(money, HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
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