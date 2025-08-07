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

    public void getPokemonsFromTrainer() {
        List<Trainer> trainers = service.getAllTrainersWithPokemons();

        if (trainers.isEmpty()) {
            OutputUtil.noTrainerRegistered();
            return;
        }

        final int choice = this.chooseTrainer(trainers, "Whose pokemons you want to see?");

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
        if (service.getAllTrainersWithPokemons().isEmpty()) {
            OutputUtil.noTrainerRegistered();
            return;
        }
        service.getAllTrainersWithPokemons()
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

            if (choice != 1) {
                OutputUtil.invalidInput();
                continue;
            }

            OutputUtil.lineSplitter();
            System.out.println("Registering as a new trainer is easy, just enter your name" +
                    "\nThe potential glory is in your hands.");
            String name = "";

            while (true) {
                name = InputUtils.readStringToTitleCase();
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
        List<Trainer> trainers = service.getAllTrainersWithPokemons();
        if (trainers.isEmpty()) {
            OutputUtil.noTrainerRegistered();
            return;
        }

        final int choice = this.chooseTrainer(trainers, "Which trainer is going to try to catch the pokemon");

        if (choice == 0) {
            return;
        }

        final int trainerId = trainers.get(choice - 1).getId();
        new PokemonController().catchPokemon(trainerId);
    }

    public void unregisterTrainer() {
        List<Trainer> trainers = service.getAllTrainersWithPokemons();
        System.out.println("So you decided to cancel your registration?\nIf you do, all your pokoemons will be free");
        final int choice = this.chooseTrainer(trainers, "Which trainer wants to cancel the registration");

        if (choice == 0) {
            return;
        }

        final String name = trainers.get(choice - 1).getName();
        final int id = trainers.get(choice - 1).getId();
        final List<Pokemon> pokemons = trainers.get(choice - 1).getPokemons();

        // delete trainer and set pokemon free only if trainer exists
        if (id > 0) {
            new PokemonController().setPokemonFree(pokemons);
            if (service.deleteTrainer(id) > 0) {
                System.out.println("Registration cancelling process finished");
                System.out.println(name + ", you are no longer registered trainer. All of your " +
                        pokemons.size() + " pokemons were set free");
            }
        }
    }

    public void editTrainer() {
        OutputUtil.lineSplitter();
        System.out.println("So, you've decided to change your name?" +
                "\nDo you realize that means a huge amount of paperwork, official visits," +
                "\ngetting things registered, and signing documents?" +
                "\nAre you really sure you want to go through with this?");
        System.out.println("0. Back");
        System.out.println("1. Yes");

        int choice = InputUtils.readInt();

        if (choice == 0) {
            return;
        }

        if (choice != 1) {
            OutputUtil.invalidInput();
        }

        List<Trainer> trainers = service.getAllTrainersWithPokemons();

        choice = this.chooseTrainer(trainers, "Ok than, select trainer whose name you want to edit");

        if (choice == 0) {
            return;
        }

        System.out.println("Enter your new name and prepare for a lengthy paperwork process");
        String name = InputUtils.readStringToTitleCase();

        if (service.updateTrainer(name, trainers.get(choice - 1).getId()) > 0){
            System.out.println("Congratulations, you have successfully changed your name");
        }
    }



     // Displays a list of trainers along with a prompt and allows the user to make a selection.
     // The method repeatedly asks for input until a valid trainer is selected or the user chooses to go back.

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
