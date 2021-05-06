package com.revature.pokecare.service;

import com.revature.pokecare.models.Pokemon;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PokemonService {

    public Pokemon findMyPokemon(int id) {
        return null;
    }

    public boolean deletePokemon(int id) {
        return true;
    }

    public Pokemon getNewPokemon() {
        return null;
    }

    public boolean updatePokemon(Pokemon pk) {
        return true;
    }
}
