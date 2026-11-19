/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author hazelbazelnessa
 */
public class Main {
 public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("== Hazel's ChatApp Registration  ==");

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

        login user = new login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        System.out.println();
        System.out.println(user.getUsernameMessage());
        System.out.println(user.getPasswordMessage());
        System.out.println(user.getCellPhoneMessage());

        if (user.checkUserName()
                && user.checkPasswordComplexity()
                && user.checkCellPhoneNumber()) {

            user.registerUser();

            System.out.println("Registration successful!");

            System.out.println();
            System.out.println("=== Hazel's ChatApp: Login ===");

            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            boolean success = user.loginUser(
                    loginUsername,
                    loginPassword
            );

            System.out.println(user.returnLoginStatus(success));

            if (success) {
                runQuickChat(scanner);
            }

        } else {
            System.out.println(
                    "Registration failed. Please fix the errors above and try again."
            );
        }

        scanner.close();
    }

    // ==================== PART 2 AND PART 3 ====================

    private static void runQuickChat(Scanner scanner) {

        System.out.println();
        System.out.println("Welcome to QuickChat.");

        Messages.setScanner(scanner);

        int maxMessages = readPositiveInt(
                scanner,
                "How many messages do you wish to enter? "
        );

        int messagesEntered = 0;

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("Please choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Longest sent message");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":

                    messagesEntered = sendMessages(
                            scanner,
                            maxMessages,
                            messagesEntered
                    );

                    break;
case "2":
    System.out.println("=== RECENTLY SENT MESSAGES ===");

    Messages[] recentMessages = MessageArrays.getSentMessages();

    if (recentMessages.length == 0) {
        System.out.println("No messages have been sent yet.");
    } else {
        for (Messages message : recentMessages) {
            System.out.println("----------------------------");
            System.out.println("Message ID: " + message.getMessageID());
            System.out.println("Message Hash: " + message.getMessageHash());
            System.out.println("Recipient: " + message.getRecipient());
            System.out.println("Message: " + message.getMessage());
        }
    }
    break;
                case "3":

                    System.out.println("Goodbye.");

                    running = false;

                    break;

                case "4":
                    storedMessagesMenu(scanner);
                    break;

                default:

                    System.out.println(
                            "Invalid option. Please enter "
                                    + "1, "
                                    + "2, 3 "
                                    + "or 4."
                    );
            }
        }
    }

    // ==================== SEND MESSAGES ====================

    private static int sendMessages(
            Scanner scanner,
            int maxMessages,
            int messagesEntered) {

        if (messagesEntered >= maxMessages) {

            System.out.println(
                    "You have already entered all "
                    + maxMessages + " messages."
            );

            return messagesEntered;
        }

        while (messagesEntered < maxMessages) {

            System.out.println();

            System.out.println(
                    "--- Message " + (messagesEntered + 1)
                    + " of " + maxMessages + " ---"
            );

            String recipient = readRecipient(scanner);

            String text = readMessageText(scanner);

            Messages message = new Messages(recipient, text);

            String result = message.SentMessage();

            messagesEntered++;

            System.out.println(result);

            if (result.equals("Message successfully sent.")) {
                MessageArrays.addSentMessage(message);
                System.out.println();
                System.out.println(message.details());
            } else if (result.equals("Message successfully stored.")) {
                MessageArrays.addStoredMessage(message);
                System.out.println(message.details());
            } else if (result.equals("Message deleted.") || result.equals("Press 0 to delete the message.")) {
                MessageArrays.addDisregardedMessage(message);
            }
        }

        System.out.println();

        System.out.println(
                "Total number of messages sent: "
                + Messages.returnTotalMessages()
        );

        return messagesEntered;
    }

    // ==================== PART 3 MESSAGE ARRAYS ====================

    private static void displayMessageArrays() {

        try {
            
            System.out.println();
            System.out.println("================================");
            System.out.println("       MESSAGE ARRAYS REPORT");
            System.out.println("================================");
            
            System.out.println(
                    MessageArrays.displayReport()
            );
            
            System.out.println(
                    "Total sent messages: "
                            + MessageArrays.getSentMessages().length
            );
            
            System.out.println(
                    "Total disregarded messages: "
                            + MessageArrays.getDisregardedMessages().length
            );
            
            try {
                System.out.println(
                        "Total stored messages: "
                                + MessageArrays.getStoredMessages().length
                );
            } catch (java.io.IOException e) {
                System.out.println("Error loading stored messages: " + e.getMessage());
            }
            System.out.println();
            
            System.out.println("Message IDs:");
            
            for (String id : MessageArrays.getMessageIDs()) {
                System.out.println(id);
            }
            
            System.out.println();
            
            System.out.println("Message Hashes:");
            
            for (String hash : MessageArrays.getMessageHashes()) {
                System.out.println(hash);
            }
            
            System.out.println();
            
            Messages longest = MessageArrays.getLongestMessage();
            
            if (longest != null) {
                
                System.out.println("Longest Message:");
                
                System.out.println(longest.details());
                
            } else {
                
                System.out.println("No messages available.");
            }
            
            System.out.println("================================");
        } catch (IOException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    // ==================== RECIPIENT VALIDATION ====================

    private static String readRecipient(Scanner scanner) {

        while (true) {

            System.out.print(
                    "Enter the recipient's cell number "
                    + "(e.g. +27718693002): "
            );

            String cell = scanner.nextLine().trim();

            if (Messages.isValidCell(cell)) {

                System.out.println(
                        "Cell phone number successfully captured."
                );

                return cell;
            }

            System.out.println(
                    "Cell phone number is incorrectly formatted "
                    + "or does not contain an international code. "
                    + "Please correct the number and try again."
            );
        }
    }

    // ==================== MESSAGE VALIDATION ====================

    private static String readMessageText(Scanner scanner) {

        while (true) {

            System.out.print(
                    "Enter your message (max 250 characters): "
            );

            String text = scanner.nextLine();

            if (Messages.isValidLength(text)) {

                System.out.println("Message captured.");

                return text;
            }

            System.out.println(
                    "Please enter a message of less than 250 characters."
            );
        }
    }

    // ==================== NUMBER VALIDATION ====================

    private static int readPositiveInt(
            Scanner scanner,
            String prompt) {

        while (true) {

            System.out.print(prompt);

            try {

                int value = Integer.parseInt(
                        scanner.nextLine().trim()
                );

                if (value > 0) {
                    return value;
                }

            } catch (NumberFormatException ignored) {

                // Display error below
            }

            System.out.println(
                    "Please enter a whole number greater than 0."
            );
        }
    }

    private static void storedMessagesMenu(Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n=== STORED MESSAGES ===");
            System.out.println("1) Display sender/recipient and all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message by ID");
            System.out.println("4) Search stored messages by recipient");
            System.out.println("5) Delete a stored message using its hash");
            System.out.println("6) Display full stored-message report");
            System.out.println("7) Return to main menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1":
                        Messages[] stored = MessageArrays.getStoredMessages();
                        if (stored.length == 0) System.out.println("No stored messages.");
                        for (Messages m : stored) System.out.println("Recipient: " + m.getRecipient() + "\nMessage: " + m.getMessage() + "\n");
                        break;
                    case "2":
                        Messages longest = null;
                        for (Messages m : MessageArrays.getStoredMessages())
                            if (longest == null || m.getMessage().length() > longest.getMessage().length()) longest = m;
                        System.out.println(longest == null ? "No stored messages." : longest.details());
                        break;
                    case "3":
                        System.out.print("Enter message ID: ");
                        Messages found = MessageArrays.searchStoredMessageByID(scanner.nextLine().trim());
                        System.out.println(found == null ? "Message not found." : found.details());
                        break;
                    case "4":
                        System.out.print("Enter recipient number: ");
                        Messages[] matches = MessageArrays.searchStoredMessagesByRecipient(scanner.nextLine().trim());
                        if (matches.length == 0) System.out.println("No messages found for that recipient.");
                        for (Messages m : matches) System.out.println(m.details() + "\n");
                        break;
                    case "5":
                        System.out.print("Enter message hash: ");
                        boolean deleted = MessageArrays.deleteStoredMessageByHash(scanner.nextLine().trim());
                        System.out.println(deleted ? "Message successfully deleted." : "Message hash not found.");
                        break;
                    case "6":
                        System.out.println(MessageArrays.displayReport());
                        break;
                    case "7":
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Please enter a number from 1 to 7.");
                }
  } catch (IOException ex) {
                System.out.println("Unable to access stored messages: " + ex.getMessage());
            
    