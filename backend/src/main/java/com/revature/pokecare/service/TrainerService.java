package com.revature.pokecare.service;

import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TrainerService {

    @Autowired
    TrainerRepository tr;

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
