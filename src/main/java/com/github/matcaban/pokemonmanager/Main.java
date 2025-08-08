package com.github.matcaban.pokemonmanager;

import com.github.matcaban.pokemonmanager.controller.PokemonManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        new PokemonManager().printOptions();
    }
}
