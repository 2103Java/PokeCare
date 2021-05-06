package com.revature.pokecare.controllers;

import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.repository.TrainerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("app/*")
public class TrainerController {

    private final TrainerRepository tr;

    @Autowired
    private SessionFactory sf;

    @Autowired
    public TrainerController(TrainerRepository tr) {
        this.tr = tr;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void postTrainer(@RequestParam(name = "username") String username, @RequestParam("password") String password) {
        Trainer trainerCheck = tr.findTrainerByUsername(username);

        if (trainerCheck == null){

            return;
        }

        if (trainerCheck.correctPassword(password)) {
            Session loginSession = sf.openSession();
        }
    }


}
