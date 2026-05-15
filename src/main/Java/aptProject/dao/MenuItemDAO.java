package aptProject.dao;

import aptProject.dao.Interface.MenuItemDAOInterface;
import aptProject.model.MenuItem;
import aptProject.utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO implements MenuItemDAOInterface {

    private static final String SELECT_ALL =
            "SELECT id, name, category, price, description, image_url FROM menu_items ORDER BY category, name";

    private static final String SELECT_BY_CATEGORY =
            "SELECT id, name, category, price, description, image_url FROM menu_items WHERE category = ? ORDER BY name";

    private static final String SELECT_BY_ID =
            "SELECT id, name, category, price, description, image_url FROM menu_items WHERE id = ?";

    @Override
    public List<MenuItem> getAllItems() {
        List<MenuItem> items = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<MenuItem> getItemsByCategory(String category) {
        List<MenuItem> items = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_CATEGORY)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(map(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public MenuItem getItemById(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addItem(MenuItem item) {
        String sql = "INSERT INTO menu_items (name, category, price, description, image_url) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getDescription());
            ps.setString(5, item.getImageUrl());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateItem(MenuItem item) {
        String sql = "UPDATE menu_items SET name=?, category=?, price=?, description=?, image_url=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getDescription());
            ps.setString(5, item.getImageUrl());
            ps.setInt(6, item.getId());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteItem(int id) {
        String sql = "DELETE FROM menu_items WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private MenuItem map(ResultSet rs) throws SQLException {
        MenuItem item = new MenuItem();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setCategory(rs.getString("category"));
        item.setPrice(rs.getDouble("price"));
        item.setDescription(rs.getString("description"));
        item.setImageUrl(rs.getString("image_url"));
        return item;
    }
}
