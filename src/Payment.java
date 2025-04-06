import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Payment implements IPayment {
    private int paymentID;
    private static AtomicInteger paymentCount = new AtomicInteger(0);
    private int buyerID;
    private int sellerID;
    private double amount;
    private LocalDateTime timestamp;
    private PaymentStatus status;

    public Payment(int buyerID, int sellerID, double amount, PaymentStatus status) {
        this.paymentID = paymentCount.incrementAndGet();
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

    @Override
    public synchronized int getPaymentID() {
        return paymentID;
    }

    @Override
    public synchronized int getBuyerID() {
        return buyerID;
    }

    @Override
    public synchronized int getSellerID() {
        return sellerID;
    }

    @Override
    public synchronized double getAmount() {
        return amount;
    }

    @Override
    public synchronized LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public synchronized PaymentStatus getStatus() {
        return status;
    }

    @Override
    public synchronized void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
