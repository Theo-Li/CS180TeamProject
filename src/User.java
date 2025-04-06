<<<<<<< Updated upstream
<<<<<<< Updated upstream
import java.util.ArrayList;
=======
import java.util.List;
>>>>>>> Stashed changes
=======
import java.util.List;
>>>>>>> Stashed changes
import java.util.concurrent.atomic.AtomicInteger;
/**
 *
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @author L15 Team 1
 * @version March 1, 2025
 */
<<<<<<< Updated upstream
<<<<<<< Updated upstream
public class User {
    private int userID;
    private static AtomicInteger userCount = new AtomicInteger(0);
    private String username;
    private String password;
    private double balance;
    private ArrayList<Item> items;


    //Constructor for User class
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        if (userCount == null) {
            userCount = new AtomicInteger(1);
        } else {
=======
public class User implements IUser {
    // Unique identifier for the user.
    private int userID;

    // Thread-safe counter for generating unique user IDs for each new User instance.
    private static AtomicInteger userCount = new AtomicInteger(0);

    // The username chosen by the user.
    private String username;

    // The user's password (in production, passwords should be stored securely, e.g., hashed).
    private String password;

    // The account balance of the user.
    private double balance;

    // List of items associated with the user (e.g., items for sale or items owned).
    private List<Item> items;

    /**
     * Constructs a new User instance with a username and password.
     * It also assigns a unique userID using a thread-safe counter.
     *
     * @param username the username chosen by the user.
     * @param password the user's password.
     */
    public User(String username, String password) {
        // Set the username and password with the provided values.
        this.username = username;
        this.password = password;

        // Although userCount is already initialized, this check is included (but is redundant).
        if (userCount == null) {
            userCount = new AtomicInteger(1);
        } else {
            // Atomically assigns a unique ID to this user and increments the counter for future instances.
>>>>>>> Stashed changes
=======
public class User implements IUser {
    // Unique identifier for the user.
    private int userID;

    // Thread-safe counter for generating unique user IDs for each new User instance.
    private static AtomicInteger userCount = new AtomicInteger(0);

    // The username chosen by the user.
    private String username;

    // The user's password (in production, passwords should be stored securely, e.g., hashed).
    private String password;

    // The account balance of the user.
    private double balance;

    // List of items associated with the user (e.g., items for sale or items owned).
    private List<Item> items;

    /**
     * Constructs a new User instance with a username and password.
     * It also assigns a unique userID using a thread-safe counter.
     *
     * @param username the username chosen by the user.
     * @param password the user's password.
     */
    public User(String username, String password) {
        // Set the username and password with the provided values.
        this.username = username;
        this.password = password;

        // Although userCount is already initialized, this check is included (but is redundant).
        if (userCount == null) {
            userCount = new AtomicInteger(1);
        } else {
            // Atomically assigns a unique ID to this user and increments the counter for future instances.
>>>>>>> Stashed changes
            userID = userCount.getAndIncrement();
        }
    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    public synchronized int getUserID() {
        return userID;
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized String getPassword() {
        return password;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void setBalance(double balance) {
        this.balance = balance;
    }

    public synchronized ArrayList<Item> getItems() {
        return items;
    }

    public synchronized void setItems(ArrayList<Item> items) {
        this.items = items;
    }



}
=======
=======
>>>>>>> Stashed changes
    /**
     * Returns the unique identifier of the user.
     *
     * @return the userID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's password.
     *
     * @return the password (note: in a secure system, this would be a hashed value).
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the account balance of the user.
     *
     * @return the balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the account balance for the user.
     *
     * @param balance the new balance to set.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Returns the list of items associated with the user.
     *
     * @return the list of items.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Sets the list of items associated with the user.
     *
     * @param items the list of items to associate with the user.
     */
    @Override
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Adds an item to the user's list of items.
     *
     * @param item the item to add.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the user's list of items.
     *
     * @param item the item to remove.
     * @return true if the item was removed (this implementation always returns true).
     */
    public boolean removeItem(Item item) {
        items.remove(item);
        return true;
    }

    /**
     * Returns a string representation of the User object.
     * Currently, it defers to the superclass's implementation.
     *
     * @return a string representation of the user.
     */
    @Override
    public String toString() {
        return super.toString();
    }
<<<<<<< Updated upstream
}
>>>>>>> Stashed changes
=======
}
>>>>>>> Stashed changes
