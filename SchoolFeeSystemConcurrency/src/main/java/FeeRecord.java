/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Windows 11
 */
public class FeeRecord {
    private int studentId;
    private int amount;
    private boolean isPaid;

    // Constructor
    public FeeRecord(int studentId, int amount, boolean isPaid) {
        this.studentId = studentId;
        this.amount = amount;
        this.isPaid = isPaid;
    }

    // Getters/Setters (NetBeans can generate these: Right-click → Insert Code)
    public int getStudentId() { return studentId; }
    public int getAmount() { return amount; }
    public boolean isPaid() { return isPaid; }
}
