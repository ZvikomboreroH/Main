/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.main;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {

    public LoginTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getFirstName method, of class Login.
     */
    @Test
    public void testGetFirstName() {
        System.out.println("getFirstName");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        String expResult = "John";
        String result = instance.getFirstName();

        assertEquals(expResult, result);
    }

    /**
     * Test of getLastName method, of class Login.
     */
    @Test
    public void testGetLastName() {
        System.out.println("getLastName");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        String expResult = "Smith";
        String result = instance.getLastName();

        assertEquals(expResult, result);
    }

    /**
     * Test of getUsername method, of class Login.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        String expResult = "john_";
        String result = instance.getUsername();

        assertEquals(expResult, result);
    }

    /**
     * Test of getCellPhoneNumber method, of class Login.
     */
    @Test
    public void testGetCellPhoneNumber() {
        System.out.println("getCellPhoneNumber");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        String expResult = "+27838968976";
        String result = instance.getCellPhoneNumber();

        assertEquals(expResult, result);
    }

    /**
     * Test of isRegistered method, of class Login.
     */
    @Test
    public void testIsRegistered() {
        System.out.println("isRegistered");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        boolean expResult = false;
        boolean result = instance.isRegistered();

        assertEquals(expResult, result);
    }

    /**
     * Test of checkUserName method, of class Login.
     */
    @Test
    public void testCheckUserName() {
        System.out.println("checkUserName");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        boolean expResult = true;
        boolean result = instance.checkUserName();

        assertEquals(expResult, result);
    }

    /**
     * Test of checkPasswordComplexity method, of class Login.
     */
    @Test
    public void testCheckPasswordComplexity() {
        System.out.println("checkPasswordComplexity");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        boolean expResult = true;
        boolean result = instance.checkPasswordComplexity();

        assertEquals(expResult, result);
    }

    /**
     * Test of checkCellPhoneNumber method, of class Login.
     */
    @Test
    public void testCheckCellPhoneNumber() {
        System.out.println("checkCellPhoneNumber");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        boolean expResult = true;
        boolean result = instance.checkCellPhoneNumber();

        assertEquals(expResult, result);
    }

    /**
     * Test of getUsernameMessage method, of class Login.
     */
    @Test
    public void testGetUsernameMessage() {
        System.out.println("getUsernameMessage");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        String expResult = "Username successfully captured.";
        String result = instance.getUsernameMessage();

        assertEquals(expResult, result);
    }

    /**
     * Test of getPasswordMessage method, of class Login.
     */
    @Test
    public void testGetPasswordMessage() {
        System.out.println("getPasswordMessage");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        String expResult = "Password successfully captured.";
        String result = instance.getPasswordMessage();

        assertEquals(expResult, result);
    }

    /**
     * Test of getCellPhoneMessage method, of class Login.
     */
    @Test
    public void testGetCellPhoneMessage() {
        System.out.println("getCellPhoneMessage");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        String expResult = "Cell phone number successfully added.";
        String result = instance.getCellPhoneMessage();

        assertEquals(expResult, result);
    }

    /**
     * Test of registerUser method, of class Login.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        String expResult
                = "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell phone number successfully added.\n"
                + "Registration successful.";

        String result = instance.registerUser();

        assertEquals(expResult, result);
    }

    /**
     * Test of loginUser method, of class Login.
     */
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");

        String enteredUsername = "john_";
        String enteredPassword = "Password1!";

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        // Register the user before attempting to log in
        instance.registerUser();

        boolean expResult = true;
        boolean result = instance.loginUser(enteredUsername, enteredPassword);

        assertEquals(expResult, result);
    }

    /**
     * Test of returnLoginStatus method, of class Login.
     */
    @Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");

        boolean loginSuccessful = false;

        Login instance = new Login(
                "John",
                "Smith",
                "john_",
                "Password1!",
                "+27838968976"
        );

        String expResult = "Username or password incorrect, please try again.";
        String result = instance.returnLoginStatus(loginSuccessful);

        assertEquals(expResult, result);
    }
}
