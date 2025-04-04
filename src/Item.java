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
    private int itemID;
    private static AtomicInteger itemCount = new AtomicInteger(0);
    private String name;
    private double price;
    private String pictureFilename;
    private int sellerID;


    public Item(String name, double price, String pictureFilename, int sellerID) {
        this.name = name;
        this.price = price;
        this.pictureFilename = pictureFilename;
        this.sellerID = sellerID;
        if (itemCount == null) {
            itemCount = new AtomicInteger(1);
        } else {
            itemID = itemCount.getAndIncrement();
        }

    }

    public int getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPictureFilename() {
        return pictureFilename;
    }

    public void setPictureFilename(String pictureFilename) {
        this.pictureFilename = pictureFilename;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

}
