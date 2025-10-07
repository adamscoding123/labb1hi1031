package com.webshop.dto;

import java.math.BigDecimal;

public class OrderItemResponseDTO {
    private int id;
    private int orderId;
    private int productId;
    private ProductResponseDTO product;
    private int quantity;
    private BigDecimal unitPrice;

    public OrderItemResponseDTO() {}

    public OrderItemResponseDTO(int id, int orderId, int productId, ProductResponseDTO product, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public ProductResponseDTO getProduct() { return product; }
    public void setProduct(ProductResponseDTO product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}