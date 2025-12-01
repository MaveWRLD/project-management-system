package models.utils;



import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class ValidationUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getValidInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Error: Input must be between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
            }
        }
    }

    public static String getValidString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Input cannot be empty.");
        }
    }


    public static int getValidInt(String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            int value = Integer.parseInt(input);
            if (value >= min) {
                return value;
            } else {
                System.out.println("Error: Input must be greater than or equal to " + min + ".");
            }
        }
    }

    public static Status getValidStatus(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            if (
                    Status.valueOf(input).equals(Status.PENDING)
                    || Status.valueOf(input).equals(Status.IN_PROGRESS)
                    || Status.valueOf(input).equals(Status.COMPLETED)
            ) {
                return Status.valueOf(input);
            }
            System.out.println("Error: Invalid status. Please choose from (Pending, In Progress, Completed).");
        }
    }

    public static boolean isValidId(String id, char prefix) {
        if (id.equals("0")) {
            return true;
        }
        if (id.length() != 4 || id.charAt(0) != prefix) {
            return false;
        }
        try {
            Integer.parseInt(id.substring(1));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getValidId(String prompt, char prefix) {
        while (true) {
            System.out.print(prompt);
            String id = scanner.nextLine().trim().toUpperCase();
            if (isValidId(id, prefix)) {
                return id;
            }
            System.out.println("Error: Invalid ID. Please enter a valid prefixed ID (e.g., " + prefix + "001).");
        }
    }

    private static String capitalize(String str) {
        String[] words = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}



