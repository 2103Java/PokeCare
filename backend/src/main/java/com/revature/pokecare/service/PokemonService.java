package com.revature.pokecare.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.repository.PokemonRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
@Service
@Transactional
public class PokemonService {
	
	@Autowired
	PokemonRepository pr;

	public static Map<Integer,String> pokes;

	static {
		pokes = new HashMap<Integer, String>();
	}

	public Pokemon findMyPokemon(int id) {
    	if (id > 0) {
    		Pokemon po = pr.findPokemonById(id);
    		if(po != null) {return po;}
    	}

        return null;
    }

    public void findAllMyPokemon(Trainer trainer){
		trainer.setPokeList(pr.findPokemonByTrainerId(trainer.getId()));
	}

    public boolean deletePokemon(int id) {
    	if(id != 0) {
    		pr.deletePokemon(id);
    		return true;
    	}
        return false;
    }

    public Pokemon getNewPokemon(Trainer trainer) {
    	Pokemon newPoke;
    	Random rand = new Random();
    	int num = rand.nextInt(898);
    	int happiness = rand.nextInt(50);    	
    	if (num <= 0) {num = 1;}


		newPoke = new Pokemon(trainer.getId(), happiness, 0, 0, 0, num);
		pr.insertPokemon(newPoke);
		trainer.setPokeList(pr.findPokemonByTrainerId(trainer.getId()));
		newPoke.setPokeName(pokes.get(num));
    	
        return newPoke;
    }
    
    
    public Pokemon playWithPokemon(Pokemon pk) {
    	if (pk != null) {
    		int happUp = ThreadLocalRandom.current().nextInt(5, 10);
    		int newHapp = pk.getHappiness() + happUp;
    		
    		if ((newHapp + happUp) <= 100) {
    			pk.setHappiness((pk.getHappiness() + happUp));}
    		else {pk.setHappiness(100);}
    			pr.updatePokemon(pk);
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
    		pr.updatePokemon(pk);
    		return pk;
    	}
    	return null;
    }
    
    public Pokemon trainPokemon(Pokemon pk, int type) {

    	switch (type){
			case 1:
				pk.setHunger(pk.getHunger() + 4);
				pk.setFatigue(pk.getFatigue() + 4);
				break;
			case 2:
				pk.setHunger(pk.getHunger() + 2);
				pk.setFatigue(pk.getFatigue() + 7);
				break;
			case 3:
				pk.setHunger(pk.getHunger() + 7);
				pk.setFatigue(pk.getFatigue() + 2);
				break;
    	}
    	pk.setExperience(pk.getExperience() + 5);
    	pr.updatePokemon(pk);
    	return pk;
    }

    public Pokemon restPokemon(Pokemon pk) {
    	if (pk != null){
    		pk.setFatigue(pk.getFatigue() - 5);
    		pr.updatePokemon(pk);
		}
    	return pk;
	}

    public boolean updatePokemon(Pokemon pk) {
    	if (pk != null) {
    		pr.updatePokemon(pk);
    		return true;
    	}
        return false;
    }

    public void fillPokeMap(){
		GetRequest getRequest = Unirest.get("https://pokeapi.co/api/v2/pokemon?limit=898&offset=0");
		try {
			HttpResponse<JsonNode> jsonNodeHttpResponse = getRequest.asJson();
			JsonNode body = jsonNodeHttpResponse.getBody();
			JSONObject fullResponse = body.getObject();
			JSONArray results = fullResponse.getJSONArray("results");

			for(int i = 0; i < 898; i++) pokes.put(i+1,results.getJSONObject(i).getString("name"));


		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
