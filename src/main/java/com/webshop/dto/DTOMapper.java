package com.webshop.dto;

import com.webshop.model.*;
import java.util.List;
import java.util.stream.Collectors;

public class DTOMapper {

    // Product mappings
    public static ProductResponseDTO toProductResponseDTO(Product product) {
        if (product == null) return null;

        return new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getCategoryId(),
            product.getCategoryName(),
            product.getImageUrl(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }

    public static Product toProduct(ProductRequestDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategoryId(dto.getCategoryId());
        product.setImageUrl(dto.getImageUrl());
        return product;
    }

    // User mappings
    public static UserResponseDTO toUserResponseDTO(User user) {
        if (user == null) return null;

        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    public static User toUser(UserRequestDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }

    // Category mappings
    public static CategoryResponseDTO toCategoryResponseDTO(Category category) {
        if (category == null) return null;

        return new CategoryResponseDTO(
            category.getId(),
            category.getName(),
            category.getDescription(),
            category.getCreatedAt()
        );
    }

    public static Category toCategory(CategoryRequestDTO dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    // Order mappings
    public static OrderResponseDTO toOrderResponseDTO(Order order) {
        if (order == null) return null;

        List<OrderItemResponseDTO> orderItemDTOs = order.getOrderItems() != null ?
            order.getOrderItems().stream()
                .map(DTOMapper::toOrderItemResponseDTO)
                .collect(Collectors.toList()) : null;

        return new OrderResponseDTO(
            order.getId(),
            order.getUserId(),
            toUserResponseDTO(order.getUser()),
            order.getOrderDate(),
            order.getTotalAmount(),
            order.getStatus(),
            order.getShippingAddress(),
            orderItemDTOs
        );
    }

    public static Order toOrder(OrderRequestDTO dto) {
        if (dto == null) return null;

        Order order = new Order(
            dto.getUserId(),
            dto.getTotalAmount(),
            dto.getShippingAddress()
        );
        return order;
    }

    // OrderItem mappings
    public static OrderItemResponseDTO toOrderItemResponseDTO(OrderItem orderItem) {
        if (orderItem == null) return null;

        return new OrderItemResponseDTO(
            orderItem.getId(),
            orderItem.getOrderId(),
            orderItem.getProductId(),
            toProductResponseDTO(orderItem.getProduct()),
            orderItem.getQuantity(),
            orderItem.getUnitPrice()
        );
    }

    public static OrderItem toOrderItem(OrderItemRequestDTO dto) {
        if (dto == null) return null;

        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(dto.getProductId());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setUnitPrice(dto.getUnitPrice());
        return orderItem;
    }

    // CartItem mappings
    public static CartItemResponseDTO toCartItemResponseDTO(CartItem cartItem) {
        if (cartItem == null) return null;

        return new CartItemResponseDTO(
            cartItem.getId(),
            cartItem.getUserId(),
            toProductResponseDTO(cartItem.getProduct()),
            cartItem.getQuantity(),
            cartItem.getAddedAt()
        );
    }

    // Collection mappings
    public static List<ProductResponseDTO> toProductResponseDTOs(List<Product> products) {
        if (products == null) return null;
        return products.stream()
            .map(DTOMapper::toProductResponseDTO)
            .collect(Collectors.toList());
    }

    public static List<UserResponseDTO> toUserResponseDTOs(List<User> users) {
        if (users == null) return null;
        return users.stream()
            .map(DTOMapper::toUserResponseDTO)
            .collect(Collectors.toList());
    }

    public static List<CategoryResponseDTO> toCategoryResponseDTOs(List<Category> categories) {
        if (categories == null) return null;
        return categories.stream()
            .map(DTOMapper::toCategoryResponseDTO)
            .collect(Collectors.toList());
    }

    public static List<OrderResponseDTO> toOrderResponseDTOs(List<Order> orders) {
        if (orders == null) return null;
        return orders.stream()
            .map(DTOMapper::toOrderResponseDTO)
            .collect(Collectors.toList());
    }

    public static List<CartItemResponseDTO> toCartItemResponseDTOs(List<CartItem> cartItems) {
        if (cartItems == null) return null;
        return cartItems.stream()
            .map(DTOMapper::toCartItemResponseDTO)
            .collect(Collectors.toList());
    }
}