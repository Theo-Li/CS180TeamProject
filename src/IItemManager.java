import java.util.List;

public interface IItemManager {
     void loadItems();
     void saveItems();
    boolean createItem(User seller, String name, double price, String pictureFilename);
    boolean deleteItem(int itemID, User seller);
    List<Item> searchItems(String keyword);

}
