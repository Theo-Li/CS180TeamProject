import java.util.concurrent.atomic.AtomicInteger;

public class Item {
    private int itemID;
    private static AtomicInteger itemCount = new AtomicInteger(1);
    private String name;
    private double price;
    private String pictureFilename;
    private int sellerID;


    public Item(String name, double price, String pictureFilename, int sellerID) {
        this.name = name;
        this.price = price;
        this.pictureFilename = pictureFilename;
        this.sellerID = sellerID;

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
