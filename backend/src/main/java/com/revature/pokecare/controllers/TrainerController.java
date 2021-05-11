package com.revature.pokecare.controllers;

import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.service.TrainerService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
    private final SessionFactory sessionFactory;
    private final TrainerService ts;

    @Autowired
    public TrainerController(SessionFactory sessionFactory, TrainerService ts) {
        this.sessionFactory = sessionFactory;
        this.ts = ts;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Trainer> postTrainer(@RequestBody MultiValueMap<String, String> form) {

        Trainer trainer = ts.login(form.getFirst("username"), form.getFirst("password"));

        if (trainer == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> register(@RequestBody MultiValueMap<String, String> form) {
        System.out.println(form);
        return ts.register(new Trainer(form.getFirst("username"), form.getFirst("email"), form.getFirst("password"))) ?
                new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    //Delete a trainer based on a passed in JSON Trainer
    @RequestMapping(value = "/delete")
    public ResponseEntity<String> deleteTrainer(@RequestBody Trainer dTrainer) {

        if (sessionFactory.getCurrentSession().isOpen()) {
            boolean trDeleted = ts.deleteTrainer(dTrainer);
            if (trDeleted) {
                sessionFactory.getCurrentSession().close();
                return new ResponseEntity<String>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return null;
    }

    //lougout ping closes the session
    @RequestMapping(value = "/logout")
    public void logout() {
        System.out.println("Hitting Logout");
        if (sessionFactory.getCurrentSession().isOpen()) {
            sessionFactory.getCurrentSession().close();
        }
    }
}