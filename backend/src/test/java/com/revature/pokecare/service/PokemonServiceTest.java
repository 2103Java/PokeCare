package com.revature.pokecare.service;

import com.revature.pokecare.models.Pokemon;
import com.revature.pokecare.models.Trainer;
import com.revature.pokecare.repository.PokemonRepository;
import com.revature.pokecare.repository.TrainerRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class PokemonServiceTest extends TestCase {

    @Mock
    PokemonRepository pokeRepo = Mockito.mock(PokemonRepository.class);
    TrainerRepository trainerRepo = Mockito.mock(TrainerRepository.class);
    Trainer tr = Mockito.mock(Trainer.class);

    PokemonService pkServe = new PokemonService(pokeRepo, trainerRepo);

    @Before
    public void setUpTests() {
        Mockito.when(pokeRepo.updatePokemon(Mockito.any())).thenReturn(true);
    }

    public void testGetNewPokemon() {
        List<Pokemon> mockList = new ArrayList<>();

        Mockito.when(pokeRepo.updatePokemon(Mockito.any())).thenReturn(true);
        Mockito.when(tr.getPokemon()).thenReturn(mockList);
        Pokemon pk = pkServe.getNewPokemon(tr);

        assertTrue((pk.getHappiness() < 50 && pk.getHappiness() >= 0));
        assertEquals(pk.getFatigue(), 0);
        assertEquals(pk.getHunger(), 0);
        assertTrue((pk.getNumber() > 0 && pk.getNumber() < 893));
    }

    public void testPlayWithPokemon() {
        Pokemon tPoke = new Pokemon();

        //Pokemon pk = pkServe.playWithPokemon(tPoke);
        //assertTrue((pk.getHappiness() > 0));
    }

    public void testFeedPokemon() {
        Pokemon tPoke = new Pokemon();
        int testHung = 3;
        tPoke.setHunger(testHung);
       /* Pokemon pk = pkServe.feedPokemon(tPoke);

        assertTrue(pk.getHunger() != testHung);
        assertTrue(pk.getHunger() >= 0);
        assertTrue(pk.getHunger() <= 100);*/
    }

    public void testTirePokemon() {
        Pokemon tPoke = new Pokemon();
        int testFat = 3;
        tPoke.setFatigue(testFat);

        /*Pokemon pk = pkServe.tirePokemon(tPoke);
        assertTrue(tPoke.getFatigue() > testFat);
        assertTrue(tPoke.getFatigue() >= 0);*/
    }

    public void testTrainPokemon1() {
        Pokemon tPoke = new Pokemon();
        tPoke.setFatigue(0);
        tPoke.setHunger(0);

       /* Pokemon pk = pkServe.trainPokemon(tPoke, 1);
        assertTrue(pk.getFatigue() == 4);
        assertTrue(pk.getHunger() == 4);
        assertTrue(pk.getExperience() == 5);*/
    }

    public void testTrainPokemon2() {
        Pokemon tPoke = new Pokemon();
        tPoke.setFatigue(0);
        tPoke.setHunger(0);

       /* Pokemon pk = pkServe.trainPokemon(tPoke, 2);
        assertTrue(pk.getFatigue() == 7);
        assertTrue(pk.getHunger() == 2);
        assertTrue(pk.getExperience() == 5);*/
    }

    public void testTrainPokemon3() {
        Pokemon tPoke = new Pokemon();
        tPoke.setFatigue(0);
        tPoke.setHunger(0);

        /*Pokemon pk = pkServe.trainPokemon(tPoke, 3);
        assertTrue(pk.getFatigue() == 2);
        assertTrue(pk.getHunger() == 7);
        assertTrue(pk.getExperience() == 5);*/
    }

    public void testRestPokemon() {
    }
}