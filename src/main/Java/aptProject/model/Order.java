package aptProject.model;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private String status;          // PENDING, ACCEPTED, PREPARING, READY, COMPLETED, REJECTED
    private String paymentStatus;   // UNPAID, PAID
    private double totalPrice;
    private String specialNote;
    private Timestamp createdAt;
    private List<OrderItem> items;

    // Joined field – user's full name (for admin views)
    private String customerName;

    public Order() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getTotalPrice() {
        return totalPrice; }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSpecialNote() {
        return specialNote;
    }
    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItem> getItems() {
        return items; }
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** Convenience: formatted token like ADG-00042 */
    public String getToken() {
        return String.format("ADG-%05d", id);
    }
}
