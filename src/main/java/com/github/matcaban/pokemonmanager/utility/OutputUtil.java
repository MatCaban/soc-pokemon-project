package com.github.matcaban.pokemonmanager.utility;

public class OutputUtil {

    private OutputUtil() {}

    public static void lineSplitter() {
        System.out.println("-".repeat(18));
    }

    public static void invalidInput() {
        System.out.println("Invalid input");
    }

    public static void noTrainerRegistered() {
        System.out.println("I don't currently have any trainers registered");
    }

    public static void noFreePokemons() {
        System.out.println("There are currently no free-roaming pokemons here");
    }
}
