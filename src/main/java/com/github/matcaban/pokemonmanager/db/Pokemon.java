package com.github.matcaban.pokemonmanager.db;

public class Pokemon {
    private String name;
    private String unique_trait;
    private Integer trainerId;

    public Pokemon(String name, String unique_trait, Integer trainerId) {
        this.name = name;
        this.unique_trait = unique_trait;
        this.trainerId = trainerId;
    }

    public String getName() {
        return name;
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
                "name='" + name + '\'' +
                ", unique_trait='" + unique_trait + '\'' +
                ", trainerId=" + trainerId +
                '}';
    }
}
