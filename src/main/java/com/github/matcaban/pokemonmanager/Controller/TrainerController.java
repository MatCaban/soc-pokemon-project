package com.github.matcaban.pokemonmanager.Controller;

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
        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("Which trainer do you want to see?");
            System.out.println("0. Back");

            for (int i = 0; i < trainers.size(); i++) {
                System.out.println((i + 1) + ". " + trainers.get(i).getName());
            }
            final int choice = InputUtils.readInt();

            if (choice == 0) {
                break;
            }

            if (choice < 1 || choice > trainers.size()) {
                System.out.println("Invalid input");
                continue;
            }

            trainers.get(choice - 1).getPokemons().forEach(System.out::println);
        }
    }

    public void listTrainersByNumOfPokemons() {
        service.getAllTrainers()
                .stream()
                .sorted((a, b) -> b.getPokemons().size() - a.getPokemons().size())
                .forEach( trainer ->
                        System.out.println(trainer.getName() + " has " + trainer.getPokemons().size() + " pokemons captured")
                );
    }
}
