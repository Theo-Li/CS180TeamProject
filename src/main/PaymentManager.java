package main;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * The PaymentManager class manages payment transactions.
 * It implements the IPaymentManager interface to handle loading, saving,
 * and processing payment data.
 *
 * Author: Tianzhi Li
 * Version: 2025-04-02
 */
public class PaymentManager implements IPaymentManager {
    // List to hold all Payment objects in memory.
    private List<Payment> paymentList = new ArrayList<>();

    // The filename used for persisting payment data.
    private final String paymentFile = "payments.txt";

    /**
     * Constructor for PaymentManager.
     * Initializes the payment list by loading payments from the file.
     */
    public PaymentManager() {
        loadPayments();
    }

    /**
     * Loads payment records from the file into the paymentList.
     *
     * Steps:
     * 1. Clears the current paymentList.
     * 2. Opens the paymentFile for reading using a BufferedReader.
     * 3. Reads the file line by line.
     * 4. Splits each line into parts using a comma as the delimiter.
     * 5. Checks that there are at least 6 parts in the line.
     * 6. Parses the buyerID, sellerID, amount, timestamp, and status.
     * 7. Creates a new Payment object with the parsed data.
     * 8. Adds the Payment object to the paymentList.
     * 9. Handles any IOException that may occur during file reading.
     */
    public synchronized void loadPayments() {
        paymentList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(paymentFile))) {
            String line;
            // Read the file line by line.
            while ((line = br.readLine()) != null) {
                // Split the line using a comma delimiter.
                String[] parts = line.split(",");
                // Ensure the line has at least 6 parts.
                if (parts.length >= 6) {
                    // Parse the required fields from the parts.
                    int buyerID = Integer.parseInt(parts[1].trim());
                    int sellerID = Integer.parseInt(parts[2].trim());
                    double amount = Double.parseDouble(parts[3].trim());
                    LocalDateTime timestamp = LocalDateTime.parse(parts[4].trim());
                    PaymentStatus status = PaymentStatus.valueOf(parts[5].trim());
                    // Create a new Payment object with the parsed data.
                    Payment payment = new Payment(buyerID, sellerID, amount, status);
                    // Add the Payment object to the list.
                    paymentList.add(payment);
                }
            }
        } catch (IOException e) {
            // Output an error message if file reading fails.
            System.out.println("Failed to load payments: " + e.getMessage());
        }
    }

    /**
     * Saves the current paymentList to the paymentFile.
     *
     * Steps:
     * 1. Opens the paymentFile for writing using a BufferedWriter.
     * 2. Iterates over each Payment in the paymentList.
     * 3. Formats each Payment's data into a comma-separated string.
     * 4. Writes the formatted string to the file, followed by a new line.
     * 5. Handles any IOException that may occur during file writing.
     */
    public synchronized void savePayments() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(paymentFile))) {
            // Iterate through each payment in the list.
            for (Payment payment : paymentList) {
                // Format the payment data as a comma-separated string.
                String line = payment.getPaymentID() + "," +
                        payment.getBuyerID() + "," +
                        payment.getSellerID() + "," +
                        payment.getAmount() + "," +
                        payment.getTimestamp().toString() + "," +
                        payment.getStatus().name();
                // Write the line to the file.
                bw.write(line);
                // Write a new line after each payment record.
                bw.newLine();
            }
        } catch (IOException e) {
            // Output an error message if file writing fails.
            System.out.println("Failed to save payments: " + e.getMessage());
        }
    }

    /**
     * Processes a payment transaction between a buyer and a seller.
     *
     * Steps:
     * 1. Creates a new Payment object with the buyer and seller IDs, amount,
     *    and sets the status to COMPLETED.
     * 2. Adds the new Payment to the paymentList.
     * 3. Calls savePayments() to persist the new payment.
     * 4. Returns true indicating successful processing.
     *
     * @param buyer the User who is making the payment.
     * @param seller the User who is receiving the payment.
     * @param amount the monetary amount of the payment.
     * @return true if the payment was processed successfully.
     */
    @Override
    public synchronized boolean processPayment(User buyer, User seller, double amount) {
        // Create a new Payment with status COMPLETED.
        Payment payment = new Payment(buyer.getUserID(), seller.getUserID(), amount, PaymentStatus.COMPLETED);
        // Add the new Payment to the list.
        paymentList.add(payment);
        // Save the updated list of payments to the file.
        savePayments();
        return true;
    }

    /**
     * Retrieves the current balance of the specified user.
     *
     * @param user the User whose balance is being queried.
     * @return the user's current balance.
     */
    @Override
    public synchronized double getUserBalance(User user) {
        // Return the balance from the User object.
        return user.getBalance();
    }

    /**
     * Updates the balance of the specified user by adding the given amount.
     *
     * Steps:
     * 1. Retrieves the user's current balance.
     * 2. Adds the specified amount to the current balance.
     * 3. Updates the User object's balance.
     * 4. Returns true indicating that the update was successful.
     *
     * @param user the User whose balance is to be updated.
     * @param amount the amount to add (or subtract if negative).
     * @return true if the balance update was successful.
     */
    @Override
    public synchronized boolean updateBalance(User user, double amount) {
        // Update the user's balance by adding the specified amount.
        user.setBalance(user.getBalance() + amount);
        return true;
    }
}
