import java.util.List;
/**
 *
 *
 *
 * @author Lex Borrero
 * @version 2025-04-02
 */
public interface IItemManager {
     void loadItems();
     void saveItems();
    boolean createItem(User seller, String name, double price, String pictureFilename);
    boolean deleteItem(int itemID, User seller);
    List<Item> searchItems(String keyword);

}
