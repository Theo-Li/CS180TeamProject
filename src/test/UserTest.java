package test;
import main.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Modifier;
import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases for the User class.
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @authors Lex Borrero and Tianzhi LI (User class)
 * @version March 1, 2025
 */
public class UserTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(UserTest.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    // Fields for capturing system output/input (if needed)
    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUpStreams() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOutput);
        System.setIn(originalSysin);
    }

    private String getOutput() {
        return testOut.toString();
    }

    private void setInput(String input) {
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }

    /**
     * Test that the User class is declared correctly.
     */
    @Test(timeout = 1000)
    public void testUserClassDeclaration() {
        Class<?> clazz = User.class;
        int modifiers = clazz.getModifiers();
        Class<?> superclass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that User is public", Modifier.isPublic(modifiers));
        Assert.assertFalse("Ensure that User is not abstract", Modifier.isAbstract(modifiers));
        Assert.assertEquals("Ensure that User extends Object", Object.class, superclass);
        // User implements IUser, so there should be exactly one interface.
        Assert.assertEquals("Ensure that User implements exactly one interface", 1, interfaces.length);
        Assert.assertEquals("Ensure that User implements IUser", IUser.class, interfaces[0]);
    }

    /**
     * Test basic field values and methods of User.
     */
    @Test(timeout = 1000)
    public void testUserFieldsAndMethods() {
        User user = new User("testUser", "testPass");

        Assert.assertEquals("getUsername() returns correct username", "testUser", user.getUsername());
        Assert.assertEquals("getPassword() returns correct password", "testPass", user.getPassword());
        Assert.assertEquals("Initial balance should be 0.0", 0.0, user.getBalance(), 0.001);

        user.setBalance(100.0);
        Assert.assertEquals("setBalance() updates balance correctly", 100.0, user.getBalance(), 0.001);

        Assert.assertTrue("getUserID() returns non-negative value", user.getUserID() >= 0);

        String userStr = user.toString();
        Assert.assertNotNull("toString() should not return null", userStr);
        Assert.assertTrue("toString() should contain the class name 'User@'", userStr.contains("User@"));
    }

    /**
     * Test that user IDs increment sequentially.
     */
    @Test(timeout = 1000)
    public void testUserIdIncrement() {
        User user1 = new User("user1", "pass1");
        User user2 = new User("user2", "pass2");
        Assert.assertEquals("User IDs should increment sequentially", user1.getUserID() + 1, user2.getUserID());
    }

    /**
     * Test the items-related methods in User.
     * <p>
     * This test assumes that an appropriate definition for the Item type exists.
     * An anonymous instance of Item is used to test addItem() and removeItem().
     */
    @Test(timeout = 1000)
    public void testItemsMethods() {
        User user = new User("itemUser", "pass");

        // Initially, items should be null.
        Assert.assertNull("getItems() should return null initially", user.getItems());

        // Initialize items list using setItems.
        user.setItems(new ArrayList<>());
        Assert.assertNotNull("After setItems(), getItems() should not return null", user.getItems());

        // Create an anonymous instance of Item.
        Item item = new Item() {};
        // Test addItem().
        user.addItem(item);
        Assert.assertTrue("After addItem(), getItems() should contain the added item", user.getItems().contains(item));

        // Test removeItem().
        boolean removed = user.removeItem(item);
        Assert.assertTrue("removeItem() should return true", removed);
        Assert.assertFalse("After removeItem(), getItems() should not contain the removed item", user.getItems().contains(item));
    }
}


