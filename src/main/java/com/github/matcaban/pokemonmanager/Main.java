package com.github.matcaban.pokemonmanager;

import com.github.matcaban.pokemonmanager.Controller.PokemonManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        new PokemonManager().printOptions();
    }
}
