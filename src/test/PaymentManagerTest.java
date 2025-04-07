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
 * A framework to run public test cases for the PaymentManager class.
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @authors Fang Rui Shen
 * @version April 6, 2025
 */
public class PaymentManagerTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PaymentManagerTest.class);
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
     * Test that the PaymentManager class is declared correctly.
     */
    @Test(timeout = 1000)
    public void testMessageManagerClassDeclaration() {
        Class<?> clazz = PaymentManager.class;
        int modifiers = clazz.getModifiers();
        Class<?> superclass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that PaymentManager is public", Modifier.isPublic(modifiers));
        Assert.assertFalse("Ensure that PaymentManager is not abstract", Modifier.isAbstract(modifiers));
        Assert.assertEquals("Ensure that PaymentManager extends Object", Object.class, superclass);
        // User implements IPaymentManager, so there should be exactly one interface.
        Assert.assertEquals("Ensure that PaymentManager implements exactly one interface", 1, interfaces.length);
        Assert.assertEquals("Ensure that PaymentManager implements IPaymentManager", IPaymentManager.class, interfaces[0]);
    }

    /**
     * Test methods of PaymentManager.
     */
    @Test
    public void testPaymentManagerAndMethods() {
        File testFile = new File("payments.txt");
        String testInput = "";

        testInput += "0,0,1,10.00," + LocalDateTime.now().toString() + "," + PaymentStatus.COMPLETED.name()+ "\n";
        testInput += "1,0,1,5.00," + LocalDateTime.now().toString() + "," + PaymentStatus.PENDING.name() + "\n";
        testInput += "2,1,2,10.00," + LocalDateTime.now().toString() + "," + PaymentStatus.FAILED.name() + "\n";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(testFile))) {
            bw.write(testInput);
        } catch (IOException e) {
            System.out.println("Failed to load payments: " + e.getMessage());
        }

        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
            System.out.println("Method 1: Line count = " + lineCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("loadPayments() loaded incorrect payments", 3, lineCount);

        PaymentManager paymentManager = new PaymentManager();
        User user1 = new User("testUser1", "testPass1");
        User user2 = new User("testUser2", "testPass2");
        User user3 = new User("testUser3", "testPass3");

        Assert.assertEquals("getUserBalance() returns incorrect balance", user1.getBalance(), paymentManager.getUserBalance(user1), 0.001);
        Assert.assertEquals("getUserBalance() returns incorrect balance", user2.getBalance(), paymentManager.getUserBalance(user2), 0.001);
        Assert.assertEquals("getUserBalance() returns incorrect balance", user3.getBalance(), paymentManager.getUserBalance(user3), 0.001);

        paymentManager.updateBalance(user1, 100.00);
        paymentManager.updateBalance(user2, 200.00);
        paymentManager.updateBalance(user3, 300.00);
        Assert.assertEquals("Ensure updateBalance() updates balance correctly", user1.getBalance(), paymentManager.getUserBalance(user1), 0.001);
        Assert.assertEquals("getUserBalance() returns incorrect balance", user2.getBalance(), paymentManager.getUserBalance(user2), 0.001);
        Assert.assertEquals("getUserBalance() returns incorrect balance", user3.getBalance(), paymentManager.getUserBalance(user3), 0.001);

        paymentManager.processPayment(user1, user3, 50.00);
        lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("savePayments() saved incorrect payments", 4, lineCount);
    }
}