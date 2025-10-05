package com.webshop.service;

import com.webshop.dao.OrderDAO;
import com.webshop.model.CartItem;
import com.webshop.model.Order;
import com.webshop.model.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderDAO orderDAO;
    private final CartService cartService;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.cartService = new CartService();
    }

    public boolean createOrderFromCart(int userId, String shippingAddress) {
        // Validate cart stock before creating order
        if (!cartService.validateCartStock(userId)) {
            return false;
        }

        List<CartItem> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            return false;
        }

        // Calculate total amount
        BigDecimal totalAmount = cartService.calculateCartTotal(userId);

        // Create order
        Order order = new Order(userId, totalAmount, shippingAddress);

        // Convert cart items to order items
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(
                        0, // orderId will be set later
                        cartItem.getProduct().getId(),
                        cartItem.getQuantity(),
                        cartItem.getProduct().getPrice()
                ))
                .collect(Collectors.toList());

        // Create order in transaction
        boolean success = orderDAO.createOrder(order, orderItems);

        // Clear cart if order was successful
        if (success) {
            cartService.clearCart(userId);
        }

        return success;
    }

    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public Order getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    public boolean updateOrderStatus(int orderId, Order.OrderStatus status) {
        return orderDAO.updateOrderStatus(orderId, status);
    }

    public boolean canOrderBeShipped(Order order) {
        return order != null && order.canBeShipped();
    }

    public boolean canOrderBeCancelled(Order order) {
        return order != null && order.canBeCancelled();
    }
}