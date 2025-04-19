/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Windows 11
 */
import java.sql.SQLException;

public class PaymentThread extends Thread {
    private final int studentId;
    private final int amount;

    public PaymentThread(int studentId, int amount) {
        this.studentId = studentId;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            FeeDatabase.submitPayment(studentId, amount);
            System.out.println("Payment processed for student ID " + studentId);
        } catch (SQLException e) {
            System.err.println("Payment failed for student ID " + studentId);
            System.err.println("Error details: " + e.getMessage());
        }
    }
}