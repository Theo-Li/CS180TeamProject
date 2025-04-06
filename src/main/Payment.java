package main;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Payment class represents a payment transaction between a buyer and a seller.
 * It implements the IPayment interface and encapsulates details such as payment ID,
 * buyer ID, seller ID, amount, timestamp, and payment status.
 *
 * Author: Tianzhi Li
 * Version: 2025-04-02
 */
public class Payment implements IPayment {

    // Unique identifier for this payment.
    private int paymentID;

    // Atomic counter to generate unique payment IDs in a thread-safe manner.
    private static AtomicInteger paymentCount = new AtomicInteger(0);

    // Identifier for the buyer in the transaction.
    private int buyerID;

    // Identifier for the seller in the transaction.
    private int sellerID;

    // The monetary amount of the payment.
    private double amount;

    // Timestamp marking the creation time of this payment.
    private LocalDateTime timestamp;

    // The current status of the payment (e.g., pending, completed, failed).
    private PaymentStatus status;

    /**
     * Constructs a new Payment object with the specified buyer, seller, amount, and status.
     * Automatically assigns a unique paymentID and sets the timestamp to the current time.
     *
     * @param buyerID   the unique identifier for the buyer.
     * @param sellerID  the unique identifier for the seller.
     * @param amount    the amount of the payment.
     * @param status    the initial status of the payment.
     */
    public Payment(int buyerID, int sellerID, double amount, PaymentStatus status) {
        // Generate and assign a unique payment ID.
        this.paymentID = paymentCount.incrementAndGet();
        // Set the buyer and seller identifiers.
        this.buyerID = buyerID;
        this.sellerID = sellerID;
        // Set the amount for this payment.
        this.amount = amount;
        // Capture the current date and time as the timestamp.
        this.timestamp = LocalDateTime.now();
        // Set the initial payment status.
        this.status = status;
    }

    /**
     * Returns the unique payment ID.
     *
     * @return the paymentID.
     */
    @Override
    public synchronized int getPaymentID() {
        return paymentID;
    }

    /**
     * Returns the buyer's ID associated with this payment.
     *
     * @return the buyerID.
     */
    @Override
    public synchronized int getBuyerID() {
        return buyerID;
    }

    /**
     * Returns the seller's ID associated with this payment.
     *
     * @return the sellerID.
     */
    @Override
    public synchronized int getSellerID() {
        return sellerID;
    }

    /**
     * Returns the amount of this payment.
     *
     * @return the payment amount.
     */
    @Override
    public synchronized double getAmount() {
        return amount;
    }

    /**
     * Returns the timestamp when the payment was created.
     *
     * @return the timestamp.
     */
    @Override
    public synchronized LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the current status of the payment.
     *
     * @return the payment status.
     */
    @Override
    public synchronized PaymentStatus getStatus() {
        return status;
    }

    /**
     * Updates the status of the payment.
     *
     * @param status the new payment status.
     */
    @Override
    public synchronized void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
