import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * The class represents a system user (which could be a buyer or a seller) and holds their basic information.
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @author L15 Team 1
 * @version March 1, 2025
 */
public class User {
    // Unique identifier for each user
    private int userID;

    // Static thread-safe counter to generate unique user IDs for each User instance
    private static AtomicInteger userCount = new AtomicInteger(0);

    // The username chosen by the user
    private String username;

    // The user's password (note: in production, passwords should be securely hashed)
    private String password;

    // The account balance for the user
    private double balance;

    // List of items associated with the user (e.g., items for sale or owned items)
    private ArrayList<Item> items;

    // Constructor for the User class that initializes username and password
    public User(String username, String password) {
        // Set the username and password with the provided values
        this.username = username;
        this.password = password;

        // Ensure userCount is not null (this check is typically redundant because it's already initialized)
        if (userCount == null) {
            userCount = new AtomicInteger(1);
        } else {
            // Assign a unique userID using the atomic counter, then increment it for the next user
            userID = userCount.getAndIncrement();
        }
    }

    // Getter method for userID
    public int getUserID() {
        return userID;
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Getter method for the user's balance
    public double getBalance() {
        return balance;
    }

    // Setter method for updating the user's balance
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Getter method for the user's list of items
    public ArrayList<Item> getItems() {
        return items;
    }

    // Setter method for updating the user's list of items
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
