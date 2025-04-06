import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 *
 * @author Lex Borrero
 * @version 2025-04-02
 */
public class ItemManager implements IItemManager {
    private List<Item> itemList;
    private final String itemFile = "items.txt";

    public ItemManager(){
        loadItems();
    }


    @Override
    public synchronized void loadItems() {
        itemList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(itemFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    String pictureFilename = parts[3].trim();
                    int sellerID = Integer.parseInt(parts[4].trim());
                    Item item = new Item(name, price, pictureFilename,sellerID);
                    itemList.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load item data: " + e.getMessage());
        }

    }

    @Override
    public synchronized void saveItems() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(itemFile))) {
            for (Item item : itemList) {
                String line = item.getItemID() + "," + item.getName() + "," + item.getPrice() + ","
                        + item.getPictureFilename() + "," + item.getSellerID();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save item data: " + e.getMessage());
        }

    }

    @Override
    public synchronized boolean createItem(User seller, String name, double price, String pictureFilename) {
        Item newItem = new Item(name, price, pictureFilename, seller.getUserID());
        itemList.add(newItem);
        saveItems();
        return true;
    }

    @Override
    public boolean deleteItem(int itemID, User seller) {
        Iterator<Item> iterator = itemList.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getItemID() == itemID && item.getSellerID() == seller.getUserID()) {
                iterator.remove();
                saveItems();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Item> searchItems(String keyword) {
        return List.of();
    }
}
