/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Windows 11
 */
import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FeeDatabase {
private static final String URL = "jdbc:postgresql://localhost:5432/SchoolFeeSystemConcurrency";
private static final String USER = "postgres";  // Default superuser
private static final String PASSWORD = "itsmysql"; // Replace with your passwordd
    private static final Lock reportLock = new ReentrantLock();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    public static void submitPayment(int studentId, int amount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
            
            PreparedStatement select = conn.prepareStatement(
                "SELECT amount FROM fee_records WHERE student_id = ? FOR UPDATE");
            select.setInt(1, studentId);
            ResultSet rs = select.executeQuery();

            if (rs.next()) {
                PreparedStatement update = conn.prepareStatement(
                    "UPDATE fee_records SET amount = ?, is_paid = true WHERE student_id = ?");
                update.setInt(1, amount);
                update.setInt(2, studentId);
                update.executeUpdate();
            } else {
                PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO fee_records (student_id, amount, is_paid) VALUES (?, ?, true)");
                insert.setInt(1, studentId);
                insert.setInt(2, amount);
                insert.executeUpdate();
            }
            conn.commit();
        }
    }

    public static void generateReport() {
        reportLock.lock();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM fee_records");
            System.out.println("=== Fee Report ===");
            while (rs.next()) {
                System.out.printf("Student ID %d: $%d (Paid: %b)\n",
                    rs.getInt("student_id"),
                    rs.getInt("amount"),
                    rs.getBoolean("is_paid"));
            }
        } catch (SQLException e) {
            System.err.println("Report generation failed!");
            e.printStackTrace();
        } finally {
            reportLock.unlock();
        }
    }
}