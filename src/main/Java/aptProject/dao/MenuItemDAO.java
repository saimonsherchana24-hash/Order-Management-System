package aptProject.dao;

import aptProject.dao.Interface.MenuItemDAOInterface;
import aptProject.model.MenuItem;
import aptProject.utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MenuItemDAO — Data Access Object for the {@code menu_items} table.
 * <p>Provides full CRUD operations for menu items: listing all items,
 * filtering by category, fetching a single item, adding, updating, and
 * deleting. Implements {MenuItemDAOInterface}.</p>
 */
public class MenuItemDAO implements MenuItemDAOInterface {

    // ── Reusable SQL constants ────────────────────────────────────────────────

    /** Fetches all menu items ordered by category then name */
    private static final String SELECT_ALL =
            "SELECT id, name, category, price, description, image_url FROM menu_items ORDER BY category, name";

    /** Fetches menu items filtered by a specific category, ordered by name */
    private static final String SELECT_BY_CATEGORY =
            "SELECT id, name, category, price, description, image_url FROM menu_items WHERE category = ? ORDER BY name";

    /** Fetches a single menu item by its primary key */
    private static final String SELECT_BY_ID =
            "SELECT id, name, category, price, description, image_url FROM menu_items WHERE id = ?";

    // ── CRUD methods ──────────────────────────────────────────────────────────

    /**
     * Retrieves every menu item from the database, ordered by category then name.
     * @return a list of all {MenuItem} objects (may be empty, never {null})
     */
    @Override
    public List<MenuItem> getAllItems() {
        List<MenuItem> items = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            // Iterate every row and convert it to a MenuItem object
            while (rs.next()) {
                items.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Retrieves all menu items that belong to a given category.
     * @param category the category to filter by (e.g. "food", "drinks", "dessert")
     * @return a list of matching { MenuItem} objects (may be empty, never {null})
     */
    @Override
    public List<MenuItem> getItemsByCategory(String category) {
        List<MenuItem> items = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_CATEGORY)) {

            ps.setString(1, category); // bind the category filter
            try (ResultSet rs = ps.executeQuery()) {
                // Collect every matching row as a MenuItem
                while (rs.next()) {
                    items.add(map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Retrieves a single menu item by its primary key.
     * @param id the ID of the menu item to fetch
     * @return the matching {MenuItem}, or {null} if not found
     */
    @Override
    public MenuItem getItemById(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id); // bind the target item ID
            try (ResultSet rs = ps.executeQuery()) {
                // Return the item if found, otherwise fall through to return null
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // item not found
    }

    /**
     * Inserts a new menu item into the database.
     * @param item the {MenuItem} to persist id field is ignored;the database assigns it automatically)
     * @return { true} if exactly one row was inserted; {false} otherwise
     */
    @Override
    public boolean addItem(MenuItem item) {
        // SQL to insert a new menu item with all required fields
        String sql = "INSERT INTO menu_items (name, category, price, description, image_url) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind each field of the MenuItem to the corresponding placeholder
            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getDescription());
            ps.setString(5, item.getImageUrl()); // relative path to the image file

            return ps.executeUpdate() == 1; // 1 row inserted = success
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates all editable fields of an existing menu item.
     * @param item the { MenuItem} with updated values; the {id} field must match an existing row in the database
     * @return {true} if the row was updated; { false} otherwise
     */
    @Override
    public boolean updateItem(MenuItem item) {
        // SQL to update all mutable columns for a specific item ID
        String sql = "UPDATE menu_items SET name=?, category=?, price=?, description=?, image_url=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Bind updated values
            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getDescription());
            ps.setString(5, item.getImageUrl());
            ps.setInt(6, item.getId()); // WHERE clause — target the correct row

            return ps.executeUpdate() == 1; // 1 row updated = success
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a menu item from the database by its primary key.
     * @param id the ID of the menu item to remove
     * @return {true} if the row was deleted; { false} otherwise
     */
    @Override
    public boolean deleteItem(int id) {
        // SQL to remove the menu item with the given ID
        String sql = "DELETE FROM menu_items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id); // bind the target item ID
            return ps.executeUpdate() == 1; // 1 row deleted = success
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Maps the current row of a {ResultSet} to a {MenuItem} object.
     * @param rs an open ResultSet positioned on a valid row
     * @throws SQLException if any column cannot be read
     */
    private MenuItem map(ResultSet rs) throws SQLException {
        MenuItem item = new MenuItem();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setCategory(rs.getString("category"));
        item.setPrice(rs.getDouble("price"));
        item.setDescription(rs.getString("description"));
        item.setImageUrl(rs.getString("image_url")); // relative path used in <img> tags
        return item;
    }
}
