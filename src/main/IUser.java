package main;

import java.util.List;
/**
 *
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @authors Lex Borrero
 * @version 04-06-2025
 */
public interface IUser {

     int getUserID();
     String getUsername();
     String getPassword();
     double getBalance();
     void setBalance(double balance);
     List<Item> getItems();
     void setItems(List<Item> items);
     void addItem(Item item);
     boolean removeItem(Item item);


}
