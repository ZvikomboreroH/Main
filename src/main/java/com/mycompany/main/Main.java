/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;
import java.util.Scanner;
/**
 *
 * @author Student
 */
/**
 * Demo console app for Part 1 - Registration and Login feature.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Login login = register(scanner);
        if (login == null) {
            scanner.close();
            return;
        }

        if (!loginUser(scanner, login)) {
            // Login failed: messages are only available after a successful login.
            scanner.close();
            return;
        }

        // Only reached after a successful login.
        runQuickChat(scanner);
        scanner.close();
    }

    // ---------------------------------------------------------------- Part 1

    private static Login register(Scanner scanner) {
        System.out.println("=== Chat App: Registration ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username (must contain '_' and be <= 5 characters): ");
        String username = scanner.nextLine();

        System.out.print("Enter password (min 8 chars, 1 capital, 1 number, 1 special char): ");
        String password = scanner.nextLine();

        System.out.print("Enter South African cell number (e.g. +27838968976): ");
        String cellPhoneNumber = scanner.nextLine();

        Login login = new Login(firstName, lastName, username, password, cellPhoneNumber);

        System.out.println();
        System.out.println(login.getUsernameMessage());
        System.out.println(login.getPasswordMessage());
        System.out.println(login.getCellPhoneMessage());

        if (login.checkUserName() && login.checkPasswordComplexity() && login.checkCellPhoneNumber()) {
            login.registerUser();
            System.out.println("Registration successful!");
            return login;
        }
        System.out.println("Registration failed. Please fix the errors above and try again.");
        return null;
    }

    private static boolean loginUser(Scanner scanner, Login login) {
        System.out.println();
        System.out.println("=== Chat App: Login ===");

        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        boolean success = login.loginUser(loginUsername, loginPassword);
        System.out.println(login.returnLoginStatus(success));
        return success;
    }

    // ---------------------------------------------------------------- Part 2

    private static void runQuickChat(Scanner scanner) {
        System.out.println();
        System.out.println("Welcome to QuickChat.");

        Message.setScanner(scanner);

        int maxMessages = readPositiveInt(scanner,
                "How many messages do you wish to enter? ");
        int messagesEntered = 0;

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("Please choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    messagesEntered = sendMessages(scanner, maxMessages, messagesEntered);
                    break;
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                case "3":
                    System.out.println("Goodbye.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2 or 3.");
            }
        }
    }

    /**
     * Lets the user enter the remaining messages (never more than the number
     * they chose at the start). Returns the new count of messages entered.
     */
    private static int sendMessages(Scanner scanner, int maxMessages, int messagesEntered) {
        if (messagesEntered >= maxMessages) {
            System.out.println("You have already entered all " + maxMessages + " messages.");
            return messagesEntered;
        }

        while (messagesEntered < maxMessages) {
            System.out.println();
            System.out.println("--- Message " + (messagesEntered + 1) + " of " + maxMessages + " ---");

            String recipient = readRecipient(scanner);
            String text = readMessageText(scanner);

            Message message = new Message(recipient, text);
            String result = message.SentMessage();
            messagesEntered++;

            System.out.println(result);
            if (result.equals("Message successfully sent.")) {
                System.out.println();
                System.out.println(message.details());
            }
        }

        System.out.println();
        System.out.println("Total number of messages sent: " + Message.returnTotalMessages());
        return messagesEntered;
    }

    private static String readRecipient(Scanner scanner) {
        while (true) {
            System.out.print("Enter the recipient's cell number (e.g. +27718693002): ");
            String cell = scanner.nextLine().trim();
            if (Message.isValidCell(cell)) {
                System.out.println("Cell phone number successfully captured.");
                return cell;
            }
            System.out.println("Cell phone number is incorrectly formatted or does not contain an "
                    + "international code. Please correct the number and try again.");
        }
    }

    private static String readMessageText(Scanner scanner) {
        while (true) {
            System.out.print("Enter your message (max 250 characters): ");
            String text = scanner.nextLine();
            if (Message.isValidLength(text)) {
                System.out.println("Message sent");
                return text;
            }
            System.out.println("Please enter a message of less than 250 characters.");
        }
    }
 private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            System.out.println("Please enter a whole number greater than 0.");
        }
    }
}


   