package com.webshop.dto;

import com.webshop.model.User;

public class UserRequestDTO {
    private String username;
    private String password;
    private String email;
    private User.UserRole role;

    public UserRequestDTO() {}

    public UserRequestDTO(String username, String password, String email, User.UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public User.UserRole getRole() { return role; }
    public void setRole(User.UserRole role) { this.role = role; }
}