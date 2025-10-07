package com.webshop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartItemResponseDTO {
    private int id;
    private int userId;
    private ProductResponseDTO product;
    private int quantity;
    private LocalDateTime addedAt;

    public CartItemResponseDTO() {}

    public CartItemResponseDTO(int id, int userId, ProductResponseDTO product, int quantity, LocalDateTime addedAt) {
        this.id = id;
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
        this.addedAt = addedAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public ProductResponseDTO getProduct() { return product; }
    public void setProduct(ProductResponseDTO product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
}