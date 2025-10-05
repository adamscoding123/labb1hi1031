package com.webshop.dao;

import com.webshop.model.CartItem;
import com.webshop.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public List<CartItem> getCartItemsByUserId(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT ci.*, p.name, p.description, p.price, p.stock_quantity, p.category_id, p.image_url " +
                    "FROM cart_items ci " +
                    "JOIN products p ON ci.product_id = p.id " +
                    "WHERE ci.user_id = ? ORDER BY ci.added_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cartItems.add(mapResultSetToCartItem(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public boolean addToCart(int userId, int productId, int quantity) {
        String checkSql = "SELECT id, quantity FROM cart_items WHERE user_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateSql = "UPDATE cart_items SET quantity = quantity + ? WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, productId);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        // Item exists, update quantity
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, quantity);
                            updateStmt.setInt(2, userId);
                            updateStmt.setInt(3, productId);
                            updateStmt.executeUpdate();
                        }
                    } else {
                        // New item, insert
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setInt(1, userId);
                            insertStmt.setInt(2, productId);
                            insertStmt.setInt(3, quantity);
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCartItemQuantity(int userId, int productId, int quantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeFromCart(int userId, int productId) {
        String sql = "DELETE FROM cart_items WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, productId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart_items WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getCartItemCount(int userId) {
        String sql = "SELECT COUNT(*) as count FROM cart_items WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private CartItem mapResultSetToCartItem(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setId(rs.getInt("id"));
        cartItem.setUserId(rs.getInt("user_id"));
        cartItem.setQuantity(rs.getInt("quantity"));
        cartItem.setAddedAt(rs.getTimestamp("added_at").toLocalDateTime());

        // Create product object
        Product product = new Product();
        product.setId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setImageUrl(rs.getString("image_url"));

        cartItem.setProduct(product);

        return cartItem;
    }
}