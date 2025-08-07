package com.github.matcaban.pokemonmanager.Controller;

import com.github.matcaban.pokemonmanager.domain.Pokemon;
import com.github.matcaban.pokemonmanager.domain.Trainer;
import com.github.matcaban.pokemonmanager.service.DBTrainerService;
import com.github.matcaban.pokemonmanager.utility.InputUtils;
import com.github.matcaban.pokemonmanager.utility.OutputUtil;

import java.util.List;

public class TrainerController {
    private final DBTrainerService service;

    public TrainerController() {
        this.service = new DBTrainerService();
    }

    public void listTrainerPokemons() {
        List<Trainer> trainers = service.getAllTrainers();

        if (trainers.isEmpty()) {
            OutputUtil.noTrainerRegistered();
            return;
        }

        final int choice = this.chooseTrainer(trainers, "Which trainer do you want to see?");

        if (choice == 0) {
            return;
        } else {
            List<Pokemon> trainersPokemon = trainers.get(choice - 1).getPokemons();
            if (trainersPokemon.isEmpty()) {
                System.out.println("This trainer has no pokemons");
                return;
            } else {
                trainersPokemon.forEach(System.out::println);
            }
        }

    }

    public void listTrainersByNumOfPokemons() {
        if (service.getAllTrainers().isEmpty()) {
            OutputUtil.noTrainerRegistered();
            return;
        }
        service.getAllTrainers()
                .stream()
                .sorted((a, b) -> b.getPokemons().size() - a.getPokemons().size())
                .forEach(trainer ->
                        System.out.println(trainer.getName() + " has " + trainer.getPokemons().size() + " pokemons captured")
                );
    }

    public void registerNewTrainer() {
        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("Welcome to registration formular" +
                    "\nDo you want to: ");
            System.out.println("0. Go Back");
            System.out.println("1. Register new trainer");

            final int choice = InputUtils.readInt();

            if (choice == 0) {
                break;
            }

            if (choice < 1 || choice > 1) {
                OutputUtil.invalidInput();
                continue;
            }

            OutputUtil.lineSplitter();
            System.out.println("Registering as a new trainer is easy, just enter your name" +
                    "\nThe potential glory is in your hands.");
            String name = "";

            while (true) {
                name = InputUtils.readString();
                if (name.isBlank()) {
                    System.out.println("You must enter your name");
                } else {
                    break;
                }
            }

            if (service.createTrainer(name) > 0) {
                System.out.println("Trainer successfully registered\nWelcome and good luck");
            }
            return;
        }
    }

    public void catchPokemon() {
        List<Trainer> trainers = service.getAllTrainers();
        if (trainers.isEmpty()) {
            OutputUtil.noTrainerRegistered();
            return;
        }

        final int choice = this.chooseTrainer(trainers,"Which trainer is going to try to catch the pokemon");

        if (choice == 0) {
            return;
        }

        final int trainerId = trainers.get(choice - 1).getId();
        new PokemonController().catchPokemon(trainerId);
    }

    public void unregisterTrainer() {
        List<Trainer> trainers = service.getAllTrainers();
        System.out.println("So you decided to cancel your registration?\nIf you do, all your pokoemons will be free");
        final int choice =  this.chooseTrainer(trainers, "Which trainer wants to cancel the registration");

        if (choice == 0) {
            return;
        }

        final String name = trainers.get(choice - 1).getName();
        final List<Pokemon> pokemons = trainers.get(choice - 1).getPokemons();
        System.out.println("Registration cancelling process finished");
        System.out.println(name + ", you are no longer registered trainer. All of your " +
                pokemons.size() + " pokemons were set free");
        new PokemonController().setPokemonFree(pokemons);
    }

    // helper method for printing all trainers
    private int chooseTrainer(List<Trainer> trainers, String prompt) {
        int choice = 0;
        while (true) {
            OutputUtil.lineSplitter();
            System.out.println(prompt);
            System.out.println("0. Back");

            for (int i = 0; i < trainers.size(); i++) {
                System.out.println((i + 1) + ". " + trainers.get(i).getName());
            }
            choice = InputUtils.readInt();

            if (choice == 0) {
                break;
            }

            if (choice < 1 || choice > trainers.size()) {
                OutputUtil.invalidInput();
                continue;
            }
            break;
        }
       return choice;
    }
}
