package com.revature.pokecare.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.models.PokemonData;
import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.repository.PokemonRepository;
import com.revature.pokecare.repository.TrainerRepository;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PokemonService {
    private final PokemonData[] pokemonData = new PokemonData[898];

    private final PokemonRepository pokemonRepo;
    private final TrainerRepository trainerRepo;
    private final static Logger loggy = Logger.getLogger(PokemonService.class);

    @Autowired
    public PokemonService(PokemonRepository pokemonRepo, TrainerRepository trainerRepo) {
        loggy.info("Pokemon Service object created.");
        this.pokemonRepo = pokemonRepo;
        this.trainerRepo = trainerRepo;
    }

    public Pokemon findMyPokemon(int id) {
        if (id > 0) {
            loggy.info("Searching pokemon of id: " + id);
            Pokemon po = pokemonRepo.findPokemonById(id);

            if (po != null) {
                loggy.info("Pokemon found.");
                return po;
            }
        }
        loggy.warn("Pokemon not found.");
        return null;
    }

    public int returnPokemon(Trainer trainer, int id) {
        Pokemon pokemon = trainer.getPokemon().stream().filter(poke -> poke.getId() == id).findFirst().orElse(null);

        if (pokemon != null && pokemonRepo.deletePokemon(pokemon)) {
            loggy.info("returnPokemon attempting" + pokemon.getId());
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

            loggy.info("returnPokemon returning money: " + money);

            return money;
        }
        return 0;
    }

    public Pokemon getNewPokemon(Trainer trainer) {
        loggy.info("Pulling pokemon for trainer: " + trainer.getUsername());

        Pokemon newPoke;
        Random rand = new Random();
        int num = rand.nextInt(898);
        int happiness = rand.nextInt(50);
        loggy.info("Random num = " + num + " and random happiness = " + happiness);


        if (num <= 0) {
            loggy.info("Invalid number, defaulting to bulbasaur.");

            num = 1;
        }

        newPoke = new Pokemon(trainer, happiness, 0, 0, 0, num);
        pokemonRepo.insertPokemon(newPoke);

        trainer.getPokemon().add(newPoke);
        newPoke.setData(getPokemonData(num));

        return newPoke;
    }

    public Pokemon playWithPokemon(Pokemon pokemon) {
        if (pokemon != null) {
            loggy.info("playWithPokemon: " + pokemon.getId());

            int happUp = ThreadLocalRandom.current().nextInt(5, 10);
            int newHapp = pokemon.getHappiness() + happUp;

            loggy.info("Happiness adjusted by: " + happUp + " New happiness: " + newHapp);

            if ((newHapp + happUp) <= 100) {
                pokemon.setHappiness((pokemon.getHappiness() + happUp));
            } else {
                loggy.info("Invalid happiness, capping at 100.");

                pokemon.setHappiness(100);
            }

            pokemonRepo.updatePokemon(pokemon);
            return pokemon;
        }
        return null;
    }

    public Pokemon feedPokemon(Pokemon pokemon) {
        if (pokemon != null) {
            loggy.info("feedPokemon attempting: " + pokemon.getId());

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
            loggy.info("Training pokemon option 1.");

            if (pokemon.getHunger() < 95 && pokemon.getFatigue() < 95){
                pokemon.setHunger(pokemon.getHunger() + 4);
                pokemon.setFatigue(pokemon.getFatigue() + 4);}
            else return pokemon;
            break;
        case 2:
            loggy.info("Training pokemon option 2.");
            if (pokemon.getHunger() < 99 && pokemon.getFatigue() < 94){
                pokemon.setHunger(pokemon.getHunger() + 2);
                pokemon.setFatigue(pokemon.getFatigue() + 7);}
                else return pokemon;
            break;
        case 3:
            loggy.info("Training pokemon option 3.");
            if (pokemon.getHunger() < 94 && pokemon.getFatigue() < 99){
            pokemon.setHunger(pokemon.getHunger() + 7);
            pokemon.setFatigue(pokemon.getFatigue() + 2);}
            else return pokemon;
            break;
        }

        loggy.info("Training success, updating experience.");
        pokemon.setExperience(pokemon.getExperience() + 5);
        pokemonRepo.updatePokemon(pokemon);
        return pokemon;
    }

    public Pokemon restPokemon(Pokemon pokemon) {
        if (pokemon != null) {
            loggy.info("Rest Pokemon called.");
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

    public PokemonData getPokemonData(int pokeId) {
        pokeId--;
        loggy.info("Getting Pokemon data.");
        if (pokemonData[pokeId] != null) {
            loggy.info("Returning data.");
            return pokemonData[pokeId];
        }

        GetRequest getRequest = Unirest.get("https://pokeapi.co/api/v2/pokemon/" + (pokeId + 1));

        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = getRequest.asJson();
            JsonNode body = jsonNodeHttpResponse.getBody();
            JSONObject poke = body.getObject();

            pokemonData[pokeId] = new PokemonData(poke.getJSONObject("species").getString("name"),
                    poke.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name"));
        } catch (UnirestException e) {
            loggy.warn(e);
            e.printStackTrace();
        }
        loggy.info("Returning data.");
        return pokemonData[pokeId];
    }
}