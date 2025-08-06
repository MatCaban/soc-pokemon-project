package com.github.matcaban.pokemonmanager.service;

import com.github.matcaban.pokemonmanager.utility.InputUtils;
import com.github.matcaban.pokemonmanager.utility.OutputUtil;

public class PokemonManager {
    private final PokemonController pokemonController;

    public PokemonManager () {
        this.pokemonController = new PokemonController();
    }

    public void printOptions() {
        System.out.println("Hello and welcome to Pokemon Manager System I.generation");

        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Pokemon Manager");
            System.out.println("2. Trainer Manager");

            int userInput = InputUtils.readInt();

            switch (userInput) {
                case 0 -> {
                    System.out.println("Thank you, bye!");
                    return;
                }
                case 1 -> pokemonManagerOption();
                case 2 -> trainerManagerOption();
                default -> {
                    System.out.println("Invalid input");
                    continue;
                }
            }
        }
    }


    private void pokemonManagerOption() {
        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("0. Back");
            System.out.println("1. Get All Free Pokemons");
            System.out.println("2. Create new pokemon");
            System.out.println("3. Edit pokemon");
            System.out.println("4. Delete (kill) pokemon");

            int userInput = InputUtils.readInt();

            switch (userInput) {
                case 0 -> {
                    return;
                }
                case 1 -> pokemonController.printFreePokemons();
                case 2 -> System.out.println("Tu bude create");
                case 3 -> System.out.println("Tu bude edit");
                case 4 -> System.out.println("Tu bude kill");
                default -> {
                    System.out.println("Invalid input");
                    continue;
                }
            }
        }
    }

    private void trainerManagerOption() {
        System.out.println("trainer manager");
    }
}
