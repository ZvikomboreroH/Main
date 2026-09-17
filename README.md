Chat App: Registration and Login System

Project Description

This project is a Java console application that demonstrates a basic registration and login system. It allows users to enter their personal details, validates their username, password, and South African cell phone number, and then authenticates the user using their registered credentials.

The project is divided into two classes:

* **`Main`** — Handles user input and displays output.
* **`Login`** — Stores user details and manages validation, registration, and login functionality.

Features

* Collects user registration details through the console.
* Validates usernames using specific formatting rules.
* Checks password complexity.
* Validates South African international cell phone numbers.
* Registers the user only when all validation checks pass.
* Authenticates users using their registered username and password.
* Displays appropriate success and error messages.

Validation Rules

Username

The username must:

* Contain an underscore (`_`).
* Be no more than five characters long.

Example of a valid username:**

```text
haz_1
```

Password

The password must:

* Contain at least eight characters.
* Include at least one capital letter.
* Include at least one number.
* Include at least one special character.

Example of a valid password:**

```text
Password1!
```

Cell Phone Number

The cell phone number must:

* Begin with the South African international code `+27`.
* Be followed by between one and ten digits.

Example:

```text
+27838968976
```

Technologies Used

Java
Java Scanner — Used to collect input from the keyboard.
Java Regular Expressions (Regex) — Used to validate passwords and cell phone numbers.
Object-Oriented Programming (OOP) — Used through classes, objects, constructors, encapsulation, and methods.

Project Structure

text
src/
└── main/
    └── java/
        └── com/
            └── mycompany/
                └── main/
                    ├── Main.java
                    └── Login.java


How the Program Works

1. The program starts in the `Main` class.
2. A `Scanner` object collects the user’s registration details.
3. A `Login` object is created using the entered information.
4. The username, password, and cell phone number are validated.
5. If all details are valid, the user is registered.
6. The user enters their username and password to log in.
7. The program compares the entered credentials with the stored credentials.
8. A welcome message or an error message is displayed.

Example Output

text
=== Chat App: Registration ===
Enter first name: Hazel
Enter last name: Bwanausi
Enter username (must contain '_' and be <= 5 characters): haz_1
Enter password (min 8 chars, 1 capital, 1 number, 1 special char): Password1!
Enter South African cell number (e.g. +27838968976): +27838968976

Username successfully captured.
Password successfully captured.
Cell phone number successfully added.
Registration successful!

=== Chat App: Login ===
Enter username: haz_1
Enter password: Password1!
Welcome Hazel, Bwanausi it is great to see you again.


Object-Oriented Programming Concepts

This project demonstrates the following concepts:

| Concept                    | Application in the Project                         |
| -------------------------- | -------------------------------------------------- |
| **Class**                  | `Main` and `Login`                                 |
| **Object**                 | `Login login = new Login(...)`                     |
| **Constructor**            | Initialises the Login object                       |
| **Encapsulation**          | Private instance variables                         |
| **Methods**                | Validation, registration, and login methods        |
| **Boolean**                | Returns `true` or `false` for validation and login |
| **Conditional statements** | `if` statements control program decisions          |
| **String**                 | Stores names, usernames, passwords, and messages   |
| **Regex**                  | Validates password and phone number formats        |

How to Run

1. Open the project in a Java IDE such as NetBeans.
2. Ensure that `Main.java` and `Login.java` are in the same package:

   ```java
   package com.mycompany.main;
   ```
3. Run the `Main` class.
4. Enter the requested registration details.
5. Follow the prompts to complete registration and login.

Important Notes

* Registration only succeeds when all validation checks pass.
* Login is only allowed after successful registration.
* The current implementation stores the password in memory as plain text. A real-world application should use secure password hashing and should not store passwords this way.
* The phone number validation checks the required format, but it does not verify whether the number actually exists.
