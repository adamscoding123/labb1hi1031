package com.webshop.dao;

import com.webshop.model.Order;
import com.webshop.model.OrderItem;
import com.webshop.model.Product;
import com.webshop.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public boolean createOrder(Order order, List<OrderItem> orderItems) {
        String orderSql = "INSERT INTO orders (user_id, total_amount, shipping_address) VALUES (?, ?, ?)";
        String orderItemSql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        String updateStockSql = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Insert order
            int orderId;
            try (PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getUserId());
                orderStmt.setBigDecimal(2, order.getTotalAmount());
                orderStmt.setString(3, order.getShippingAddress());
                orderStmt.executeUpdate();

                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to get order ID");
                    }
                }
            }

            // Insert order items and update stock
            try (PreparedStatement itemStmt = conn.prepareStatement(orderItemSql);
                 PreparedStatement stockStmt = conn.prepareStatement(updateStockSql)) {

                for (OrderItem item : orderItems) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, item.getProductId());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setBigDecimal(4, item.getUnitPrice());
                    itemStmt.addBatch();

                    stockStmt.setInt(1, item.getQuantity());
                    stockStmt.setInt(2, item.getProductId());
                    stockStmt.addBatch();
                }

                itemStmt.executeBatch();
                stockStmt.executeBatch();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, u.username, u.email FROM orders o JOIN users u ON o.user_id = u.id WHERE o.user_id = ? ORDER BY o.order_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSetToOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, u.username, u.email FROM orders o JOIN users u ON o.user_id = u.id ORDER BY o.order_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT o.*, u.username, u.email FROM orders o JOIN users u ON o.user_id = u.id WHERE o.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = mapResultSetToOrder(rs);
                    order.setOrderItems(getOrderItemsByOrderId(orderId));
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateOrderStatus(int orderId, Order.OrderStatus status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            stmt.setInt(2, orderId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT oi.*, p.name, p.description, p.image_url FROM order_items oi JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orderItems.add(mapResultSetToOrderItem(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setStatus(Order.OrderStatus.valueOf(rs.getString("status")));
        order.setShippingAddress(rs.getString("shipping_address"));

        // Create user object
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        order.setUser(user);

        return order;
    }

    private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getInt("id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setUnitPrice(rs.getBigDecimal("unit_price"));

        // Create product object
        Product product = new Product();
        product.setId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setImageUrl(rs.getString("image_url"));
        orderItem.setProduct(product);

        return orderItem;
    }
}