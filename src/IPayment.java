import java.time.LocalDateTime;

public interface IPayment {
    int getPaymentID();
    int getBuyerID();
    int getSellerID();
    double getAmount();
    LocalDateTime getTimestamp();
    PaymentStatus getStatus();
    void setStatus(PaymentStatus status);
}
