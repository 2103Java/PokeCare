package com.revature.pokecare.service;

import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.repository.PokemonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

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
    	
    	//We'll need to pass in a trainer ID here so we can associate it with a user, but for now this is fine.
    	newPoke = new Pokemon(trainerID, happiness, 0, 0, 0, num);
    	
    	
    	
        return null;
    }

    public boolean updatePokemon(Pokemon pk) {
    	if (pk != null) {
    		pr.updatePokemon(pk);
    		return true;
    	}
        return false;
    }
}
