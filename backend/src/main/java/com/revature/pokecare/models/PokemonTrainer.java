package com.revature.pokecare.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Table(name = "poketrainer")
public class PokemonTrainer
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
    private List<Pokemon> pokeList = new ArrayList<Pokemon>();

    public PokemonTrainer(){
        super();
    }

    public PokemonTrainer(String username, String password){
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Pokemon> getPokeList() {
        return pokeList;
    }

    public void setPokeList(List<Pokemon> pokeList) {
        this.pokeList = pokeList;
    }
}
