public interface IPaymentManager {
    boolean processPayment(User buyer, User seller, double amount);
    double getUserBalance(User user);
    boolean updateBalance(User user, double amount);
    void loadPayments();
    void savePayments();
}
