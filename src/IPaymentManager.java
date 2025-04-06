/**
 *
 *
 *
 * @author Tianzhi Li
 * @version 2025-04-02
 */
public interface IPaymentManager {
    boolean processPayment(User buyer, User seller, double amount);
    double getUserBalance(User user);
    boolean updateBalance(User user, double amount);
    void loadPayments();
    void savePayments();
}
