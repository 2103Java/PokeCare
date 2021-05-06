package com.revature.pokecare.service;

import com.revature.pokecare.models.Trainer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TrainerService {

    public Trainer login(String username, String password) {
        return null;
    }

    public boolean putTrainer(Trainer newTrainer) {
        return true;
    }

    public boolean deleteTrainer(Trainer dTrainer) {
        return true;
    }


}
