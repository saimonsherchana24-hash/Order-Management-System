package aptProject.model;

/**
 * OrderItem — model class representing a single line item within an order.
 *
 * <p>Each OrderItem records which menu item was ordered, the quantity, and
 * a price/name snapshot taken at the time of ordering. Snapshots are important
 * because the menu can change after an order is placed — the order history
 * must always reflect what the customer actually paid for.</p>
 */
public class OrderItem {

    // ── Fields ────────────────────────────────────────────────────────────────

    private int id;           // auto-generated primary key of this line item row
    private int orderId;      // foreign key — the parent order this item belongs to
    private int menuItemId;   // foreign key — the menu item that was ordered (0 if unlinked)
    private String itemName;  // snapshot of the item name at the time of ordering
    private double itemPrice; // snapshot of the unit price at the time of ordering
    private int quantity;     // number of units ordered

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Default no-arg constructor — required by the DAO mapper. */
    public OrderItem() {}

    /**
     * Convenience constructor used when building an order from the shopping cart.
     *
     * @param menuItemId the ID of the menu item being ordered
     * @param itemName   the name of the item (snapshot)
     * @param itemPrice  the unit price of the item (snapshot)
     * @param quantity   the number of units ordered
     */
    public OrderItem(int menuItemId, String itemName, double itemPrice, int quantity) {
        this.menuItemId = menuItemId;
        this.itemName   = itemName;
        this.itemPrice  = itemPrice;
        this.quantity   = quantity;
    }

    // ── Getters and Setters ───────────────────────────────────────────────────

    /** Returns the database-assigned ID of this line item. */
    public int getId(){ 
        return id; 
    }
    /** Sets the line item ID (called by the DAO after reading from the database). */
    public void setId(int id) { 
        this.id = id; 
    }

    /** Returns the ID of the parent order. */
    public int getOrderId() { 
        return orderId; 
    }
    /** Sets the parent order ID. */
    public void setOrderId(int orderId) {
        this.orderId = orderId; 
    }

    /** Returns the ID of the referenced menu item (may be 0 if the item was unlinked). */
    public int getMenuItemId() { 
        return menuItemId; 
    }
    /** Sets the menu item ID. */
    public void setMenuItemId(int menuItemId){ 
        this.menuItemId = menuItemId;
    }

    /** Returns the item name as it was at the time of ordering (snapshot). */
    public String getItemName() {
        return itemName; 
    }
    /** Sets the item name snapshot. */
    public void setItemName(String itemName) { 
        this.itemName = itemName; 
    }

    /** Returns the unit price as it was at the time of ordering (snapshot). */
    public double getItemPrice(){ 
        return itemPrice;
    }
    /** Sets the unit price snapshot. */
    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    /** Returns the number of units ordered. */
    public int getQuantity(){ 
        return quantity;
    }
    /** Sets the quantity. */
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    /**
     * Calculates the subtotal for this line item (unit price × quantity).
     *
     * @return the line item subtotal as a double
     */
    public double getSubtotal() {
        return itemPrice * quantity; // price snapshot × quantity
    }
}
