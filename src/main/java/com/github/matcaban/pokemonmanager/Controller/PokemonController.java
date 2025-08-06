package com.github.matcaban.pokemonmanager.Controller;

import com.github.matcaban.pokemonmanager.service.DBPokemonService;
import com.github.matcaban.pokemonmanager.utility.InputUtils;
import com.github.matcaban.pokemonmanager.utility.OutputUtil;

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

    public void createNewPokemon() {
        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("Are you sure you saw Pokemon?");
            System.out.println("0. Back");
            System.out.println("1. Yes");
            int choice = InputUtils.readInt();

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    OutputUtil.lineSplitter();
                    System.out.println("So you saw a new Pokemon\nGreat, enter its name:");
                    String name = InputUtils.readStringToLowerCase();

                    int id = service.getIdOfName(name);

                    if (id == -1) {
                        System.out.println("I'm sorry, I don't have that name in my pokedex\nYou must have seen some other animal");
                        continue;
                    }

                    System.out.println("Oh great a " + name + "\nTake a quick look at some identifying mark on it so we can tell it apart from the others of the same kind");

                    String unique_trait = InputUtils.readStringToLowerCase();

                    if (service.create(id, unique_trait) > 0) {
                        System.out.println("Pokemon successfully added to my pokedex. You can now go and catch it");
                        return;
                    }
                }
                default -> {
                    System.out.println("Invalid input, try again");
                    continue;
                }

            }

        }


    }

}

