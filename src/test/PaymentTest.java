package test;
import main.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.io.*;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases for the Payment class.
 *
 * <p>Purdue University -- CS18000 -- Spring 2025</p>
 *
 * @authors Fang Rui Shen
 * @version April 6, 2025
 */
public class PaymentTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PaymentTest.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    // Fields for capturing system output/input (if needed)
    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUpStreams() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOutput);
        System.setIn(originalSysin);
    }

    private String getOutput() {
        return testOut.toString();
    }

    private void setInput(String input) {
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }

    /**
     * Test that the Payment class is declared correctly.
     */
    @Test(timeout = 1000)
    public void testPaymentClassDeclaration() {
        Class<?> clazz = Payment.class;
        int modifiers = clazz.getModifiers();
        Class<?> superclass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();

        Assert.assertTrue("Ensure that Payment is public", Modifier.isPublic(modifiers));
        Assert.assertFalse("Ensure that Payment is not abstract", Modifier.isAbstract(modifiers));
        Assert.assertEquals("Ensure that Payment extends Object", Object.class, superclass);
        // Payment implements IPayment, so there should be exactly one interface.
        Assert.assertEquals("Ensure that Payment implements exactly one interface", 1, interfaces.length);
        Assert.assertEquals("Ensure that Payment implements IPayment", IPayment.class, interfaces[0]);
    }

    /**
     * Test basic field values and methods of Payment.
     */
    @Test(timeout = 1000)
    public void testPaymentFieldsAndMethods() {
        Payment payment = new Payment(1, 1, 10.00, PaymentStatus.COMPLETED);
        LocalDateTime testAfterTimestamp = LocalDateTime.now();

        Assert.assertTrue("getPaymentID() returns invalid value", payment.getPaymentID() >= 0);

        Assert.assertEquals("getBuyerID() returns incorrect buyerID", 1, payment.getBuyerID());
        Assert.assertEquals("getSellerID() returns incorrect sellerID", 1, payment.getSellerID());
        Assert.assertEquals("getAmount() returns incorrect amount", 10.0, payment.getAmount(), 0.001);
        Assert.assertEquals("getTimestamp() returns incorrect timestamp", payment.getTimestamp(), testAfterTimestamp);
        Assert.assertEquals("getStatus() returns incorrect paymentStatus", PaymentStatus.COMPLETED, payment.getStatus());
        
        payment.setStatus(PaymentStatus.FAILED);
        Assert.assertEquals("Ensure setStatus() updates paymentStatus correctly", PaymentStatus.FAILED, payment.getStatus());
    }

    /**
     * Test that payment IDs increment sequentially.
     */
    @Test(timeout = 1000)
    public void testPaymentIdIncrement() {
        Payment payment1 = new Payment(1, 1, 1.00, PaymentStatus.PENDING);
        Payment payment2 = new Payment(3, 3, 2.00, PaymentStatus.PENDING);
        Assert.assertEquals("Payment IDs should increment sequentially", payment1.getPaymentID() + 1, payment2.getPaymentID());
    }
}


