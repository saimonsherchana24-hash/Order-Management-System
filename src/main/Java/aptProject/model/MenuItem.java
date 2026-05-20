package aptProject.model;

/**
 * MenuItem — model class representing a single item on the restaurant menu.
 *
 * <p>Stores the item's identity (id, name), its category (food, drinks, dessert),
 * price, a short description, and the relative URL of its image. Instances are
 * loaded from the {@code menu_items} table by {@code MenuItemDAO} and passed to
 * JSP pages for display.</p>
 */
public class MenuItem {

    // ── Fields ────────────────────────────────────────────────────────────────

    private int id;            // auto-generated primary key
    private String name;       // display name of the item (e.g. "Bruschetta")
    private String category;   // grouping: "food", "drinks", or "dessert"
    private double price;      // unit price in the local currency
    private String description; // short text shown on the menu card
    private String imageUrl;   // context-relative path to the item's image file

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Default no-arg constructor — required by the DAO mapper. */
    public MenuItem() {}

    /**
     * Convenience constructor for creating a new menu item (e.g. in tests or admin forms).
     *
     * @param name        the item's display name
     * @param category    the category it belongs to
     * @param price       the unit price
     * @param description a short description shown on the menu
     * @param imageUrl    relative path to the item's image
     */
    public MenuItem(String name, String category, double price, String description, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // ── Getters and Setters ───────────────────────────────────────────────────

    /** Returns the database-assigned item ID. */
    public int getId()  {
        return id; 
    }
    /** Sets the item ID (called by the DAO after reading from the database). */
    public void setId(int id) { 
        this.id = id; 
    }

    /** Returns the item's display name. */
    public String getName() { 
        return name;
    }
    /** Sets the item's display name. */
    public void setName(String name) { 
        this.name = name; 
    }

    /** Returns the category this item belongs to (e.g. "food", "drinks", "dessert"). */
    public String getCategory() { 
        return category; 
    }
    /** Sets the category. */
    public void setCategory(String category)  { 
        this.category = category; 
    }

    /** Returns the unit price of this item. */
    public double getPrice() { 
        return price;
    }
    /** Sets the unit price. */
    public void setPrice(double price) { 
        this.price = price;
    }

    /** Returns the short description shown on the menu card. */
    public String getDescription(){ 
        return description; 
    }
    /** Sets the description. */
    public void setDescription(String description) { 
        this.description = description; 
    }

    /** Returns the context-relative URL of the item's image (e.g. {@code /Resource/menu/item_123.jpg}). */
    public String getImageUrl(){ 
        return imageUrl; 
    }
    /** Sets the image URL. */
    public void setImageUrl(String imageUrl)  { 
        this.imageUrl = imageUrl;
    }
}
