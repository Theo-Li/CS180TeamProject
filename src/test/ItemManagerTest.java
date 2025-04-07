package test;
import main.*;

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
import java.util.List;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases for the ItemManager class.
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @author Lex Borrero
 * @version April 2025
 */
public class ItemManagerTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ItemManagerTest.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    // Fields for capturing system output/input if needed
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
     * Cleanup the items.txt file (if created during tests)
     */
    @After
    public void cleanupFile() {
        File file = new File("items.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Test that the ItemManager class is declared correctly.
     */
    @Test(timeout = 1000)
    public void testItemManagerClassDeclaration() {
        Class<?> clazz = ItemManager.class;
        int modifiers = clazz.getModifiers();
        Class<?> superclass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that ItemManager is public", Modifier.isPublic(modifiers));
        Assert.assertFalse("Ensure that ItemManager is not abstract", Modifier.isAbstract(modifiers));
        Assert.assertEquals("Ensure that ItemManager extends Object", Object.class, superclass);
        // It should implement IItemManager
        Assert.assertEquals("Ensure that ItemManager implements exactly one interface", 1, interfaces.length);
        Assert.assertEquals("Ensure that ItemManager implements IItemManager", IItemManager.class, interfaces[0]);
    }

    /**
     * Helper method to initialize the private 'itemList' field of an ItemManager.
     */
    private void initializeItemList(ItemManager manager) throws Exception {
        Field field = ItemManager.class.getDeclaredField("itemList");
        field.setAccessible(true);
        field.set(manager, new ArrayList<Item>());
    }

    /**
     * Test the loadItems() method by creating a temporary items.txt file.
     */
    @Test(timeout = 1000)
    public void testLoadItems() throws Exception {
        // Create a temporary items.txt file with known content.
        PrintWriter writer = new PrintWriter(new FileWriter("items.txt"));
        writer.println("1,TestItem1,10.0,test1.jpg,100");
        writer.println("2,TestItem2,20.0,test2.jpg,101");
        writer.close();

        // Create an ItemManager instance.
        ItemManager manager = new ItemManager();
        // In case itemList was not initialized in the constructor,
        // initialize it manually using reflection.
        initializeItemList(manager);
        // Now explicitly call loadItems().
        manager.loadItems();

        // Access the private itemList field.
        Field field = ItemManager.class.getDeclaredField("itemList");
        field.setAccessible(true);
        List<Item> items = (List<Item>) field.get(manager);

        Assert.assertNotNull("After loadItems, itemList should not be null", items);
        Assert.assertEquals("loadItems should load 2 items", 2, items.size());

        // Verify details of the first item.
        Item first = items.get(0);
        Assert.assertEquals("First item's name should be 'TestItem1'", "TestItem1", first.getName());
        Assert.assertEquals("First item's price should be 10.0", 10.0, first.getPrice(), 0.001);
        Assert.assertEquals("First item's picture filename should be 'test1.jpg'", "test1.jpg", first.getPictureFileName());
        Assert.assertEquals("First item's seller ID should be 100", 100, first.getSellerID());
    }

    /**
     * Test the createItem() method.
     */
    @Test(timeout = 1000)
    public void testCreateItem() throws Exception {
        // Initialize an ItemManager instance with an empty itemList.
        ItemManager manager = new ItemManager();
        initializeItemList(manager);

        // Create a dummy seller.
        User seller = new User("seller", "pass");

        // Call createItem.
        boolean created = manager.createItem(seller, "NewItem", 15.5, "new.jpg");
        Assert.assertTrue("createItem should return true", created);

        // Access itemList via reflection.
        Field field = ItemManager.class.getDeclaredField("itemList");
        field.setAccessible(true);
        List<Item> items = (List<Item>) field.get(manager);

        Assert.assertEquals("After createItem, itemList should contain 1 item", 1, items.size());
        Item item = items.get(0);
        Assert.assertEquals("Created item's name should be 'NewItem'", "NewItem", item.getName());
        Assert.assertEquals("Created item's price should be 15.5", 15.5, item.getPrice(), 0.001);
        Assert.assertEquals("Created item's picture filename should be 'new.jpg'", "new.jpg", item.getPictureFileName());
        Assert.assertEquals("Created item's seller ID should match seller's userID", seller.getUserID(), item.getSellerID());
    }

    /**
     * Test the deleteItem() method.
     */
    @Test(timeout = 1000)
    public void testDeleteItem() throws Exception {
        ItemManager manager = new ItemManager();
        initializeItemList(manager);

        User seller = new User("seller", "pass");
        // Create two items.
        manager.createItem(seller, "ItemToDelete", 20.0, "del.jpg");
        manager.createItem(seller, "ItemToKeep", 25.0, "keep.jpg");

        // Access the private itemList field.
        Field field = ItemManager.class.getDeclaredField("itemList");
        field.setAccessible(true);
        List<Item> items = (List<Item>) field.get(manager);

        Assert.assertEquals("Before deletion, itemList should contain 2 items", 2, items.size());

        // Get the itemID of the first item (to delete).
        int deleteID = items.get(0).getItemID();
        boolean deleted = manager.deleteItem(deleteID, seller);
        Assert.assertTrue("deleteItem should return true for a successful deletion", deleted);

        // Check that the itemList now contains only 1 item.
        Assert.assertEquals("After deletion, itemList should contain 1 item", 1, items.size());

        // Attempt to delete an item that doesn't exist.
        boolean notDeleted = manager.deleteItem(9999, seller);
        Assert.assertFalse("deleteItem should return false when no matching item is found", notDeleted);
    }

    /**
     * Test the searchItems() method.
     */
    @Test(timeout = 1000)
    public void testSearchItems() throws Exception {
        ItemManager manager = new ItemManager();
        initializeItemList(manager);

        User seller = new User("seller", "pass");
        // Add multiple items.
        manager.createItem(seller, "Apple", 5.0, "apple.jpg");
        manager.createItem(seller, "Banana", 3.0, "banana.jpg");
        manager.createItem(seller, "Grape", 4.0, "grape.jpg");
        manager.createItem(seller, "Pineapple", 6.0, "pineapple.jpg");

        // Search for items containing "Apple".
        List<Item> searchResult = manager.searchItems("Apple");
        // Should match both "Apple" and "Pineapple".
        Assert.assertEquals("searchItems should return 2 items when searching for 'Apple'", 2, searchResult.size());

        // Verify that each returned item's name contains "Apple".
        for (Item item : searchResult) {
            Assert.assertTrue("Each search result should contain 'Apple' in the name", item.getName().contains("Apple"));
        }
    }
}
