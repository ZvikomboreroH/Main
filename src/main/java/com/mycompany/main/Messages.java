/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONObject;
import org.json.JSONException;
/**
 *
 * @author hazelbazelnessa
 */
public class Messages {
    
   public static final int SEND = 1;
    public static final int DISREGARD = 2;
    public static final int STORE = 3;

    private static final SecureRandom RANDOM = new SecureRandom();
    private static Scanner input = new Scanner(System.in);
    private static int numMessagesSent = 0;
    private static String storeFile = "storedMessages.json";

    private String messageID;
    private String messageHash;
    private int messageNumber;
    private String recipient;
    private String message;
    private String status = "Pending";

    /** Creates a message with a randomly generated ten-digit ID. */
    public Messages(String recipient, String message) {
        this(generateID(), numMessagesSent, recipient, message);
    }

    /** Constructor retained for the assignment's supplied unit-test data. */
    public Messages(String messageID, String recipient, String message) {
        this(messageID, numMessagesSent, recipient, message);
    }

    public Messages(String messageID, int messageNumber, String recipient, String message) {
        this.messageID = messageID == null ? "" : messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient == null ? "" : recipient;
        this.message = message == null ? "" : message;
        this.messageHash = createMessageHash();
    }

    private static String generateID() {
        long value = RANDOM.nextLong(1_000_000_000L, 10_000_000_000L);
        return Long.toString(value);
    }

    public boolean checkMessageID() {
        return messageID.matches("\\d{1,10}");
    }

    public String getMessageIDStatus() {
        return checkMessageID() ? "Message ID generated: " + messageID
                : "Message ID is invalid; it must be no more than ten digits.";
    }

    public boolean isMessageLengthValid() {
        return message != null && message.length() <= 250;
    }

    public String checkMessageLength() {
        if (isMessageLengthValid()) return "Message ready to send.";
        return "Message exceeds 250 characters by " + (message.length() - 250)
                + "; please reduce the size.";
    }

    public static boolean isValidLength(String text) {
        return text != null && !text.trim().isEmpty() && text.length() <= 250;
    }

    public boolean checkRecipientCellValid() {
        return recipient.matches("^\\+27\\d{9}$");
    }

    public String checkRecipientCell() {
        return checkRecipientCellValid()
                ? "Cell phone number successfully captured."
                : "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public static boolean isValidCell(String cell) {
        return cell != null && cell.matches("^\\+27\\d{9}$");
    }

    /** Hash format required by the brief: first two ID digits:number:first+last word. */
    public String createMessageHash() {
        String[] words = message.trim().isEmpty() ? new String[]{""} : message.trim().split("\\s+");
        String first = words[0].replaceAll("[^\\p{Alnum}]", "");
        String last = words[words.length - 1].replaceAll("[^\\p{Alnum}]", "");
        return messageID.substring(0, Math.min(2, messageID.length())) + ":"
                + messageNumber + ":" + (first + last).toUpperCase();
    }

    public String SentMessage() {
        while (true) {
            System.out.println("Choose an option: 1) Send Message "
                    + " 2) Disregard Message "
                    + " 3) Store Message");
            String choice = input.hasNextLine() ? input.nextLine().trim() : "1";
            try {
                int selected = Integer.parseInt(choice);
                String result = SentMessage(selected);
                if (selected == DISREGARD && result.equals("Press 0 to delete the message.")) {
                    System.out.println(result);
                    String confirmation = input.hasNextLine() ? input.nextLine().trim() : "0";
                    return "0".equals(confirmation) ? "Message deleted." : "Message was not deleted.";
                }
                if (result.startsWith("Invalid option")) {
                    System.out.println(result);
                    continue;
                }
                return result;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid option. Please enter 1, "
                        + "2"
                        + " or 3.");
            }
        }
    }

    public String SentMessage(int choice) {
        if (choice == DISREGARD) {
            status = "Disregarded";
            return "Press 0 to delete the message.";
        }
        if (!checkMessageID() || !checkRecipientCellValid() || !isMessageLengthValid() || message.trim().isEmpty()) {
            return !checkRecipientCellValid() ? checkRecipientCell()
                    : "Please ensure the message ID and message are valid and the message is no more than 250 characters.";
        }
        switch (choice) {
            case SEND:
                status = "Sent";
                messageNumber = numMessagesSent;
                messageHash = createMessageHash();
                numMessagesSent++;
                sentForReport.add(this);
                return "Message successfully sent.";
            case STORE:
                try {
                    status = "Stored";
                    storeMessage();
                    return "Message successfully stored.";
                } catch (IOException ex) {
                    status = "Pending";
                    return "Message could not be stored: " + ex.getMessage();
                }
            default:
                return "Invalid option. Please enter 1,"
                        + " 2"
                        + " or 3.";
        }
    }

    public void storeMessage() throws IOException {
        Path path = Paths.get(storeFile);
        JSONObject all = readJsonFile(path);
        all.put(messageID, toJSON());
        Files.writeString(path, all.toString(2), StandardCharsets.UTF_8);
        status = "Stored";
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("messageID", messageID);
        json.put("messageHash", messageHash);
        json.put("messageNumber", messageNumber);
        json.put("recipient", recipient);
        json.put("message", message);
        json.put("status", status);
        return json;
    }

    public static Messages fromJSON(JSONObject json) throws JSONException {
        Messages result = new Messages(json.getString("messageID"), json.getInt("messageNumber"),
                json.getString("recipient"), json.getString("message"));
        result.status = json.optString("status", "Stored");
        result.messageHash = json.optString("messageHash", result.createMessageHash());
        return result;
    }

    private static JSONObject readJsonFile(Path path) throws IOException {
        if (!Files.exists(path) || Files.size(path) == 0) return new JSONObject();
        try { return new JSONObject(Files.readString(path, StandardCharsets.UTF_8)); }
        catch (JSONException ex) { throw new IOException("Invalid JSON storage file.", ex); }
    }

    public static Messages readStoredMessage(String id) throws IOException {
        JSONObject all = readJsonFile(Paths.get(storeFile));
        if (!all.has(id)) return null;
        try { return fromJSON(all.getJSONObject(id)); }
        catch (JSONException ex) { throw new IOException("Could not read stored message.", ex); }
    }

    public static Messages[] getAllStoredMessages() throws IOException {
        JSONObject all = readJsonFile(Paths.get(storeFile));
        List<Messages> result = new ArrayList<>();
        for (String id : all.keySet()) {
            try { result.add(fromJSON(all.getJSONObject(id))); }
            catch (JSONException ex) { throw new IOException("Could not load stored message.", ex); }
        }
        return result.toArray(new Messages[0]);
    }

    public static int countStoredMessages() throws IOException { return getAllStoredMessages().length; }

    public static boolean deleteStoredMessageByHash(String hash) throws IOException {
        Path path = Paths.get(storeFile);
        JSONObject all = readJsonFile(path);
        for (String id : new ArrayList<>(all.keySet())) {
            if (hash.equals(all.getJSONObject(id).optString("messageHash"))) {
                all.remove(id);
                Files.writeString(path, all.toString(2), StandardCharsets.UTF_8);
                return true;
            }
        }
        return false;
    }

    public String details() {
        return "Message ID: " + messageID + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient + "\nMessage: " + message;
    }

    private static final List<Messages> sentForReport = new ArrayList<>();
    public static String printMessages() {
        if (sentForReport.isEmpty()) return "No messages have been sent.";
        StringBuilder out = new StringBuilder();
        for (Messages m : sentForReport) out.append(m.details()).append("\n");
        return out.toString().trim();
    }

   