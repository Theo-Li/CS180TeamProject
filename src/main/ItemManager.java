package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.File;
import java.nio.file.Files;

/**
 * The ItemManager class handles operations for managing a collection of items.
 * It implements the IItemManager interface and provides functionalities to load,
 * save, create, delete, and search for items.
 *
 * Author: Lex Borrero and Tianzhi Li
 * Version: 2025-04-02
 */
public class ItemManager implements IItemManager {

    // A list to store all items managed by this class.
    // Each element is an instance of the Item class.
    private List<Item> itemList;

    // The file name where item data is persisted.
    // This file is used to load and save items.
    private final String itemFile = "items.txt";

    /**
     * Constructor for ItemManager.
     * Initializes the item manager by loading items from the file.
     * Note: Ensure that 'itemList' is properly initialized (e.g., with an ArrayList)
     * before calling loadItems.
     */
    public ItemManager(){
        // Example initialization if not done elsewhere:
        // itemList = new ArrayList<>();
        loadItems();
    }

    /**
     * Loads items from the data file.
     *
     * This method performs the following steps:
     * 1. Clears the current list of items.
     * 2. Opens the file specified by 'itemFile' using a BufferedReader.
     * 3. Reads the file line by line, where each line should contain
     *    comma-separated values representing an item's details.
     * 4. Splits each line into parts and checks if there are at least 5 fields.
     * 5. Parses the necessary fields (name, price, picture filename, and seller ID).
     * 6. Creates a new Item object with the parsed data and adds it to 'itemList'.
     *
     * The method is synchronized to ensure thread-safe access.
     */
    @Override
    public synchronized void loadItems() {
        // Clear any existing items in the list.
        itemList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(itemFile))) {
            String line;
            // Read the file line by line.
            while ((line = br.readLine()) != null) {
                // Split the line into components using a comma as the delimiter.
                String[] parts = line.split(",");
                // Check that the line contains at least 5 parts.
                if (parts.length >= 5) {
                    // parts[0] is presumed to be the item ID but is not used for creating the Item.
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    String pictureFilename = parts[3].trim();
                    int sellerID = Integer.parseInt(parts[4].trim());
                    // Create a new Item using the parsed data.
                    Item item = new Item(name, price, pictureFilename, sellerID);
                    // Add the newly created item to the list.
                    itemList.add(item);
                }
            }
        } catch (IOException e) {
            // Print an error message if there is an issue reading the file.
            System.out.println("Failed to load item data: " + e.getMessage());
        }
    }

    /**
     * Saves the current list of items to the data file.
     *
     * This method performs the following steps:
     * 1. Opens the file specified by 'itemFile' using a BufferedWriter.
     * 2. Iterates over each Item in 'itemList'.
     * 3. Formats each item's details into a comma-separated string.
     * 4. Writes each formatted string to the file on a new line.
     *
     * The method is synchronized to ensure thread safety during the write operation.
     */
    @Override
    public synchronized void saveItems() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(itemFile))) {
            // Loop through all items in the list.
            for (Item item : itemList) {
                // Format the item's details as: itemID,name,price,pictureFilename,sellerID.
                String line = item.getItemID() + "," + item.getName() + "," + item.getPrice() + ","
                        + item.getPictureFileName() + "," + item.getSellerID();
                // Write the formatted line to the file.
                bw.write(line);
                // Write a newline character after each item.
                bw.newLine();
            }
        } catch (IOException e) {
            // Print an error message if there is an issue writing to the file.
            System.out.println("Failed to save item data: " + e.getMessage());
        }
    }

    /**
     * Creates a new item and adds it to the collection.
     *
     * This method performs the following steps:
     * 1. Creates a new Item object using the provided name, price, picture filename,
     *    and the seller's user ID.
     * 2. Adds the new item to the 'itemList'.
     * 3. Calls saveItems() to persist the new list to the data file.
     *
     * @param seller the User object representing the seller.
     * @param name the name of the new item.
     * @param price the price of the new item.
     * @param pictureFilename the filename for the item's picture.
     * @return true if the item was created and saved successfully.
     */
    @Override
    public synchronized boolean createItem(User seller, String name, double price, String pictureFilename) {
        // Create a new Item with the provided details and seller's user ID.
        Item newItem = new Item(name, price, pictureFilename, seller.getUserID());
        // Add the new item to the item list.
        itemList.add(newItem);
        // Save the updated item list to the file.
        saveItems();
        return true;
    }

    /**
     * Deletes an item from the collection based on item ID and seller.
     *
     * This method performs the following steps:
     * 1. Iterates over 'itemList' using an iterator.
     * 2. Checks each item to see if its ID matches 'itemID' and if its seller ID
     *    matches the given seller's user ID.
     * 3. If a matching item is found, it is removed from the list.
     * 4. Calls saveItems() to persist the updated list to the data file.
     *
     * @param itemID the unique identifier of the item to delete.
     * @param seller the User object representing the seller attempting to delete the item.
     * @return true if the item was found and deleted; false otherwise.
     */
    @Override
    public boolean deleteItem(int itemID, User seller) {
        // Create an iterator to safely remove an item during iteration.
        Iterator<Item> iterator = itemList.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            // Check if the current item matches both the itemID and seller's user ID.
            if (item.getItemID() == itemID && item.getSellerID() == seller.getUserID()) {
                // Remove the matching item.
                iterator.remove();
                // Save the updated item list to the file.
                saveItems();
                return true;
            }
        }
        // Return false if no matching item was found.
        return false;
    }

    /**
     * Searches for items whose names contain the given keyword.
     *
     * This method performs the following steps:
     * 1. Iterates over all items in 'itemList'.
     * 2. Checks if the item's name contains the search keyword.
     * 3. Adds matching items to a results list.
     * 4. Returns the list of matched items.
     *
     * @param keyword the string to search for within item names.
     * @return a list of items whose names contain the keyword.
     */
    @Override
    public List<Item> searchItems(String keyword) {
        // Create a new list to hold search results.
        List<Item> result = new ArrayList<>();
        // Iterate through each item in the collection.
        for (Item item : itemList) {
            // If the item's name includes the keyword, add it to the results.
            if (item.getName().contains(keyword)) {
                result.add(item);
            }
        }
        // Return the list of found items.
        return result;
    }


    /**
     * Converts the image located at the given path into a Base64 encoded string.
     *
     * @param imagePath The path to the image file.
     * @return A Base64 encoded string representing the image, or null if an error occurs.
     */
    public  String convertImageToBase64(String imagePath) {
        try {
            File file = new File(imagePath);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            System.out.println("Error converting image to Base64: " + e.getMessage());
            return null;
        }
    }

    /**
     * Displays the original image of an item.
     * The method searches for the item by its ID, then decodes the Base64-encoded image data,
     * and displays the image in a JFrame.
     *
     * @param itemID The unique identifier of the item whose image is to be displayed.
     */
    public synchronized void displayItemImage(int itemID) {
        for (Item item : itemList) {
            if (item.getItemID() == itemID) {
                // Retrieve the Base64 encoded image data from the item.
                String imageData = item.getPictureFileName();
                try {
                    // Decode the Base64 string to get the original image bytes.
                    byte[] imageBytes = Base64.getDecoder().decode(imageData);
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                    BufferedImage img = ImageIO.read(bis);

                    // Display the image in a JFrame.
                    JFrame frame = new JFrame("Item Image: " + item.getName());
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(400, 400);
                    JLabel label = new JLabel(new ImageIcon(img));
                    frame.add(label);
                    frame.setVisible(true);
                } catch (IOException ex) {
                    System.out.println("Error loading image: " + ex.getMessage());
                }
                return;
            }
        }
        System.out.println("Item with ID " + itemID + " not found.");
    }
}
