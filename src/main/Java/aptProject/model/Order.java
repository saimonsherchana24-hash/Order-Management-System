package aptProject.model;

import java.sql.Timestamp;
import java.util.List;

/**
 * Order — model class representing a customer order.
 *
 * <p>Stores the order header data: which user placed it, its current
 * fulfilment status, payment status, total cost, an optional special note,
 * and the timestamp it was created. The list of line items ({@link OrderItem})
 * is attached separately by the DAO after the header is fetched.</p>
 *
 * <p>Valid status values: PENDING, ACCEPTED, PREPARING, READY, COMPLETED, REJECTED</p>
 * <p>Valid payment status values: UNPAID, PAID</p>
 */
public class Order {

    // ── Fields ────────────────────────────────────────────────────────────────

    private int id;                  // auto-generated primary key
    private int userId;              // foreign key — the customer who placed the order
    private String status;           // fulfilment stage: PENDING → ACCEPTED → PREPARING → READY → COMPLETED / REJECTED
    private String paymentStatus;    // payment state: UNPAID or PAID
    private double totalPrice;       // sum of all line item subtotals
    private String specialNote;      // optional customer instructions (e.g. "no onions")
    private Timestamp createdAt;     // date and time the order was placed
    private List<OrderItem> items;   // line items — populated by the DAO after the header is loaded

    // Joined field — customer's full name pulled from the users table (admin views only)
    private String customerName;

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Default no-arg constructor — required by the DAO mapper. */
    public Order() {}

    // ── Getters and Setters ───────────────────────────────────────────────────

    /** Returns the database-assigned order ID. */
    public int getId() {
        return id;
    }
    /** Sets the order ID (called by the DAO after reading from the database). */
    public void setId(int id) {
        this.id = id;
    }

    /** Returns the ID of the customer who placed this order. */
    public int getUserId() {
        return userId;
    }
    /** Sets the customer user ID. */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** Returns the current fulfilment status (e.g. "PENDING", "COMPLETED"). */
    public String getStatus() {
        return status;
    }
    /** Updates the fulfilment status. */
    public void setStatus(String status) {
        this.status = status;
    }

    /** Returns the payment status ("UNPAID" or "PAID"). */
    public String getPaymentStatus() {
        return paymentStatus;
    }
    /** Sets the payment status. */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /** Returns the total cost of the order. */
    public double getTotalPrice() {
        return totalPrice;
    }
    /** Sets the total cost of the order. */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /** Returns the customer's optional special instructions, or {@code null} if none. */
    public String getSpecialNote() {
        return specialNote;
    }
    /** Sets the special note. */
    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    /** Returns the timestamp when the order was created. */
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    /** Sets the creation timestamp (populated from the database). */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /** Returns the list of line items belonging to this order. */
    public List<OrderItem> getItems() {
        return items;
    }
    /** Attaches the list of line items (called by the DAO after fetching the header). */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    /**
     * Returns the customer's full name.
     * This field is populated via a JOIN with the users table — it is not stored
     * in the orders table itself.
     */
    public String getCustomerName() {
        return customerName;
    }
    /** Sets the customer name (populated from the JOIN result). */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    /**
     * Returns a human-readable order reference token in the format {@code ADG-00042}.
     * The ID is zero-padded to 5 digits for consistent display.
     *
     * @return formatted order token string
     */
    public String getToken() {
        return String.format("ADG-%05d", id);
    }
}
