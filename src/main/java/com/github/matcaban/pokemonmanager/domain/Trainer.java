package com.github.matcaban.pokemonmanager.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trainer {
    private Integer id;
    private String name;
    private List<Pokemon> pokemons;

    public Trainer(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.pokemons = new ArrayList<>();
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void addPokemon(Pokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(id, trainer.id) && Objects.equals(name, trainer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pokemons);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pokemons=" + pokemons +
                '}';
    }
}
