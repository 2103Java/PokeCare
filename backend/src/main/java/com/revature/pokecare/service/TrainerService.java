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
    	//Making sure these aren't empty strings. We don't want empty strings!
    	if (username != null && password != null){
    		Trainer train = tr.findTrainerByUsername(username);
    		//And just in case the DAO passes us an empty trainer object.
    		if (train != null && train.correctPassword(password)) {
    			return train;
    		}
    	}
    	
        return null;
    }
    
    public boolean putTrainer(Trainer newTrainer) {
    	
    	if (newTrainer != null) {
    		
    		tr.insertTrainer(newTrainer);
    		return true;
    	}
    	
        return false;
    }

    public boolean deleteTrainer(Trainer dTrainer) {
    	//Probably need to check if the users is an admin before allowing this, but that's going to be a session thing and probably handled in the controller.
    	if (dTrainer != null)
    	{
    		tr.deleteTrainer(dTrainer.getId());
    	}
    	
    	
        return true;
    }


}
