package com.github.matcaban.pokemonmanager.db;

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
}
