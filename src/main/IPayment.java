package main;
import java.time.LocalDateTime;
/**
 *
 *
 *
 * @author Tianzhi Li
 * @version 2025-04-02
 */
public interface IPayment {
    int getPaymentID();
    int getBuyerID();
    int getSellerID();
    double getAmount();
    LocalDateTime getTimestamp();
    PaymentStatus getStatus();
    void setStatus(PaymentStatus status);
}
