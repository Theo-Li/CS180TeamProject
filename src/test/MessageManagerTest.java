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
import java.util.List;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases for the MessageManager class.
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @author Fang Rui Shen
 * @version April 6, 2025
 */
public class MessageManagerTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MessageManagerTest.class);
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
     * Test that the MessageManager class is declared correctly.
     */
    @Test(timeout = 1000)
    public void testMessageManagerClassDeclaration() {
        Class<?> clazz = MessageManager.class;
        int modifiers = clazz.getModifiers();
        Class<?> superclass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that MessageManager is public", Modifier.isPublic(modifiers));
        Assert.assertFalse("Ensure that MessageManager is not abstract", Modifier.isAbstract(modifiers));
        Assert.assertEquals("Ensure that MessageManager extends Object", Object.class, superclass);
        // User implements IMessageManager, so there should be exactly one interface.
        Assert.assertEquals("Ensure that MessageManager implements exactly one interface",
                1, interfaces.length);
        Assert.assertEquals("Ensure that MessageManager implements IMessageManager",
                IMessageManager.class, interfaces[0]);
    }

    /**
     * Test methods of MessageManager.
     */
    @Test
    public void testMessageManagerAndMethods() {
        File testFile = new File("messages.txt");
        String testInput = "0,1,test message 1\r\n" + //
                        "0,1,test message 2\r\n" + //
                        "1,0,test message 3\r\n" + //
                        "0,1,test message 4\r\n" + //
                        "1,0,test message 5\\r\\n";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(testFile))) {
            bw.write(testInput);
        } catch (IOException e) {
            System.out.println("Failed to load messages: " + e.getMessage());
        }

        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(testFile))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("loadMessages() loaded incorrect messages", 5, lineCount);

        MessageManager messageManager = new MessageManager();
        User user1 = new User("testUser1", "testPass1");
        User user2 = new User("testUser2", "testPass2");
        User user3 = new User("testUser3", "testPass3");

        List<Message> messagesForUser1 = messageManager.getMessagesForUser(user1);
        List<Message> messagesForUser2 = messageManager.getMessagesForUser(user2);
        List<Message> messagesForUser3 = messageManager.getMessagesForUser(user3);
        Assert.assertTrue("getMessagesForUser() returns incorrect messages",
                messagesForUser1.size() == 5);
        Assert.assertTrue("getMessagesForUser() returns incorrect messages",
                messagesForUser2.size() == 5);
        Assert.assertTrue("getMessagesForUser() returns incorrect messages",
                messagesForUser3.size() == 0);
        Assert.assertEquals("loadMessages() loads incorrect messages", "test message 1",
                messagesForUser1.get(0).getMessage());
        Assert.assertEquals("loadMessages() loads incorrect messages", "test message 1",
                messagesForUser2.get(0).getMessage());

        messageManager.sendMessage(user2, user3, "test message 6");
        messagesForUser1 = messageManager.getMessagesForUser(user1);
        messagesForUser2 = messageManager.getMessagesForUser(user2);
        messagesForUser3 = messageManager.getMessagesForUser(user3);
        Assert.assertTrue("saveMessages() saves incorrect messages", messagesForUser1.size() == 5);
        Assert.assertTrue("saveMessages() saves incorrect messages", messagesForUser2.size() == 6);
        Assert.assertTrue("saveMessages() saves incorrect messages", messagesForUser3.size() == 1);

        List<Message> conversationForUser1and2 = messageManager.getConversation(user1, user2);
        List<Message> conversationForUser1and3 = messageManager.getConversation(user1, user3);
        List<Message> conversationForUser2and3 = messageManager.getConversation(user2, user3);
        Assert.assertTrue("getConversation() returns incorrect messages",
                conversationForUser1and2.size() == 5);
        Assert.assertTrue("getConversation() returns incorrect messages",
                conversationForUser1and3.size() == 0);
        Assert.assertTrue("getConversation() returns incorrect messages",
                conversationForUser2and3.size() == 1);
    }
}