import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentManager implements IPaymentManager {
    private List<Payment> paymentList = new ArrayList<>();
    private final String paymentFile = "payments.txt";

    public PaymentManager() {
        loadPayments();
    }

    public synchronized void loadPayments() {
        paymentList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(paymentFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int buyerID = Integer.parseInt(parts[1].trim());
                    int sellerID = Integer.parseInt(parts[2].trim());
                    double amount = Double.parseDouble(parts[3].trim());
                    LocalDateTime timestamp = LocalDateTime.parse(parts[4].trim());
                    PaymentStatus status = PaymentStatus.valueOf(parts[5].trim());
                    Payment payment = new Payment(buyerID, sellerID, amount, status);
                    paymentList.add(payment);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load payments: " + e.getMessage());
        }
    }

    public synchronized void savePayments() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(paymentFile))) {
            for (Payment payment : paymentList) {
                String line = payment.getPaymentID() + "," +
                        payment.getBuyerID() + "," +
                        payment.getSellerID() + "," +
                        payment.getAmount() + "," +
                        payment.getTimestamp().toString() + "," +
                        payment.getStatus().name();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save payments: " + e.getMessage());
        }
    }

    @Override
    public synchronized boolean processPayment(User buyer, User seller, double amount) {
        Payment payment = new Payment(buyer.getUserID(), seller.getUserID(), amount, PaymentStatus.COMPLETED);
        paymentList.add(payment);
        savePayments();
        return true;
    }

    @Override
    public synchronized double getUserBalance(User user) {
        return user.getBalance();
    }

    @Override
    public synchronized boolean updateBalance(User user, double amount) {
        user.setBalance(user.getBalance() + amount);
        return true;
    }
}
