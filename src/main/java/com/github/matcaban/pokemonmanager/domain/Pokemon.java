package com.github.matcaban.pokemonmanager.domain;

import java.util.Objects;

public class Pokemon {
    private Integer id;
    private String name;
    private String type;
    private String unique_trait;
    private Integer trainerId;

    public Pokemon(Integer id, String name, String type, String unique_trait, Integer trainerId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.unique_trait = unique_trait;
        this.trainerId = trainerId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUnique_trait() {
        return unique_trait;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", identification mark='" + unique_trait + '\'' +
                ", trainerId=" + trainerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(id, pokemon.id) && Objects.equals(name, pokemon.name) && Objects.equals(type, pokemon.type) && Objects.equals(unique_trait, pokemon.unique_trait) && Objects.equals(trainerId, pokemon.trainerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, unique_trait, trainerId);
    }
}
