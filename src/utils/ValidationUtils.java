package utils;

import utils.exceptions.InvalidInputException;

import java.util.Scanner;

public class ValidationUtils {
    private final Scanner scanner = new Scanner(System.in);

    public int getValidInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                throw new InvalidInputException("Error: Input must be between " + min + " and " + max + ".");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
            }
        }
    }

    public String getValidString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                    if (!input.isEmpty()) {
                        return input;
                    }
                    throw new InvalidInputException("Error: Input cannot be empty.");
            } catch (InvalidInputException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public int getValidInt(String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min) {
                    return value;
                }
                throw new InvalidInputException("Error: Input must be greater than or equal to " + min + ".");
            } catch (InvalidInputException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public String getValidType(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                if (input.equals("HARDWARE")) {
                    return input.toUpperCase();
                } else if (input.equals("SOFTWARE")) {
                    return input.toUpperCase();
                }
                throw new InvalidInputException("Error: Invalid status. Please choose from (Pending, In Progress, Completed).");
            } catch (InvalidInputException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public Status getValidStatus(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                if (
                        Status.valueOf(input).equals(Status.PENDING) ||
                        Status.valueOf(input).equals(Status.IN_PROGRESS) ||
                        Status.valueOf(input).equals(Status.COMPLETED)
                ) {return Status.valueOf(input);}
                throw new InvalidInputException("Error: Invalid status. Please choose from (Pending, In Progress, Completed).");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getValidId(String prompt, char prefix)  {
        while (true) {
            System.out.print(prompt);
            String id = scanner.nextLine().trim().toUpperCase();
            try {
            if (isValidId(id, prefix)) {
                return id;
            }
            throw new InvalidInputException("Error: Invalid ID. Please enter a valid prefixed ID (e.g., " + prefix + "001).");
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean isValidId(String id, char prefix) {
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
}



