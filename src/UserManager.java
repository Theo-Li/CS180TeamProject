import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserManager implements IUserManager {
    private List<User> userList = new ArrayList<>();
    private final String userFile = "users.txt";

    public UserManager() {
        loadUsers();
    }

    public synchronized void loadUsers() {
        userList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    double balance = Double.parseDouble(parts[3].trim());
                    User user = new User(username, password);
                    user.setBalance(balance);
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load user data: " + e.getMessage());
        }
    }

    public synchronized void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile))) {
            for (User user : userList) {
                String line = user.getUserID() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getBalance();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save user data: " + e.getMessage());
        }
    }

    public synchronized boolean addUser(User user) {
        for (User u : userList) {
            if (u.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        userList.add(user);
        saveUsers();
        return true;
    }

    public synchronized boolean deleteUser(int userID) {
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUserID() == userID) {
                iterator.remove();
                saveUsers();
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean registerUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        User newUser = new User(username, password);
        userList.add(newUser);
        saveUsers();
        return true;
    }

    @Override
    public synchronized User login(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public synchronized User getUser(int userID) {
        for (User user : userList) {
            if (user.getUserID() == userID) {
                return user;
            }
        }
        return null;
    }
}
