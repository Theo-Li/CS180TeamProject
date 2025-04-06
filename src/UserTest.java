import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.io.*;
import java.util.ArrayList;
/**
 * A framework to run public test cases for the User class.
 *
 * <p>Purdue University -- CS18000 -- Summer 2022</p>
 *
 * @author Purdue CS
 * @version June 13, 2022
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
     * Test that the User class is declared correctly.
     */
    @Test(timeout = 1000)
    public void userClassDeclarationTest() {
        Class<?> clazz = User.class;
        int modifiers = clazz.getModifiers();
        Class<?> superclass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that `User` is public!", Modifier.isPublic(modifiers));
        Assert.assertFalse("Ensure that `User` is NOT abstract!", Modifier.isAbstract(modifiers));
        Assert.assertEquals("Ensure that `User` extends `Object`!", Object.class, superclass);
        Assert.assertEquals("Ensure that `User` implements no interfaces!", 0, interfaces.length);
    }

    /**
     * Test the basic fields and methods of User.
     */
    @Test(timeout = 1000)
    public void testUserFieldsAndMethods() {
        User user = new User("testUser", "testPass");

        Assert.assertEquals("Ensure getUsername() returns the correct username", "testUser", user.getUsername());
        Assert.assertEquals("Ensure getPassword() returns the correct password", "testPass", user.getPassword());
        Assert.assertEquals("Ensure initial balance is 0.0", 0.0, user.getBalance(), 0.001);

        user.setBalance(50.0);
        Assert.assertEquals("Ensure changeBalance() updates the balance correctly", 50.0, user.getBalance(), 0.001);

        Assert.assertTrue("Ensure getUserID() returns a non-negative value", user.getUserID() >= 0);

        String userStr = user.toString();
        Assert.assertNotNull("Ensure toString() does not return null", userStr);
        Assert.assertTrue("Ensure toString() contains the class name `User@`", userStr.contains("User@"));
    }

    /**
     * Helper method to initialize the private 'items' field in User.
     */
    private void initializeItems(User user) throws Exception {
        Field itemsField = User.class.getDeclaredField("items");
        itemsField.setAccessible(true);
        itemsField.set(user, new ArrayList<>());
    }

    /**
     * Test the items-related methods in User.
     * <p>
     * This test assumes that an appropriate definition for the Item type exists.
     * An anonymous instance of Item is created to test addItem() and removeItem().
     */
    @Test(timeout = 1000)
    public void testItemsMethods() throws Exception {
        User user = new User("itemUser", "pass");

        // Initially, items should be null because it is not initialized in the constructor.
        Assert.assertNull("Initially, getItems() should return null", user.getItems());

        // Initialize the items list via reflection.
        initializeItems(user);
        Assert.assertNotNull("After initialization, getItems() should not return null", user.getItems());

        // Create an anonymous instance of Item.
        Item item1 = new Item() {};
        ArrayList<Item> itemsAfterAdd = user.addItem(item1);
        Assert.assertTrue("After addItem(), the items list should contain the added item", itemsAfterAdd.contains(item1));

        ArrayList<Item> itemsAfterRemove = user.removeItem(item1);
        Assert.assertFalse("After removeItem(), the items list should not contain the removed item", itemsAfterRemove.contains(item1));
    }
}

