package com.revature.pokecare.models;

public class PokemonData {
    private final String name;
    private final String type;

    public PokemonData(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}