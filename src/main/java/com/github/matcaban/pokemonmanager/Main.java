package com.github.matcaban.pokemonmanager;

import com.github.matcaban.pokemonmanager.Controller.PokemonManager;
import com.github.matcaban.pokemonmanager.service.DBTrainerService;

import java.sql.SQLException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws SQLException {

        PokemonManager pokemonManager = new PokemonManager();
        pokemonManager.printOptions();

//        DBPokemonService service = new DBPokemonService();
//
//        System.out.println(service.getIdOfName("mrmime"));

//        DBTrainerService service = new DBTrainerService();
//
//        service.getAllTrainers().stream().forEach(System.out::println);

    }
}
