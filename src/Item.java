import java.util.concurrent.atomic.AtomicInteger;
/**
 *
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

    public synchronized int getItemID() {
        return itemID;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized double getPrice() {
        return price;
    }

    public synchronized void setPrice(double price) {
        this.price = price;
    }

    public synchronized String getPictureFilename() {
        return pictureFilename;
    }

    public synchronized void setPictureFilename(String pictureFilename) {
        this.pictureFilename = pictureFilename;
    }

    public synchronized int getSellerID() {
        return sellerID;
    }

    public synchronized void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

}
