/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author hazelbazelnessa
 */
public class MessageArrays {
 // Message arrays
    private static Messages[] sentMessages = new Messages[0];

    private static Messages[] disregardedMessages = new Messages[0];

    private static Messages[] storedMessages = new Messages[0];

    // Adds a message to an array
    private static Messages[] append(
            Messages[] original, Messages item) {

        Messages[] updated = Arrays.copyOf(
                original, original.length + 1);

        updated[original.length] = item;

        return updated;
    }

    // Adds sent message
    public static void addSentMessage(Messages message) {

        sentMessages = append(sentMessages, message);
    }

    // Adds disregarded message
    public static void addDisregardedMessage(Messages message) {

        disregardedMessages =
                append(disregardedMessages, message);
    }

    // Adds stored message
    public static void addStoredMessage(Messages message) {

        storedMessages = append(storedMessages, message);
    }

    // Gets sent messages
    public static Messages[] getSentMessages() {

        return Arrays.copyOf(
                sentMessages, sentMessages.length);
    }

    // Gets disregarded messages
    public static Messages[] getDisregardedMessages() {

        return Arrays.copyOf(
                disregardedMessages, disregardedMessages.length);
    }

    // Gets stored messages from memory and JSON
    public static Messages[] getStoredMessages()
            throws IOException {

        Messages[] jsonMessages =
                Messages.getAllStoredMessages();

        List<Messages> combined = new ArrayList<>();

        for (Messages message : storedMessages) {
            combined.add(message);
        }

        for (Messages message : jsonMessages) {

            boolean exists = false;

            for (Messages existing : combined) {

                if (existing.getMessageID().equals(
                        message.getMessageID())) {

                    exists = true;
                    break;
                }
            }

            if (!exists) {
                combined.add(message);
            }
        }

        return combined.toArray(new Messages[0]);
    }

    // Displays recently sent messages
    public static String displayRecentlySentMessages() {

        if (sentMessages.length == 0) {

            return "No messages have been sent yet.";
        }

        StringBuilder output = new StringBuilder();

        output.append("=== RECENTLY SENT MESSAGES ===\n");

        for (int i = 0; i < sentMessages.length; i++) {

            output.append("\nMessage ")
                    .append(i + 1)
                    .append("\n");

            output.append(sentMessages[i].details());

            output.append("\n");
        }

        return output.toString();
    }

    // Finds the longest sent message
    public static Messages getLongestMessage() {

        Messages longest = null;

        for (Messages message : sentMessages) {

            if (longest == null
                    || message.getMessage().length()
                    > longest.getMessage().length()) {

                longest = message;
            }
        }

        return longest;
    }

    // Gets all message IDs
    public static String[] getMessageIDs()
            throws IOException {

        List<String> ids = new ArrayList<>();

        addIDs(ids, sentMessages);

        addIDs(ids, disregardedMessages);

        addIDs(ids, getStoredMessages());

        return ids.toArray(new String[0]);
    }

    private static void addIDs(
            List<String> ids, Messages[] messages) {

        for (Messages message : messages) {

            if (!ids.contains(message.getMessageID())) {
                ids.add(message.getMessageID());
            }
        }
    }

    // Gets all message hashes
    public static String[] getMessageHashes()
            throws IOException {

        List<String> hashes = new ArrayList<>();

        addHashes(hashes, sentMessages);

        addHashes(hashes, disregardedMessages);

        addHashes(hashes, getStoredMessages());

        return hashes.toArray(new String[0]);
    }

    private static void addHashes(
            List<String> hashes, Messages[] messages) {

        for (Messages message : messages) {

            if (!hashes.contains(message.getMessageHash())) {
                hashes.add(message.getMessageHash());
            }
        }
    }

    // Searches for a message by ID
    public static Messages searchStoredMessageByID(String id)
            throws IOException {

        for (Messages message : sentMessages) {

            if (message.getMessageID().equals(id)) {
                return message;
            }
        }

        for (Messages message : disregardedMessages) {

            if (message.getMessageID().equals(id)) {
                return message;
            }
        }

        for (Messages message : getStoredMessages()) {

            if (message.getMessageID().equals(id)) {
                return message;
            }
        }

        return null;
    }

    // Searches stored messages by recipient
    public static Messages[] searchStoredMessagesByRecipient(
            String recipient) throws IOException {

        List<Messages> results = new ArrayList<>();

        for (Messages message : getStoredMessages()) {

            if (message.getRecipient().equals(recipient)) {
                results.add(message);
            }
        }

        return results.toArray(new Messages[0]);
    }

    // Deletes a stored message by hash
    public static boolean deleteStoredMessageByHash(String hash)
            throws IOException {

        boolean deleted =
                Messages.deleteStoredMessageByHash(hash);

        if (!deleted) {

            for (int i = 0; i < storedMessages.length; i++) {

                if (storedMessages[i].getMessageHash()
                        .equals(hash)) {

                    storedMessages = removeAt(
                            storedMessages, i);

                    return true;
                }
            }
        }

        if (deleted) {

            for (int i = 0; i < storedMessages.length; i++) {

                if (storedMessages[i].getMessageHash()
                        .equals(hash)) {

                    storedMessages = removeAt(
                            storedMessages, i);

                    break;
                }
            }
        }

        return deleted;
    }

    // Removes an array item
    private static Messages[] removeAt(
            Messages[] original, int index) {

        Messages[] updated = new Messages[
                original.length - 1];

        for (int i = 0, j = 0;
                i < original.length; i++) {

            if (i != index) {
                updated[j++] = original[i];
            }
        }

        return updated;
    }

    // Generates a complete message report
    public static String displayReport()
            throws IOException {

        StringBuilder report = new StringBuilder();

        report.append("=== MESSAGE REPORT ===\n");

        report.append("\nSENT MESSAGES\n");

        appendReport(report, sentMessages);

        report.append("\nDISREGARDED MESSAGES\n");

        appendReport(report, disregardedMessages);

        report.append("\nSTORED MESSAGES\n");

        appendReport(report, getStoredMessages());

        return report.toString();
    }

    private static void appendReport(
            StringBuilder report, Messages[] messages) {

        if (messages.length == 0) {

            report.append("No messages available.\n");

            return;
        }

        for (Messages message : messages) {

            report.append(message.details())
                    .append("\n\n");
        }
    }

    // Counts sent messages
    public static int getSentCount() {
        return sentMessages.length;
    }

    // Counts disregarded messages
    public static int getDisregardedCount() {
        return disregardedMessages.length;
    }
 // Counts stored messages
    public static int getStoredCount()
            throws IOException {

        return getStoredMessages().length;
    }
 // Resets all arrays
    public static void resetPart3Arrays() {

        sentMessages = new Messages[0];

        disregardedMessages = new Messages[0];

        storedMessages = new Messages[0];
    }
}
   
   
