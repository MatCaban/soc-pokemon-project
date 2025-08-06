package com.github.matcaban.pokemonmanager.service;

import com.github.matcaban.pokemonmanager.db.DBPokemonService;

public class PokemonController {
    private final DBPokemonService service;

    public PokemonController() {
        this.service = new DBPokemonService();
    }

    public void printFreePokemons() {
        service.getAllPokemon().stream()
                .filter(pokemon -> pokemon.getTrainerId() == 0)
                .forEach(System.out::println);
    }
}
