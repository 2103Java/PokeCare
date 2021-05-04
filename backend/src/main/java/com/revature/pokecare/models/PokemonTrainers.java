package com.revature.pokecare.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonTrainers
{
    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int id;

    @Column (name = "username", unique = true, nullable = false)
    private String username;

    @Column (name = "password")
    private String password;

    @OneToMany
    private List<Pokemons> pokeList = new ArrayList<Pokemons>();

}
