# QuickChat – Chat App

A Java console application built in two parts.

- **Part 1** adds registration and login (username, password and cell number rules).
- **Part 2** adds sending messages after login: the QuickChat menu, the `Message` class, JSON storage, unit tests and continuous integration.

Part 2 was developed on the feature branch `QuickChatTasks`. The `main` branch still holds the working Part 1 code.

---

## 1. What Part 1 gave us

- `Login.java` holds the registration rules: `checkUserName()`, `checkPasswordComplexity()`, `checkCellPhoneNumber()`, `registerUser()`, `loginUser()` and `returnLoginStatus()`.
- `Main.java` asked for the registration details and then the login details, all inside `main`.
- `LoginTest.java` holds 8 unit tests for the Part 1 rules.
- `pom.xml` is the Maven build file.

---

## 2. Summary of changes from Part 1 to Part 2

New files:
- `Message.java` is the `Message` class with every method from the brief.
- `MessageTest.java` holds 21 unit tests that use the brief's test data.
- `.github/workflows/maven.yml` is a GitHub Actions workflow that runs the tests on every push.
- `.gitignore` keeps `target/` and the generated JSON files out of git.

Changed files:
- `Main.java` was split into methods, and login now gates the new QuickChat menu.
- `Login.java` had a small change: the cell number rule was moved into a reusable method.
- `pom.xml` gained the `org.json` dependency for JSON storage.

Unchanged:
- `LoginTest.java` is unchanged, and all 8 Part 1 tests still pass.

---

## 3. Changes to existing Part 1 code

### `Login.java`
The brief's hint for the recipient number test says to reuse the method and tests from the last task. The cell number rule from Part 1 (`+27` followed by 1 to 10 digits) was therefore moved into a reusable static method, `Login.isValidCellPhoneNumber(String)`. `checkCellPhoneNumber()` now calls it, so Part 1 behaves exactly as before and the Part 1 tests are unaffected. `Message.checkRecipientCell()` uses the same rule.

### `Main.java`
Part 1's `main` did everything inline. It is now split into small methods:

- `register()` runs the Part 1 registration (unchanged behaviour).
- `loginUser()` runs the Part 1 login (unchanged behaviour, still one attempt).
- `runQuickChat()` is new. It shows the welcome message, asks for the message count and runs the numeric menu loop.
- `sendMessages()` is new. It enters messages up to the chosen count.
- `readRecipient()`, `readMessageText()` and `readPositiveInt()` are new input helpers that ask again until the input is valid.

`main` only calls `runQuickChat()` after `loginUser()` succeeds. A failed login ends the program, so messages can only be sent after a successful login (brief item 1).

### `pom.xml`
Added the `org.json` dependency (`org.json:json`, version 20240303), used to store messages in a JSON file.

---

## 4. New in Part 2

### 4.1 QuickChat flow (brief items 1–7)

1. Register and log in (Part 1).
2. The app displays "Welcome to QuickChat."
3. The user says how many messages they want to enter. Only that many can be entered.
4. A numeric menu is shown and repeated until the user quits:
   - Option 1 – Send Messages
   - Option 2 – Show recently sent messages, which displays "Coming Soon."
   - Option 3 – Quit
5. For each message the user enters a recipient and a message:
   - If the message is too long, the app shows "Please enter a message of less than 250 characters." and asks again.
   - If it is valid, the app shows "Message sent".
   - The user then chooses to send, disregard or store the message:
     - Send shows "Message successfully sent."
     - Disregard shows "Press 0 to delete the message." and, once 0 is pressed, confirms the deletion.
     - Store shows "Message successfully stored."
6. After a message is sent, its details are shown in this order: Message ID, Message Hash, Recipient, Message.
7. When all the messages have been entered, the total number of messages sent is displayed.

### 4.2 The `Message` class

Methods from the brief:
- `boolean checkMessageID()` returns true if the ID is no more than ten digits.
- `String checkRecipientCell()` returns the success text, or the incorrectly formatted / no international code error.
- `String createMessageHash()` builds the hash from the first two digits of the ID, the message number, and the first and last words of the message, all in capitals, separated by colons.
- `String SentMessage()` asks the user to send, disregard or store the message and returns the matching text.
- `static String printMessages()` returns the details of all messages sent while the program is running.
- `static int returnTotalMessages()` returns the total number of messages sent.
- `storeMessage()` stores the message in a JSON file.

Extra helpers: `checkMessageLength()` returns the ready-to-send text or the "exceeds 250 characters by X" error, `getMessageIDStatus()` returns the "Message ID generated" text, and `details()` and `generateMessageID()` support the rest.

### 4.3 Message data (brief table)

- **Message ID:** a random ten-digit number.
- **Num messages sent:** starts at 0 and goes up by 1 for each message sent.
- **Recipient:** must start with an international code.
- **Message:** at most 250 characters.
- **Message Hash:** auto-generated from the ID, message number and message text.

### 4.4 JSON storage (`storeMessage()`)
Stored messages are written to `storedMessages.json` using `org.json`. The file is one JSON object keyed by message ID, so a message can be found again by its ID (`readStoredMessage(id)`), and messages stored in earlier runs are kept. Each entry holds the message ID, message number, recipient, message text and message hash.

### 4.5 No arrays
The code uses no arrays or lists. The only array is `String[] args` in `main`, which Java requires.
- The hash finds the first and last word with a loop over the characters, not by splitting the text.
- Sent messages are tracked with a counter and a text log.
- The JSON file is an object keyed by message ID, not a JSON array.

---

## 5. Unit tests (`MessageTest.java`)

The tests use the test data from the brief: Num Messages is 2, the first message is sent and the second is discarded. They cover:

- **Message length:** success, exactly 250 characters, and failure with the size reported.
- **Recipient number (reuses the Part 1 rule):** a valid number is captured, a local number is rejected, and the Part 1 numbers are checked.
- **Message hash:** the Test Case 1 hash, plus the remaining hashes checked in a loop.
- **Message ID:** the ID generated text, and an ID that is too long is rejected.
- **MessageSent:** the send, disregard and store texts, with and without asking the user.
- **Totals and details:** the total sent, the `printMessages()` order, and the full scenario from the brief.
- **JSON storage:** a message is stored and read back by ID, and two messages are stored.

All tests run with `mvn test` (8 Part 1 tests and 21 Part 2 tests, 29 in total).

---

## 6. Continuous integration (brief item 7)

`.github/workflows/maven.yml` runs the Maven tests with JDK 17 on every push and pull request, so the tests run automatically whenever the code is updated.

---

## 7. Git workflow

Part 2 is developed on a feature branch named `QuickChatTasks`, created from `main`, so `main` always stays in working order. The branch is then pushed to GitHub.

---

## 8. Notes and assumptions

- **Cell number length:** the brief says "no more than ten characters", but its own valid number is 12 characters. The rule used is the Part 1 rule: `+27` followed by 1 to 10 digits.
- **Message numbering:** the number in the hash counts the messages sent before it, starting at 0. Discarded and stored messages are not counted.
- **250 characters:** exactly 250 is allowed ("should not exceed 250").
- **Hash loop test:** the brief says to refer to the video for this. The messages used are our own.
- **Test message IDs:** the tests set the ID by hand so that the hash is predictable. The app itself generates random IDs.
