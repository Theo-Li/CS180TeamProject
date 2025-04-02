import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
/**
 *
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @author L15 Team 1
 * @version March 1, 2025
 */
public class User {
    private int userID;
    private static AtomicInteger userCount = new AtomicInteger(0);
    private String username;
    private String password;
    private double balance;
    private ArrayList<Item> items;


    //Constructor for User class
    public User(String username, String password){
        this.username = username;
        this.password = password;
        if(userCount == null) {
            userCount = new AtomicInteger(1);
        } else {
            userID = userCount.getAndIncrement();
        }
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items){
        this.items = items;
    }



}
