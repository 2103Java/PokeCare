package com.revature.pokecare.service;

import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.repository.PokemonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;

@Service
@Transactional
public class PokemonService {
	
	@Autowired
	PokemonRepository pr;

    public Pokemon findMyPokemon(int id) {
    	if (id > 0) {
    		Pokemon po = pr.findPokemonById(id);
    		if(po != null) {return po;}
    	}
    	
        return null;
    }

    public boolean deletePokemon(int id) {
    	if(id != 0) {
    		pr.deletePokemon(id);
    		return true;
    	}
        return false;
    }

    public Pokemon getNewPokemon(int trainerID) {
    	Pokemon newPoke;
    	Random rand = new Random();
    	int num = rand.nextInt(898);
    	int happiness = rand.nextInt(50);    	
    	if (num <= 0) {num = 1;}
    	newPoke = new Pokemon(trainerID, happiness, 0, 0, 0, num);
    	
        return null;
    }
    
    
    public Pokemon playWithPokemon(Pokemon pk) {
    	if (pk != null) {
    		int happUp = ThreadLocalRandom.current().nextInt(5, 10);
    		int newHapp = pk.getHappiness() + happUp;
    		
    		if ((newHapp + happUp) <= 100) {
    			pk.setHappiness((pk.getHappiness() + happUp));}
    		else {pk.setHappiness(100);}
    			updatePokemon(pk);
    			return pk;
    		}
    	return null;
    }
    
    public Pokemon feedPokemon(Pokemon pk) {
    	if (pk != null) {
//    		int hungDown = ThreadLocalRandom.current().nextInt(5, 10);
    		
    		int newHung = pk.getHunger() - 5;
    		if(newHung >=0) {
    			pk.setHunger(newHung);
    		}else {pk.setHunger(0);}
    		pr.updatePokemon(pk);
    		return pk;
    	}
    	
    	else return null;
    }
    
    public Pokemon tirePokemon(Pokemon pk) {
    	if (pk != null) {
    		pk.setFatigue(pk.getFatigue() + 5);
    		updatePokemon(pk);
    		return pk;
    	}
    	return null;
    }
    
    public Pokemon trainPokemon(Pokemon pk) {
    	pk.setExperience(pk.getExperience() + 5);
    	pk = tirePokemon(pk);
    	updatePokemon(pk);
    	return pk;
    }
    

    public boolean updatePokemon(Pokemon pk) {
    	if (pk != null) {
    		pr.updatePokemon(pk);
    		return true;
    	}
        return false;
    }
}
