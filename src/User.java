import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private int userID;
    private AtomicInteger userCount = new AtomicInteger(1);
    private String username;
    private String password;
    private double balance;
    private ArrayList<Item> items;

    //Constructor for User class
    public User(String username, String password){
        this.username = username;
        this.password = password;
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
