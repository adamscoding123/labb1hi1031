package com.webshop.service;

import com.webshop.dao.CartDAO;
import com.webshop.model.CartItem;
import java.math.BigDecimal;
import java.util.List;

public class CartService {
    private final CartDAO cartDAO;
    private final ProductService productService;

    public CartService() {
        this.cartDAO = new CartDAO();
        this.productService = new ProductService();
    }

    public List<CartItem> getCartItems(int userId) {
        return cartDAO.getCartItemsByUserId(userId);
    }

    public boolean addToCart(int userId, int productId, int quantity) {
        if (!productService.hasSufficientStock(productId, quantity)) {
            return false;
        }
        return cartDAO.addToCart(userId, productId, quantity);
    }

    public boolean updateCartItemQuantity(int userId, int productId, int quantity) {
        if (quantity <= 0) {
            return removeFromCart(userId, productId);
        }

        if (!productService.hasSufficientStock(productId, quantity)) {
            return false;
        }

        return cartDAO.updateCartItemQuantity(userId, productId, quantity);
    }

    public boolean removeFromCart(int userId, int productId) {
        return cartDAO.removeFromCart(userId, productId);
    }

    public boolean clearCart(int userId) {
        return cartDAO.clearCart(userId);
    }

    public int getCartItemCount(int userId) {
        return cartDAO.getCartItemCount(userId);
    }

    public BigDecimal calculateCartTotal(int userId) {
        List<CartItem> cartItems = getCartItems(userId);
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean validateCartStock(int userId) {
        List<CartItem> cartItems = getCartItems(userId);
        for (CartItem item : cartItems) {
            if (!productService.hasSufficientStock(item.getProduct().getId(), item.getQuantity())) {
                return false;
            }
        }
        return true;
    }
}