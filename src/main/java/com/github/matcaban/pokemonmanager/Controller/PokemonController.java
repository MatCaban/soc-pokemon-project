package com.github.matcaban.pokemonmanager.Controller;

import com.github.matcaban.pokemonmanager.domain.Pokemon;
import com.github.matcaban.pokemonmanager.service.DBPokemonService;
import com.github.matcaban.pokemonmanager.utility.InputUtils;
import com.github.matcaban.pokemonmanager.utility.OutputUtil;

import javax.crypto.spec.PSource;
import javax.sound.midi.Soundbank;
import java.util.List;

public class PokemonController {
    private final DBPokemonService service;

    public PokemonController() {
        this.service = new DBPokemonService();
    }

    public void printFreePokemons() {
        if (service.getAllPokemon().isEmpty()) {
            System.out.println("There are currently no free-roaming pokemons here.");
        } else {
            service.getAllPokemon().stream()
                    .filter(pokemon -> pokemon.getTrainerId() == 0)
                    .forEach(System.out::println);
        }

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
                        System.out.println("I'm sorry, I don't have that name in my pokedex database of existing pokemons" +
                                "\nYou must have seen some other animal");
                        continue;
                    }

                    System.out.println("Oh great a " + name + "\nTake a quick look at some identifying mark on it so we " +
                            "can tell it apart from the others of the same kind");

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

    public void updatePokemon() {
        List<Pokemon> pokemons = service.getAllPokemon();

        if (pokemons.isEmpty()) {
            System.out.println("Sorry, there are currently no free-roaming pokemon available");
            return;
        }

        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("So do you think I wrote the identification marks of one of the " +
                    "\nPokemon in the pokedex incorrectly? And which one?");

            System.out.println("0. Back");
            for (int i = 0; i < pokemons.size(); i++) {
                System.out.println((i + 1) + ". " + pokemons.get(i));
            }

            final int choice = InputUtils.readInt();

            if (choice == 0) {
                break;
            } else if (choice < 1 || choice > pokemons.size()) {
                System.out.println("Invalid input try again");
                continue;
            }

            System.out.println("Write a new one identification marks");
            String newMarks = InputUtils.readStringToLowerCase();

            if (service.updateUniqueTrait(newMarks, pokemons.get(choice - 1).getId()) > 0 ) {
                System.out.println("Thank you, I successfully updated my pokedex");
                break;
            }
        }

    }

    public void deletePokemon() {
        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("You can only kill free-roaming pokemon," +
                    "\nand only if there is no other option");
            System.out.println("Do you want to continue?");
            System.out.println("0. Back");
            System.out.println("1. Yes");

            int choice = InputUtils.readInt();

            if (choice == 0) {
                System.out.println("Thank God you changed your mind. Bye");
                break;
            } else if (choice == 1) {
                choosePokemonToDelete();
            } else {
                System.out.println("Invalid input");
                continue;
            }
        }
    }

    private void choosePokemonToDelete() {
        List<Pokemon> pokemons = service.getAllPokemon()
                .stream()
                .filter(pokemon -> pokemon.getTrainerId() == 0)
                .toList();
        if (pokemons.isEmpty()) {
            System.out.println("Sorry, there are currently no free-roaming pokemon\n" +
                    "It looks like everyone is dead.");
            return;
        }

        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("These are all freely moving Pokemons");
            System.out.println("0. Back");
            for (int i = 0; i < pokemons.size(); i++) {
                System.out.println((i + 1) + ". " + pokemons.get(i));
            }

            System.out.println("Make up your mind: ");
            final int choice = InputUtils.readInt();

            if (choice == 0) {
                break;
            } else if (choice < 1 || choice > pokemons.size()) {
                System.out.println("Invalid Input");
                continue;
            }

            if (service.delete(pokemons.get(choice - 1).getId()) > 0) {
                System.out.println("A moment of silence, please");
                return;
            }
        }

    }

}

