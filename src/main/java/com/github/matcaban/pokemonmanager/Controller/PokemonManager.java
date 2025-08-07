package com.github.matcaban.pokemonmanager.Controller;

import com.github.matcaban.pokemonmanager.utility.InputUtils;
import com.github.matcaban.pokemonmanager.utility.OutputUtil;

import java.sql.SQLOutput;

public class PokemonManager {
    private final PokemonController pokemonController;
    private final TrainerController trainerController;

    public PokemonManager () {
        this.pokemonController = new PokemonController();
        this.trainerController = new TrainerController();
    }

    public void printOptions() {
        System.out.println("Hello and welcome to Pokemon Manager System I.generation");

        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Pokemon Manager");
            System.out.println("2. Trainer Manager");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 0 -> {
                    System.out.println("Thank you, bye!");
                    return;
                }
                case 1 -> pokemonManagerOption();
                case 2 -> trainerManagerOption();
                default -> {
                    OutputUtil.invalidInput();
                    continue;
                }
            }
        }
    }


    private void pokemonManagerOption() {
        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("0. Back");
            System.out.println("1. Display All Free Pokemons");
            System.out.println("2. Create new pokemon");
            System.out.println("3. Edit pokemon");
            System.out.println("4. Kill pokemon");

            int choice = InputUtils.readInt();

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> pokemonController.printFreePokemons();
                case 2 -> pokemonController.createNewPokemon();
                case 3 -> pokemonController.updatePokemon();
                case 4 -> pokemonController.deletePokemon();
                default -> {
                    OutputUtil.invalidInput();
                    continue;
                }
            }
        }
    }

    private void trainerManagerOption() {
        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("0. Back");
            System.out.println("1. List trainer pokemons");
            System.out.println("2. List trainers by number of pokemon");
            System.out.println("3. Register new trainer");

            final int choice = InputUtils.readInt();

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> trainerController.listTrainerPokemons();
                case 2 -> trainerController.listTrainersByNumOfPokemons();
                case 3 -> trainerController.registerNewTrainer();
                default -> {
                    OutputUtil.invalidInput();
                    return;
                }
            }
        }


    }
}
