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
import java.time.LocalDateTime;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases for the UserManager class.
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @authors Fang Rui Shen
 * @version April 6, 2025
 */
public class UserManagerTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(UserManagerTest.class);
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
     * Test that the UserManager class is declared correctly.
     */
    @Test(timeout = 1000)
    public void testUserManagerClassDeclaration() {
        Class<?> clazz = UserManager.class;
        int modifiers = clazz.getModifiers();
        Class<?> superclass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that UserManager is public", Modifier.isPublic(modifiers));
        Assert.assertFalse("Ensure that UserManager is not abstract", Modifier.isAbstract(modifiers));
        Assert.assertEquals("Ensure that UserManager extends Object", Object.class, superclass);
        // User implements IUserManager, so there should be exactly one interface.
        Assert.assertEquals("Ensure that UserManager implements exactly one interface", 1, interfaces.length);
        Assert.assertEquals("Ensure that UserManager implements IUserManager", IUserManager.class, interfaces[0]);
    }

    /**
     * Test methods of UserManager.
     */
    @Test
    public void testUserManagerAndMethods() {
        File testFile = new File("users.txt");
        String testInput = "0,testUser1,testPass1,100.00\r\n" + //
                        "1,testUser2,testPass2,200.00\r\n";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(testFile))) {
            bw.write(testInput);
        } catch (IOException e) {
            System.out.println("Failed to load users: " + e.getMessage());
        }

        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("loadUsers() loaded incorrect users", 2, lineCount);

        UserManager userManager = new UserManager();
        User user1 = new User("testUser1", "testPass1");

        Assert.assertEquals("getUser() returns incorrect user", "testUser1", userManager.getUser(0).getUsername());
        Assert.assertFalse("addUser() does not check duplicate username", userManager.addUser(user1));
        Assert.assertFalse("registerUser() does not check duplicate username", userManager.registerUser("testUser1", "testPass1"));

        userManager.deleteUser(0);
        Assert.assertTrue("deleteUser() incorrectly deleted user", userManager.addUser(user1));

        Assert.assertEquals("login() returns incorrect user", "testUser1", userManager.login("testUser1", "testPass1").getUsername());
        Assert.assertNull("login() incorrectly verified user", userManager.login("testUser3", "testPass3"));

        lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("saveUsers() saved incorrect users", 2, lineCount);
    }
}