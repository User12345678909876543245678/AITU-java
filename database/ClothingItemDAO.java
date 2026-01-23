package database;

import model.ClothingItem;

import java.sql.*;
import java.util.ArrayList;

public class ClothingItemDAO {

    public void insert(ClothingItem item) {
        String sql = "INSERT INTO clothing_item (name, size, price, brand) VALUES (?, ?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getSize());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getBrand());

            ps.executeUpdate();
            System.out.println("Item inserted into DB.");

        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
        }
    }

    public ArrayList<ClothingItem> getAll() {
        ArrayList<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT item_id, name, size, price, brand FROM clothing_item ORDER BY item_id";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClothingItem item = new ClothingItem(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getString("size"),
                        rs.getDouble("price"),
                        rs.getString("brand")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Select failed: " + e.getMessage());
        }
        return items;
    }
}
