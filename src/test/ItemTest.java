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
 * A framework to run public test cases for the Item class.
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @author Fang Rui Shen
 * @version April 6, 2025
 */
public class ItemTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ItemTest.class);
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
     * Test that the Item class is declared correctly.
     */
    @Test(timeout = 1000)
    public void testItemClassDeclaration() {
        Class<?> clazz = Item.class;
        int modifiers = clazz.getModifiers();
        Class<?> superclass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that Item is public", Modifier.isPublic(modifiers));
        Assert.assertFalse("Ensure that Item is not abstract", Modifier.isAbstract(modifiers));
        Assert.assertEquals("Ensure that Item extends Object", Object.class, superclass);
        // User implements IItem, so there should be exactly one interface.
        Assert.assertEquals("Ensure that Item implements exactly one interface",
                1, interfaces.length);
        Assert.assertEquals("Ensure that Item implements IItem", IItem.class, interfaces[0]);
    }

    /**
     * Test basic field values and methods of Item.
     */
    @Test(timeout = 1000)
    public void testItemFieldsAndMethods() {
        Item item = new Item("testItem", 10.00, "test filename", 1);

        Assert.assertTrue("getItemID() returns invalid value", item.getItemID() >= 0);

        Assert.assertEquals("getName() returns incorrect name", "testItem", item.getName());
        Assert.assertEquals("getPrice() returns incorrect price", 10.0, item.getPrice(), 0.001);
        Assert.assertEquals("getPictureFilename() returns incorrect filename", "test filename",
                item.getPictureFileName());
        Assert.assertEquals("getSellerID() returns incorrect sellerID", 1, item.getSellerID());

        item.setItemID(2);
        item.setName("testItem 2");
        item.setPrice(1.00);
        item.setPictureFileName("test filename 2");
        item.setSellerID(2);
        Assert.assertEquals("Ensure setItemID() updates itemID correctly", 2, item.getItemID());
        Assert.assertEquals("Ensure setName() updates name correctly", "testItem 2", item.getName());
        Assert.assertEquals("Ensure setPrice() updates price correctly", 1.0, item.getPrice(),
                0.001);
        Assert.assertEquals("Ensure setPictureFilename() updates name correctly", "test filename 2",
                item.getPictureFileName());
        Assert.assertEquals("Ensure setSellerID() updates name correctly", 2, item.getSellerID());
    }

    /**
     * Test that Item IDs increment sequentially.
     */
    @Test(timeout = 1000)
    public void testItemIdIncrement() {
        Item item1 = new Item("item1", 10.00, "filename1", 5);
        Item item2 = new Item("item2", 10.00, "filename2", 5);
        Assert.assertEquals("Item IDs should increment sequentially",
                item1.getItemID() + 1, item2.getItemID());
    }
}


