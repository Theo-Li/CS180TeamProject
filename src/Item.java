import java.util.concurrent.atomic.AtomicInteger;
/**
 * Class that holds information for items
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @author L15 Team 1
 * @version March 1, 2025
 */
public class Item {
    // Unique identifier for each item
    private int itemID;

    // Static thread-safe counter to generate unique item IDs for each Item instance
    private static AtomicInteger itemCount = new AtomicInteger(0);

    // Name of the item
    private String name;

    // Price of the item
    private double price;

    // Filename for the item's picture
    private String pictureFilename;

    // ID of the seller who listed the item
    private int sellerID;

    // Constructor for the Item class that initializes name, price, pictureFilename, and sellerID
    public Item(String name, double price, String pictureFilename, int sellerID) throws IllegalArgumentException{
        // Set the name of the item
        this.name = name;
        // Set the price of the item
        this.price = price;
        // Set the filename for the item's picture
        this.pictureFilename = pictureFilename;
        // Set the seller's ID for the item
        this.sellerID = sellerID;

        // Although itemCount is already initialized, this check is in place to ensure it's not null.
        if (itemCount == null) {
            itemCount = new AtomicInteger(1);
        } else {
            // Assign a unique itemID using the atomic counter, then increment it for the next item.
            itemID = itemCount.getAndIncrement();
        }
    }

    //Empty constructor
    public Item() {

    }

    // Getter method for itemID
    public int getItemID() {
        return itemID;
    }

    //Setter method for itermID
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    // Getter method for the item's name
    public String getName() {
        return name;
    }

    // Setter method for the item's name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for the item's price
    public double getPrice() {
        return price;
    }

    // Setter method for the item's price
    public void setPrice(double price) {
        this.price = price;
    }

    // Getter method for the item's picture filename
    public String getPictureFilename() {
        return pictureFilename;
    }

    // Setter method for the item's picture filename
    public void setPictureFilename(String pictureFilename) {
        this.pictureFilename = pictureFilename;
    }

    // Getter method for the seller's ID associated with the item
    public int getSellerID() {
        return sellerID;
    }

    // Setter method for updating the seller's ID for the item
    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }
}
