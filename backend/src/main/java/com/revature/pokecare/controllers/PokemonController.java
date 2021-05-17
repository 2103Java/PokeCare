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

    @PutMapping(value = "/train/{id}/{type}")
    public ResponseEntity<?> trainPokemon(@PathVariable int id, @PathVariable int type, HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        if (trainer != null && pokemonService.trainPokemon(trainer, id, type)) {
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

    @PutMapping(value = "/rest/{id}/{fatigue}")
    public ResponseEntity<?> restPokemon(@PathVariable int id, @PathVariable int fatigue, HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        if (trainer != null && pokemonService.restPokemon(trainer, id, fatigue)) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping(value = "/play/{id}")
    public ResponseEntity<Integer> playWithPokemon(@PathVariable int id, HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        if (trainer != null) {
            int hap = pokemonService.playWithPokemon(trainer, id);

            if (hap > 0) {
                return new ResponseEntity<>(hap, HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> returnPokemon(@PathVariable int id, HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        if (trainer != null) {
            int money = pokemonService.returnPokemon(trainer, id);

            return new ResponseEntity<>(money, HttpStatus.ACCEPTED);
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