public interface IUserManager {
    boolean registerUser(String username, String password);
    User login(String username, String password);
    boolean deleteUser(int userID);
    User getUser(int userID);
    void loadUsers();
    void saveUsers();
}
