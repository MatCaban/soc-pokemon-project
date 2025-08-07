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
                System.out.println("Invalid input!");
                System.out.println("Try again");
                sc.nextLine();
            }
        }
    }

    public static String readStringToLowerCase() {
        return sc.nextLine().toLowerCase();
    }

    public static String readStringToTitleCase() {
        String s = sc.nextLine().toLowerCase();
        String[] arrS = s.split(" ");

        return Arrays.stream(arrS)
                .map(string -> {
                    return string.substring(0, 1).toUpperCase() + string.substring(1);
                })
                .collect(Collectors.joining(" "));

    }
}
