/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.main;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author hazelbazelnessa
 */
public class MessageArraysTest {
    
    public MessageArraysTest() {
    }

    @TempDir
    Path tempDir;

    private Messages message1;
    private Messages message2;
    private Messages message3;

    @BeforeEach
    public void setUp() {

        MessageArrays.resetPart3Arrays();
        Messages.resetSession();

        Messages.setStoreFile(
            tempDir.resolve("storedMessages.json").toString()
        );

        message1 = new Messages(
            "1234567890",
            "+27838968976",
            "Hello this is my first message"
        );

        message2 = new Messages(
            "1234567891",
            "+27821234567",
            "This is my second message"
        );

        message3 = new Messages(
            "1234567892",
            "+27831234567",
            "This is a much longer message for testing"
        );
    }

    // TEST 1: Add a sent message
    @Test
    public void testAddSentMessage() {

        MessageArrays.addSentMessage(message1);

        assertEquals(1, MessageArrays.getSentCount());
        assertEquals(message1,
            MessageArrays.getSentMessages()[0]);
    }

    // TEST 2: Add a disregarded message
    @Test
    public void testAddDisregardedMessage() {

        MessageArrays.addDisregardedMessage(message2);

        assertEquals(1,
            MessageArrays.getDisregardedCount());

        assertEquals(message2,
            MessageArrays.getDisregardedMessages()[0]);
    }

    // TEST 3: Add a stored message
    @Test
    public void testAddStoredMessage() throws IOException {

        MessageArrays.addStoredMessage(message3);

        Messages[] stored =
            MessageArrays.getStoredMessages();

        assertEquals(1, stored.length);

        assertEquals(message3.getMessageID(),
            stored[0].getMessageID());
    }

    // TEST 4: Verify sent messages return a copy
    @Test
    public void testGetSentMessagesReturnsCopy() {

        MessageArrays.addSentMessage(message1);

        Messages[] messages =
            MessageArrays.getSentMessages();

        messages[0] = message2;

        assertEquals(message1,
            MessageArrays.getSentMessages()[0]);
    }

    // TEST 5: Display recently sent messages
    @Test
    public void testDisplayRecentlySentMessages() {

        MessageArrays.addSentMessage(message1);

        String result =
            MessageArrays.displayRecentlySentMessages();

        assertTrue(result.contains(
            "RECENTLY SENT MESSAGES"));

        assertTrue(result.contains(
            message1.getMessage()));
    }

    // TEST 6: Display recently sent messages when empty
    @Test
    public void testDisplayNoSentMessages() {

        String result =
            MessageArrays.displayRecentlySentMessages();

        assertEquals(
            "No messages have been sent yet.",
            result
        );
    }

    // TEST 7: Find the longest sent message
    @Test
    public void testGetLongestMessage() {

        MessageArrays.addSentMessage(message1);
        MessageArrays.addSentMessage(message2);
        MessageArrays.addSentMessage(message3);

        Messages longest =
            MessageArrays.getLongestMessage();

        assertEquals(
            message3.getMessage(),
            longest.getMessage()
        );
    }

    // TEST 8: Check longest message when array is empty
    @Test
    public void testGetLongestMessageEmpty() {

        assertNull(MessageArrays.getLongestMessage());
    }

    // TEST 9: Retrieve all message IDs
    @Test
    public void testGetMessageIDs() throws IOException {

        MessageArrays.addSentMessage(message1);
        MessageArrays.addDisregardedMessage(message2);
        MessageArrays.addStoredMessage(message3);

        String[] ids =
            MessageArrays.getMessageIDs();

        assertEquals(3, ids.length);

        assertTrue(Arrays.asList(ids)
            .contains(message1.getMessageID()));

        assertTrue(Arrays.asList(ids)
            .contains(message2.getMessageID()));

        assertTrue(Arrays.asList(ids)
            .contains(message3.getMessageID()));
    }

    // TEST 10: Retrieve all message hashes
    @Test
    public void testGetMessageHashes() throws IOException {

        MessageArrays.addSentMessage(message1);
        MessageArrays.addDisregardedMessage(message2);
        MessageArrays.addStoredMessage(message3);

        String[] hashes =
            MessageArrays.getMessageHashes();

        assertEquals(3, hashes.length);

        assertTrue(Arrays.asList(hashes)
            .contains(message1.getMessageHash()));

        assertTrue(Arrays.asList(hashes)
            .contains(message2.getMessageHash()));

        assertTrue(Arrays.asList(hashes)
            .contains(message3.getMessageHash()));
    }

