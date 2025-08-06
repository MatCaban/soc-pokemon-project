package com.github.matcaban.pokemonmanager.service;

import com.github.matcaban.pokemonmanager.utility.InputUtils;

public class PokemonManager {

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
                    return;}
                case 1 -> pokemonManagerOption();
                case 2 -> trainerManagerOption();
                default -> {
                    System.out.println("Invalid input");
                    continue;
                }
            }
        }
    }


    private void pokemonManagerOption(){
        System.out.println("pokemon manager");
    }

    private void trainerManagerOption(){
        System.out.println("trainer manager");
    }
}
