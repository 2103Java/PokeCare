package com.revature.pokecare.service;

import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.repository.PokemonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Pokemon getNewPokemon() {
    	
    	
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
