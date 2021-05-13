package com.revature.pokecare.models;

import javax.persistence.*;

@Entity
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "poke_number")
    private int number;

	@Column(name = "trainer_id")
    private int trainer_id;

    @Column(name = "happiness")
    private int happiness;

    @Column(name = "hunger")
    private int hunger;

    @Column(name = "fatigue")
    private int fatigue;

    @Column(name = "experience")
    private int experience;

    @Transient
    private String pokeName;

    public String getPokeName() {
        return pokeName;
    }

    public void setPokeName(String pokeName) {
        this.pokeName = pokeName;
    }

    public Pokemon() {
    }

    public Pokemon(int trainer_id, int happiness, int hunger, int fatigue, int experience, int number) {
        this.trainer_id = trainer_id;
        this.happiness = happiness;
        this.hunger = hunger;
        this.fatigue = fatigue;
        this.experience = experience;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

    public int getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(int trainer_id) {
        this.trainer_id = trainer_id;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

}
