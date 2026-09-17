/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;
import java.util.regex.Pattern;
/**
 *
 * @author Student
 */
public class Login {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    private boolean isRegistered = false;

    public Login(String firstName, String lastName, String username, String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // ---------- Getters ----------
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    // ---------- Validation methods ----------

    /**
     * Username must contain an underscore and be no more than five
     * characters long.
     */
    public boolean checkUserName() {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Password must be at least eight characters long and contain a
     * capital letter, a number, and a special character.
     */
    public boolean checkPasswordComplexity() {
        if (password == null) {
            return false;
        }
        boolean hasMinLength = password.length() >= 8;
        boolean hasCapital = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasNumber = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecial = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
        return hasMinLength && hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Cell phone number must contain the South African international
     * country code (+27) followed by the number, which must be no more
     * than ten characters long (after the country code).
     */
    public boolean checkCellPhoneNumber() {
        if (cellPhoneNumber == null) {
            return false;
        }
        return Pattern.matches("^\\+27\\d{1,10}$", cellPhoneNumber);
    }

    // ---------- Message helpers (match required output text) ----------

    public String getUsernameMessage() {
        return checkUserName()
                ? "Username successfully captured."
                : "Username is not correctly formatted; please ensure that your username "
                + "contains an underscore and is no more than five characters in length.";
    }

    public String getPasswordMessage() {
        return checkPasswordComplexity()
                ? "Password successfully captured."
                : "Password is not correctly formatted; please ensure that the password "
                + "contains at least eight characters, a capital letter, a number, and a special character.";
    }

    public String getCellPhoneMessage() {
        return checkCellPhoneNumber()
                ? "Cell phone number successfully added."
                : "Cell phone number incorrectly formatted or does not contain international code.";
    }

    /**
     * Runs all registration checks and returns the correct message.
     * Registration only succeeds when username, password and cell number
     * are all valid.
     */
    public String registerUser() {
        if (!checkUserName()) {
            return getUsernameMessage();
        }
        if (!checkPasswordComplexity()) {
            return getPasswordMessage();
        }
        if (!checkCellPhoneNumber()) {
            return getCellPhoneMessage();
        }
        isRegistered = true;
        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.\nRegistration successful.";
    }

    // ---------- Login ----------

    /**
     * Verifies that the entered username and password match the stored
     * credentials for a user that has already registered.
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        if (!isRegistered) {
            return false;
        }
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

    /**
     * Returns the appropriate message for the outcome of a login attempt.
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}


