package com.webshop.service;

import com.webshop.dao.UserDAO;
import com.webshop.model.User;
import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User authenticate(String username, String password) {
        return userDAO.authenticate(username, password);
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean createUser(User user) {
        return userDAO.createUser(user);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int id) {
        return userDAO.deleteUser(id);
    }

    public boolean isAdmin(User user) {
        return user != null && user.isAdmin();
    }

    public boolean isWarehouseStaff(User user) {
        return user != null && user.isWarehouseStaff();
    }

    public boolean isCustomer(User user) {
        return user != null && user.isCustomer();
    }
}