package com.github.matcaban.pokemonmanager.controller;

import com.github.matcaban.pokemonmanager.domain.Pokemon;
import com.github.matcaban.pokemonmanager.service.DBPokemonService;
import com.github.matcaban.pokemonmanager.utility.InputUtils;
import com.github.matcaban.pokemonmanager.utility.OutputUtil;

import java.util.List;
import java.util.Random;

public class PokemonController {
    private final DBPokemonService service;

    public PokemonController() {
        this.service = new DBPokemonService();
    }

    public void printPokemonsWithoutTrainer() {
        List<Pokemon> freePokemon = service.getAllPokemon().stream()
                .filter(pokemon -> pokemon.getTrainerId() == 0)
                .toList();
        if (freePokemon.isEmpty()) {
            OutputUtil.noFreePokemons();
        } else {
            freePokemon.forEach(System.out::println);
        }

    }

    public void createNewPokemon() {
        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("So you saw a new Pokemon\nGreat, enter its name:");
            String name = InputUtils.readStringToTitleCase();

            int id = service.getIdOfName(name);

            if (id == -1) {
                System.out.println("I'm sorry, I don't have that name in my pokedex database of existing pokemons" +
                        "\nYou must have seen some other animal");
                continue;
            }

            System.out.println("Oh great a " + name + "\nTake a quick look at some identifying mark on it so we " +
                    "can tell it apart from the others of the same kind");
            String unique_trait = "";
            while (true) {
                unique_trait = InputUtils.readStringToLowerCase();
                if (unique_trait.isBlank()) {
                    System.out.println("Identifying mark can not be empty. Look again");
                } else {
                    break;
                }
            }


            if (service.createPokemon(id, unique_trait) > 0) {
                System.out.println("Pokemon successfully added to my pokedex. You can now go and catch it");
            }
            return;
        }
    }

    public void updatePokemon() {
        List<Pokemon> pokemons = service.getAllPokemon();

        if (pokemons.isEmpty()) {
            OutputUtil.noFreePokemons();
            return;
        }

        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("So do you think I wrote the identification marks of one of the " +
                    "\npokemon in the pokedex incorrectly? And which one?");

            System.out.println("0. Back");
            for (int i = 0; i < pokemons.size(); i++) {
                System.out.println((i + 1) + ". " + pokemons.get(i));
            }

            final int choice = InputUtils.readInt();

            if (choice == 0) {
                break;
            } else if (choice < 1 || choice > pokemons.size()) {
                OutputUtil.invalidInput();
                continue;
            }

            System.out.println("Write a correct identification marks");
            String newMarks = InputUtils.readStringToLowerCase();

            if (service.updateUniqueTrait(newMarks, pokemons.get(choice - 1).getId()) > 0) {
                System.out.println("Thank you, I successfully updated my pokedex");
            }
            break;
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

            if (choice < 0 || choice > 1) {
                OutputUtil.invalidInput();
                continue;
            }

            if (choice == 0) {
                System.out.println("Thank God you changed your mind. Bye");
                break;
            } else {
                choosePokemonToDelete();
                return;
            }
        }
    }

    public void catchPokemon(int trainerId) {
        while (true) {
            List<Pokemon> pokemons = this.getPokemonsWithoutTrainer();

            if (pokemons.isEmpty()) {
                OutputUtil.noFreePokemons();
                return;
            }

            int choice = this.chooseFreePokemon(pokemons);

            Random random = new Random();
            // 70% probability to catch pokemon
            if (random.nextInt(10) < 7) {
                if (service.updateTrainerId(pokemons.get(choice - 1).getId(), trainerId) > 0) {
                    System.out.println("You caught a pokemon");
                }
            } else {
                System.out.println("Bad luck. Your pokemon got away");
            }

            OutputUtil.lineSplitter();
            System.out.println("Do you want to try to catch another one?");
            System.out.println("0. No");
            System.out.println("1. Yes");

            while (true) {
                choice = InputUtils.readInt();
                if (choice < 0 || choice > 1) {
                    OutputUtil.invalidInput();
                } else {
                    break;
                }
            }


            if (choice == 0) {
                break;
            }
        }

    }

    public void setPokemonFree(List<Pokemon> pokemons) {
        pokemons.forEach(pokemon -> service.updateTrainerId(pokemon.getId(), 0));
    }

    private List<Pokemon> getPokemonsWithoutTrainer() {
        return service.getAllPokemon()
                .stream()
                .filter(pokemon -> pokemon.getTrainerId() == 0)
                .toList();
    }

    private void choosePokemonToDelete() {
        List<Pokemon> pokemons = this.getPokemonsWithoutTrainer();
        if (pokemons.isEmpty()) {
            OutputUtil.noFreePokemons();
            return;
        }

        final int choice = this.chooseFreePokemon(pokemons);

        if (choice == 0) {
            return;
        }

        if (service.deletePokemon(pokemons.get(choice - 1).getId()) > 0) {
            System.out.println("A moment of silence, please");
        }
    }

    private int chooseFreePokemon(List<Pokemon> pokemons) {
        int choice;
        while (true) {
            OutputUtil.lineSplitter();
            System.out.println("These are all freely moving pokemons");
            System.out.println("0. Back");
            for (int i = 0; i < pokemons.size(); i++) {
                System.out.println((i + 1) + ". " + pokemons.get(i));
            }

            System.out.println("What's your choice?");
            choice = InputUtils.readInt();

            if (choice == 0) {
                break;
            } else if (choice < 1 || choice > pokemons.size()) {
                OutputUtil.invalidInput();
                continue;
            }
            break;
        }
        return choice;
    }

}

