package com.github.matcaban.pokemonmanager;

import com.github.matcaban.pokemonmanager.db.DBPokemonService;
import com.github.matcaban.pokemonmanager.service.PokemonManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        PokemonManager pokemonManager = new PokemonManager();
        pokemonManager.printOptions();
    }
}
