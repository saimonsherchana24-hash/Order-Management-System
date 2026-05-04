package aptProject.model;

public class OrderItem {
    private int id;
    private int orderId;
    private int menuItemId;
    private String itemName;    // snapshot at order time
    private double itemPrice;   // snapshot at order time
    private int quantity;

    public OrderItem() {}

    public OrderItem(int menuItemId, String itemName, double itemPrice, int quantity) {
        this.menuItemId = menuItemId;
        this.itemName   = itemName;
        this.itemPrice  = itemPrice;
        this.quantity   = quantity;
    }

    public int getId()                      { return id; }
    public void setId(int id)               { this.id = id; }

    public int getOrderId()                 { return orderId; }
    public void setOrderId(int orderId)     { this.orderId = orderId; }

    public int getMenuItemId()                      { return menuItemId; }
    public void setMenuItemId(int menuItemId)        { this.menuItemId = menuItemId; }

    public String getItemName()                     { return itemName; }
    public void setItemName(String itemName)        { this.itemName = itemName; }

    public double getItemPrice()                    { return itemPrice; }
    public void setItemPrice(double itemPrice)      { this.itemPrice = itemPrice; }

    public int getQuantity()                        { return quantity; }
    public void setQuantity(int quantity)           { this.quantity = quantity; }

    public double getSubtotal() {
        return itemPrice * quantity;
    }
}
