# QuickChat Application

## Description
QuickChat is a Java-based messaging application that allows users to create, validate, send, disregard, and store messages. The application uses a `Message` class to manage message information, generate unique ten-digit message IDs, and create message hashes. Messages can also be stored in a JSON file for later retrieval.

## Features
- Generate ten-digit message IDs.
- Validate recipient cell phone numbers using the South African international code (+27).
- Validate messages to ensure they do not exceed 250 characters.
- Generate message hashes using the message ID, message number, and first and last words.
- Send, disregard, or store messages.
- Display sent message details and count the total number of messages sent.
- Store messages in `storedMessages.json`.
- Retrieve stored messages by their message IDs.

## Requirements
- Java Development Kit (JDK).
- NetBeans IDE.
- The `org.json` library for JSON processing.

## Installation and Setup
1. Open NetBeans IDE.
2. Create or open your QuickChat Java project.
3. Add the `Main.java` and `Message.java` files to the project.
4. Add the `org.json` library to the project dependencies.
5. Ensure that the package declarations in both Java files match.
6. Build the project to check for compilation errors.
7. Run `Main.java` to launch the application.

## How to Use
1. Register and log in if required by the application.
2. Select the QuickChat option.
3. Enter the maximum number of messages you want to process.
4. Choose **Send Messages** from the menu.
5. Enter the recipient's cell phone number and message text.
6. Choose whether to send, disregard, or store each message.
7. View the displayed message details and return to the menu.
8. Select the quit option to exit the application.

## JSON Storage
Messages selected for storage are saved in `storedMessages.json`. Each message is stored using its message ID as the key and includes the message ID, message
