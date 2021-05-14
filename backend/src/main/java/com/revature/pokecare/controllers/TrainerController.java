package com.revature.pokecare.controllers;

import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Trainer> postTrainer(@RequestBody MultiValueMap<String, String> form, HttpSession session) {
        Trainer trainer = trainerService.login(form.getFirst("username"), form.getFirst("password"));

        if (trainer == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        session.setAttribute("PokeTrainer", trainer);
        return new ResponseEntity<>(trainer, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> register(@RequestBody MultiValueMap<String, String> form) {
        return trainerService.register(new Trainer(form.getFirst("username"), form.getFirst("email"), form.getFirst("password"))) ?
                new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    //Delete a trainer based on a passed in JSON Trainer
    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteTrainer(@RequestBody Trainer dTrainer, HttpSession session) {

        if (session.getAttribute("PokeTrainer").equals(dTrainer)) {
            boolean trDeleted = trainerService.deleteTrainer(dTrainer);
            if (trDeleted) {
                session.invalidate();
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return null;
    }

    @PostMapping(value = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping
    public ResponseEntity<Trainer> getTrainer(HttpSession session) {
        Trainer trainer = (Trainer) session.getAttribute("PokeTrainer");

        return trainer == null ? new ResponseEntity<>(HttpStatus.FORBIDDEN) : new ResponseEntity<>(trainer, HttpStatus.OK);
    }
}