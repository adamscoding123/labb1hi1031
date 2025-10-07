package com.webshop.dto;

import com.webshop.model.Order;
import java.math.BigDecimal;
import java.util.List;

public class OrderRequestDTO {
    private int userId;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private List<OrderItemRequestDTO> orderItems;

    public OrderRequestDTO() {}

    public OrderRequestDTO(int userId, BigDecimal totalAmount, String shippingAddress, List<OrderItemRequestDTO> orderItems) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public List<OrderItemRequestDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemRequestDTO> orderItems) { this.orderItems = orderItems; }
}