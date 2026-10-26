package com.ecommerce.ui;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

public final class ConsoleInput {
    private ConsoleInput() {
    }

    public static String readMenuChoice(Scanner scanner, String prompt, String... validChoices) {
        System.out.print(prompt);
        System.out.flush();
        if (!scanner.hasNextLine()) {
            return null;
        }

        String choice = scanner.nextLine().trim();
        if (choice.isEmpty()) {
            System.out.println("Menu choice cannot be blank. Please choose a listed option.");
            return "";
        }
        if (!choice.matches("\\d+")) {
            System.out.println("Please enter a numeric menu choice.");
            return "";
        }
        if (!Set.copyOf(Arrays.asList(validChoices)).contains(choice)) {
            System.out.println("Invalid option. Please choose a listed option.");
            return "";
        }
        return choice;
    }
}
