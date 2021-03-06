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

    @Autowired
    public PokemonService(PokemonRepository pokemonRepo, TrainerRepository trainerRepo) {
        this.pokemonRepo = pokemonRepo;
        this.trainerRepo = trainerRepo;
    }

    public int returnPokemon(Trainer trainer, int id) {
        Pokemon pokemon = trainer.getPokemon().stream().filter(poke -> poke.getId() == id).findFirst().orElse(null);

        if (pokemon != null && pokemonRepo.deletePokemon(pokemon)) {
            int money = pokemon.getHappiness();

            if (pokemon.getExperience() > 1) {
                money += (double) pokemon.getFatigue() / 10;
            }

            if (pokemon.getHunger() > 10) {
                money /= pokemon.getHunger();
            }

            trainer.getPokemon().remove(pokemon);
            trainer.setCurrency(trainer.getCurrency() + money);
            trainerRepo.updateTrainer(trainer);

            return money;
        }
        return 0;
    }

    public Pokemon getNewPokemon(Trainer trainer) {
        if(trainer.getPokemon().size() >= 6) return null;
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
        newPoke.setData(getPokemonData(num));

        return newPoke;
    }

    public int playWithPokemon(Trainer trainer, int id) {
        Pokemon pokemon = trainer.getPokemon().stream().filter(poke -> poke.getId() == id).findFirst().orElse(null);

        if (pokemon == null) {
            return 0;
        }

        int hap = ThreadLocalRandom.current().nextInt(10, 30);

        pokemon.setHappiness(Math.min(pokemon.getHappiness() + hap, 100));
        pokemon.setFatigue(Math.min(pokemon.getFatigue() + 20, 100));
        pokemon.setHunger(Math.min(pokemon.getHunger() + 10, 100));
        pokemonRepo.updatePokemon(pokemon);

        return hap;
    }

    public boolean feedPokemon(Trainer trainer, int id) {
        if (trainer.getCurrency() < 100) {
            return false;
        }

        Pokemon pokemon = trainer.getPokemon().stream().filter(poke -> poke.getId() == id).findFirst().orElse(null);

        if (pokemon == null) {
            return false;
        }

        pokemon.setHunger(Math.max(pokemon.getHunger() - 50, 0));
        pokemonRepo.updatePokemon(pokemon);

        trainer.setCurrency(trainer.getCurrency() - 100);
        trainerRepo.updateTrainer(trainer);
        return true;
    }

    public boolean trainPokemon(Trainer trainer, int pokeId, int type) {
        Pokemon pokemon = trainer.getPokemon().stream().filter(poke -> poke.getId() == pokeId).findFirst().orElse(null);

        if (pokemon == null || pokemon.getHunger() >= 75 || pokemon.getFatigue() > 0 || pokemon.getHappiness() <= 20) {
            return false;
        }

        switch (type) {
        case 1:
            pokemon.setHunger(Math.min(pokemon.getHunger() + 100, 100));
            pokemon.setFatigue(Math.min(pokemon.getFatigue() + 100, 100));
            pokemon.setExperience(pokemon.getExperience() + 150);
            break;

        case 2:
            pokemon.setHunger(Math.min(pokemon.getHunger() + 50, 100));
            pokemon.setFatigue(Math.min(pokemon.getFatigue() + 50, 100));
            pokemon.setExperience(pokemon.getExperience() + 60);
            break;

        case 3:
            pokemon.setHunger(Math.min(pokemon.getHunger() + 10, 100));
            pokemon.setFatigue(Math.min(pokemon.getFatigue() + 10, 100));
            pokemon.setExperience(pokemon.getExperience() + 30);
            break;
        }

        pokemon.setHappiness(Math.max(pokemon.getHappiness() - 20, 0));
        pokemonRepo.updatePokemon(pokemon);
        return true;
    }

    public boolean restPokemon(Trainer trainer, int pokeId, int fatigue) {
        Pokemon pokemon = trainer.getPokemon().stream().filter(poke -> poke.getId() == pokeId).findFirst().orElse(null);

        if (pokemon == null || pokemon.getFatigue() < 0) {
            return false;
        }

        pokemon.setFatigue(fatigue);
        pokemonRepo.updatePokemon(pokemon);
        return true;
    }

    public PokemonData getPokemonData(int pokeId) {
        pokeId--;

        if (pokemonData[pokeId] != null) {
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
            e.printStackTrace();
        }

        return pokemonData[pokeId];
    }
}