    // TEST 11: Search for a message using its ID
    @Test
    public void testSearchStoredMessageByID()
            throws IOException {

        MessageArrays.addSentMessage(message1);

        Messages found =
            MessageArrays.searchStoredMessageByID(
                message1.getMessageID()
            );

        assertNotNull(found);

        assertEquals(
            message1.getMessageID(),
            found.getMessageID()
        );
    }

    // TEST 12: Search for a nonexistent message
    @Test
    public void testSearchNonexistentMessage()
            throws IOException {

        Messages found =
            MessageArrays.searchStoredMessageByID(
                "9999999999"
            );

        assertNull(found);
    }

    // TEST 13: Search stored messages by recipient
    @Test
    public void testSearchStoredMessagesByRecipient()
            throws IOException {

        MessageArrays.addStoredMessage(message1);
        MessageArrays.addStoredMessage(message2);

        Messages[] results =
            MessageArrays.searchStoredMessagesByRecipient(
                "+27838968976"
            );

        assertEquals(1, results.length);

        assertEquals(
            message1.getRecipient(),
            results[0].getRecipient()
        );
    }

    // TEST 14: Delete a stored message from memory
    @Test
    public void testDeleteStoredMessageFromMemory()
            throws IOException {

        MessageArrays.addStoredMessage(message1);

        boolean result =
            MessageArrays.deleteStoredMessageByHash(
                message1.getMessageHash()
            );

        assertTrue(result);

        assertEquals(0,
            MessageArrays.getStoredCount());
    }

    // TEST 15: Delete a stored message from JSON
    @Test
    public void testDeleteStoredMessageFromJSON()
            throws IOException {

        message1.storeMessage();

        boolean result =
            MessageArrays.deleteStoredMessageByHash(
                message1.getMessageHash()
            );

        assertTrue(result);

        assertEquals(0,
            MessageArrays.getStoredCount());
    }

    // TEST 16: Attempt to delete a nonexistent message
    @Test
    public void testDeleteNonexistentMessage()
            throws IOException {

        boolean result =
            MessageArrays.deleteStoredMessageByHash(
                "INVALID"
            );

        assertFalse(result);
    }

    // TEST 17: Generate the complete message report
    @Test
    public void testDisplayReport() throws IOException {

        MessageArrays.addSentMessage(message1);
        MessageArrays.addDisregardedMessage(message2);
        MessageArrays.addStoredMessage(message3);

        String report =
            MessageArrays.displayReport();

        assertTrue(report.contains("MESSAGE REPORT"));
        assertTrue(report.contains("SENT MESSAGES"));
        assertTrue(report.contains("DISREGARDED MESSAGES"));
        assertTrue(report.contains("STORED MESSAGES"));

        assertTrue(report.contains(message1.getMessage()));
        assertTrue(report.contains(message2.getMessage()));
        assertTrue(report.contains(message3.getMessage()));
    }

    // TEST 18: Count sent, disregarded and stored messages
    @Test
    public void testMessageCounts() throws IOException {

        MessageArrays.addSentMessage(message1);
        MessageArrays.addSentMessage(message2);

        MessageArrays.addDisregardedMessage(message3);

        MessageArrays.addStoredMessage(message1);

        assertEquals(2,
            MessageArrays.getSentCount());

        assertEquals(1,
            MessageArrays.getDisregardedCount());

        assertEquals(1,
            MessageArrays.getStoredCount());
    }

    // TEST 19: Reset all Part 3 message arrays
    @Test
    public void testResetPart3Arrays() throws IOException {

        MessageArrays.addSentMessage(message1);
        MessageArrays.addDisregardedMessage(message2);
        MessageArrays.addStoredMessage(message3);

        MessageArrays.resetPart3Arrays();

        assertEquals(0,
            MessageArrays.getSentCount());

        assertEquals(0,
            MessageArrays.getDisregardedCount());

        assertEquals(0,
            MessageArrays.getStoredCount());
    }

    // TEST 20: Retrieve JSON and memory messages without duplicates
    @Test
    public void testGetStoredMessagesCombinesMemoryAndJSON()
            throws IOException {

        message1.storeMessage();

        MessageArrays.addStoredMessage(message1);
        MessageArrays.addStoredMessage(message2);

        Messages[] stored =
            MessageArrays.getStoredMessages();

        assertEquals(2, stored.length);

        assertTrue(Arrays.stream(stored)
            .anyMatch(m -> m.getMessageID().equals(
                message1.getMessageID())));

        assertTrue(Arrays.stream(stored)
            .anyMatch(m -> m.getMessageID().equals(
                message2.getMessageID())));
    }
}
