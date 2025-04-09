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

/**
 * A framework to run public test cases for the Message class.
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @authors Fang Rui Shen
 * @version April 6, 2025
 */
public class MessageTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MessageTest.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    // Fields for capturing system output/input
    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreInputAndOutput() {
        System.setIn(originalSysin);
        System.setOut(originalOutput);
    }

    private String getOutput() {
        return testOut.toString();
    }

    private void receiveInput(String str) {
        testIn = new ByteArrayInputStream(str.getBytes());
        System.setIn(testIn);
    }

    /**
     * Test that the Message class is declared correctly.
     */
    @Test(timeout = 1000)
    public void messageClassDeclarationTest() {
        Class<?> clazz = Message.class;
        int modifiers = clazz.getModifiers();
        Class<?> superclass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that `Message` is public!", Modifier.isPublic(modifiers));
        Assert.assertFalse("Ensure that `Message` is NOT abstract!", Modifier.isAbstract(modifiers));
        Assert.assertEquals("Ensure that `Message` extends `Object`!", Object.class, superclass);
        // Message implements IMessage, so there should be exactly one interface.
        Assert.assertEquals("Ensure that Message implements exactly one interface",
                1, interfaces.length);
        Assert.assertEquals("Ensure that Message implements IMessage", IMessage.class, interfaces[0]);
    }

    /**
     * Test the basic fields and methods of Message.
     */
    @Test(timeout = 1000)
    public void testMessageFieldsAndMethods() {
        Message message = new Message(1, 1, "test message");

        Assert.assertEquals("Ensure getSenderID() returns correct senderID",
                1, message.getSenderID());
        Assert.assertEquals("Ensure getReceiverID() returns correct receiverID",
                1, message.getReceiverID());
        Assert.assertEquals("Ensure getMessage() returns correct message",
                "test message", message.getMessage());

        message.setSenderID(2);
        message.setReceiverID(2);
        message.setMessage("test message 2");
        Assert.assertEquals("Ensure setSenderID() updates senderID correctly",
                2, message.getSenderID());
        Assert.assertEquals("Ensure setReceiverID() updates receiverID correctly",
                2, message.getReceiverID());
        Assert.assertEquals("Ensure setMessage() updates message correctly",
                "test message 2", message.getMessage());
    }
}