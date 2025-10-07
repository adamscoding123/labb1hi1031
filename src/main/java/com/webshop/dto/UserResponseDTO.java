package com.webshop.dto;

import com.webshop.model.User;
import java.time.LocalDateTime;

public class UserResponseDTO {
    private int id;
    private String username;
    private String email;
    private User.UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponseDTO() {}

    public UserResponseDTO(int id, String username, String email, User.UserRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public User.UserRole getRole() { return role; }
    public void setRole(User.UserRole role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}