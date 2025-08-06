package com.github.matcaban.pokemonmanager.utility;

import java.util.Scanner;

public class InputUtils {
    private final static Scanner sc = new Scanner(System.in);

    public static String readString() {
        return sc.nextLine();
    }

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
}
