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
                if (parts.length >= 4) {
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim();
                    String pictureFilename = parts[3].trim();
                    int sellerID = Integer.parseInt(parts[4].trim());
                    Item item = new Item(String name, double price, String pictureFilename, int sellerID);
                    item.setPrice(price);
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
                String line = item.getSellerID() + "," + item.getName() + "," + item.getPictureFilename() + "," + item.getPrice();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save item data: " + e.getMessage());
        }

    }

    @Override
    public boolean createItem(User seller, String name, double price, String pictureFilename) {
        for (Item i : itemList) {
            if (i.getItemID().equals(item.getItemID())) {
                return false;
            }
        }
        itemList.add(item);
        saveItems();
        return true;

    }

    @Override
    public boolean deleteItem(int itemID, User seller) {
        Iterator<Item> iterator = itemList.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getItemID() == itemID) {
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
