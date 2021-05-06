package com.revature.pokecare.controllers;

import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.service.TrainerService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("pokeCare/Trainer")
public class TrainerController {


    private final SessionFactory sf;

    private final TrainerService ts;

    @Autowired
    public TrainerController(SessionFactory sf, TrainerService ts) {
        this.sf = sf;
        this.ts = ts;
    }

    //login with a POST call, service layer verifies trainer exists and passwords match then opens a session, do we want to do more with the status code?
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void postTrainer(@RequestParam(name = "username") String username, @RequestParam("password") String password) {
        Trainer trainerCheck = ts.login(username, password);

        if (trainerCheck == null) {
            return;
        }

        if (trainerCheck.correctPassword(password)) {
            Session loginSession = sf.openSession();
        }
    }

    //Put or Post for registration?
    @RequestMapping(value = "/register", method = RequestMethod.PUT)
    public ResponseEntity<String> putTrainer(@RequestBody Trainer newTrainer) {
        boolean newTrPut = ts.putTrainer(newTrainer);

        if (newTrPut) {
            return new ResponseEntity<String>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    //Delete a trainer based on a passed in JSON Trainer
    @RequestMapping(value = "/delete")
    public ResponseEntity<String> deleteTrainer(@RequestBody Trainer dTrainer) {

        if (sf.getCurrentSession().isOpen()) {
            boolean trDeleted = ts.deleteTrainer(dTrainer);
            if (trDeleted) {
                sf.getCurrentSession().close();
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
        if (sf.getCurrentSession().isOpen()) sf.getCurrentSession().close();
    }


}
