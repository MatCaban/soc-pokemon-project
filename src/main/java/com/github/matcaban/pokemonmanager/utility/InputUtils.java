package com.github.matcaban.pokemonmanager.utility;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputUtils {
    private final static Scanner sc = new Scanner(System.in);


    public static int readInt() {
        while (true) {
            try {
                int input = sc.nextInt();
                sc.nextLine();
                return input;
            } catch (Exception e) {
                OutputUtil.invalidInput();
                sc.nextLine();
            }
        }
    }

    public static String readStringToLowerCase() {
        return sc.nextLine().toLowerCase();
    }

    public static String readStringToTitleCase() {
        String userInput = sc.nextLine().toLowerCase();
        String[] userInputArray = userInput.split(" ");

        return Arrays.stream(userInputArray)
                .map(string -> {
                    return string.substring(0, 1).toUpperCase() + string.substring(1);
                })
                .collect(Collectors.joining(" "));

    }
}
