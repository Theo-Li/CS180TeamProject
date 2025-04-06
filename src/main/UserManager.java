package main;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The UserManager class manages user-related operations.
 * It implements the IUserManager interface and handles loading,
 * saving, registering, logging in, and retrieving users.
 *
 * Author: Tianzhi Li
 * Version: 2025-04-02
 */
public class UserManager implements IUserManager {
    // A list to store User objects.
    // It holds all the users managed by this class.
    private List<User> userList = new ArrayList<>();

    // The filename for storing user data persistently.
    // The file "users.txt" is used for reading and writing user details.
    private final String userFile = "users.txt";

    /**
     * Constructor for UserManager.
     * Initializes the user manager by loading user data from the file.
     */
    public UserManager() {
        loadUsers();
    }

    /**
     * Loads users from the data file into the userList.
     *
     * This method performs the following steps:
     * 1. Clears the current userList to remove any existing users.
     * 2. Opens the file specified by userFile using a BufferedReader.
     * 3. Reads the file line by line; each line should have comma-separated values.
     * 4. Splits each line into parts and checks if there are at least 4 parts.
     * 5. Parses the username, password, and balance from the line.
     * 6. Creates a new User object with the parsed username and password.
     * 7. Sets the balance for the User and adds the user to userList.
     * 8. Catches and handles any IOException during file reading.
     *
     * The method is synchronized to ensure thread safety.
     */
    public synchronized void loadUsers() {
        // Clear any existing users.
        userList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            // Read file line by line.
            while ((line = br.readLine()) != null) {
                // Split the line into parts using a comma as a delimiter.
                String[] parts = line.split(",");
                // Ensure there are at least 4 parts (userID, username, password, balance).
                if (parts.length >= 4) {
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    double balance = Double.parseDouble(parts[3].trim());
                    // Create a new User with the provided username and password.
                    User user = new User(username, password);
                    // Set the user's balance.
                    user.setBalance(balance);
                    // Add the new user to the userList.
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            // Print an error message if file reading fails.
            System.out.println("Failed to load user data: " + e.getMessage());
        }
    }

    /**
     * Saves the current userList to the data file.
     *
     * This method performs the following steps:
     * 1. Opens the file specified by userFile using a BufferedWriter.
     * 2. Iterates over each User in userList.
     * 3. Formats each user's details into a comma-separated string.
     * 4. Writes the formatted string to the file, one user per line.
     * 5. Catches and handles any IOException during file writing.
     *
     * The method is synchronized to ensure thread safety.
     */
    public synchronized void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile))) {
            // Loop through all users in the list.
            for (User user : userList) {
                // Format the user's details: userID,username,password,balance.
                String line = user.getUserID() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getBalance();
                // Write the line to the file.
                bw.write(line);
                // Start a new line for the next user.
                bw.newLine();
            }
        } catch (IOException e) {
            // Print an error message if file writing fails.
            System.out.println("Failed to save user data: " + e.getMessage());
        }
    }

    /**
     * Adds a new user to the userList if the username is unique.
     *
     * This method performs the following steps:
     * 1. Checks whether a user with the same username already exists.
     * 2. If a duplicate is found, returns false.
     * 3. If no duplicate is found, adds the new user to userList.
     * 4. Calls saveUsers() to persist the updated list.
     * 5. Returns true to indicate the user was added successfully.
     *
     * @param user the User object to be added.
     * @return true if the user is added; false if a duplicate username exists.
     */
    public synchronized boolean addUser(User user) {
        // Iterate over the user list to check for duplicate usernames.
        for (User u : userList) {
            if (u.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        // Add the new user if no duplicate is found.
        userList.add(user);
        // Persist the updated user list.
        saveUsers();
        return true;
    }

    /**
     * Deletes a user from the userList based on the userID.
     *
     * This method performs the following steps:
     * 1. Iterates over userList using an iterator.
     * 2. Checks if the current user's ID matches the provided userID.
     * 3. If a match is found, removes the user from the list.
     * 4. Calls saveUsers() to persist the updated list.
     * 5. Returns true if a user is successfully deleted.
     * 6. Returns false if no matching user is found.
     *
     * @param userID the unique identifier of the user to be deleted.
     * @return true if the user was deleted; false otherwise.
     */
    public synchronized boolean deleteUser(int userID) {
        // Create an iterator to traverse the user list safely.
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            // Check for a matching userID.
            if (user.getUserID() == userID) {
                // Remove the matching user.
                iterator.remove();
                // Persist the updated user list.
                saveUsers();
                return true;
            }
        }
        // Return false if no user with the provided userID is found.
        return false;
    }

    /**
     * Registers a new user by creating a new User object and adding it to the userList.
     *
     * This method performs the following steps:
     * 1. Checks if a user with the given username already exists.
     * 2. If the username is taken, returns false.
     * 3. Otherwise, creates a new User with the given username and password.
     * 4. Adds the new user to userList.
     * 5. Calls saveUsers() to persist the updated list.
     * 6. Returns true to indicate successful registration.
     *
     * @param username the username for the new user.
     * @param password the password for the new user.
     * @return true if registration is successful; false if the username already exists.
     */
    @Override
    public synchronized boolean registerUser(String username, String password) {
        // Check if the username is already in use.
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        // Create a new user with the provided credentials.
        User newUser = new User(username, password);
        // Add the new user to the user list.
        userList.add(newUser);
        // Persist the updated list.
        saveUsers();
        return true;
    }

    /**
     * Logs in a user by verifying the provided username and password.
     *
     * This method performs the following steps:
     * 1. Iterates over userList to find a user with the matching username and password.
     * 2. Returns the User object if a match is found.
     * 3. Returns null if no matching user is found.
     *
     * @param username the username to search for.
     * @param password the password to validate.
     * @return the User object if login is successful; null otherwise.
     */
    @Override
    public synchronized User login(String username, String password) {
        // Iterate through all users in the list.
        for (User user : userList) {
            // Check if both username and password match.
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        // Return null if no matching user is found.
        return null;
    }

    /**
     * Retrieves a user from the userList based on the userID.
     *
     * This method performs the following steps:
     * 1. Iterates over the userList.
     * 2. Checks if the current user's ID matches the provided userID.
     * 3. Returns the User object if a match is found.
     * 4. Returns null if no matching user is found.
     *
     * @param userID the unique identifier of the user.
     * @return the User object if found; null otherwise.
     */
    @Override
    public synchronized User getUser(int userID) {
        // Iterate through the user list.
        for (User user : userList) {
            // Check if the userID matches.
            if (user.getUserID() == userID) {
                return user;
            }
        }
        // Return null if no matching user is found.
        return null;
    }
}