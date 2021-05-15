package com.revature.pokecare.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.repository.PokemonRepository;
import com.revature.pokecare.repository.TrainerRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PokemonService {
    public static Map<Integer, String> pokes = new HashMap<>();

    private final PokemonRepository pokemonRepo;
    private final TrainerRepository trainerRepo;

    @Autowired
    public PokemonService(PokemonRepository pokemonRepo, TrainerRepository trainerRepo) {
        this.pokemonRepo = pokemonRepo;
        this.trainerRepo = trainerRepo;
    }

    public Pokemon findMyPokemon(int id) {
        if (id > 0) {
            Pokemon po = pokemonRepo.findPokemonById(id);

            if (po != null) {
                return po;
            }
        }

        return null;
    }

    public int returnPokemon(Trainer trainer, int id) {
        Pokemon pokemon = trainer.getPokemon().stream().filter(poke -> poke.getId() == id).findFirst().orElse(null);

        if (pokemon != null && pokemonRepo.deletePokemon(pokemon)) {
            int money = 100 * pokemon.getHappiness();

            if (pokemon.getHunger() > 10) {
                money /= pokemon.getHunger();
            }

            if (pokemon.getExperience() > 1) {
                money /= (double) pokemon.getFatigue() / 10;
            }

            trainer.getPokemon().remove(pokemon);
            trainer.setCurrency(trainer.getCurrency() + money);
            trainerRepo.updateTrainer(trainer);

            return money;
        }
        return 0;
    }

    public Pokemon getNewPokemon(Trainer trainer) {
        Pokemon newPoke;
        Random rand = new Random();
        int num = rand.nextInt(898);
        int happiness = rand.nextInt(50);

        if (num <= 0) {
            num = 1;
        }

        newPoke = new Pokemon(trainer, happiness, 0, 0, 0, num);
        pokemonRepo.insertPokemon(newPoke);

        trainer.getPokemon().add(newPoke);
        newPoke.setPokeName(pokes.get(num));

        return newPoke;
    }

    public Pokemon playWithPokemon(Pokemon pokemon) {
        if (pokemon != null) {
            int happUp = ThreadLocalRandom.current().nextInt(5, 10);
            int newHapp = pokemon.getHappiness() + happUp;

            if ((newHapp + happUp) <= 100) {
                pokemon.setHappiness((pokemon.getHappiness() + happUp));
            } else {
                pokemon.setHappiness(100);
            }

            pokemonRepo.updatePokemon(pokemon);
            return pokemon;
        }
        return null;
    }

    public Pokemon feedPokemon(Pokemon pokemon) {
        if (pokemon != null) {
//    		int hungDown = ThreadLocalRandom.current().nextInt(5, 10);

            int newHung = pokemon.getHunger() - 5;
            if (newHung >= 0) {
                pokemon.setHunger(newHung);
            } else {
                pokemon.setHunger(0);
            }

            pokemonRepo.updatePokemon(pokemon);
            return pokemon;
        }
        return null;
    }

    public Pokemon tirePokemon(Pokemon pokemon) {
        if (pokemon != null) {
            pokemon.setFatigue(pokemon.getFatigue() + 5);
            pokemonRepo.updatePokemon(pokemon);
            return pokemon;
        }
        return null;
    }

    public Pokemon trainPokemon(Pokemon pokemon, int type) {
        switch (type) {
        case 1:
            pokemon.setHunger(pokemon.getHunger() + 4);
            pokemon.setFatigue(pokemon.getFatigue() + 4);
            break;
        case 2:
            pokemon.setHunger(pokemon.getHunger() + 2);
            pokemon.setFatigue(pokemon.getFatigue() + 7);
            break;
        case 3:
            pokemon.setHunger(pokemon.getHunger() + 7);
            pokemon.setFatigue(pokemon.getFatigue() + 2);
            break;
        }

        pokemon.setExperience(pokemon.getExperience() + 5);
        pokemonRepo.updatePokemon(pokemon);
        return pokemon;
    }

    public Pokemon restPokemon(Pokemon pokemon) {
        if (pokemon != null) {
            pokemon.setFatigue(pokemon.getFatigue() - 5);
            pokemonRepo.updatePokemon(pokemon);
        }
        return pokemon;
    }

    public boolean updatePokemon(Pokemon pokemon) {
        if (pokemon != null) {
            pokemonRepo.updatePokemon(pokemon);
            return true;
        }
        return false;
    }

    public void fillPokeMap() {
        GetRequest getRequest = Unirest.get("https://pokeapi.co/api/v2/pokemon?limit=898&offset=0");

        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = getRequest.asJson();
            JsonNode body = jsonNodeHttpResponse.getBody();
            JSONObject fullResponse = body.getObject();
            JSONArray results = fullResponse.getJSONArray("results");

            for (int i = 0; i < 898; i++) {
                pokes.put(i + 1, results.getJSONObject(i).getString("name"));
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}