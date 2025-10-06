package com.webshop.dao;

import com.webshop.model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

  public List<Category> getAllCategories() {
    List<Category> categories = new ArrayList<>();
    String sql = "SELECT * FROM categories ORDER BY name";

    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        categories.add(category);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return categories;
  }

  public Category createCategory(String name) {
    String sql = "INSERT INTO categories (name) VALUES (?)";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      stmt.setString(1, name);
      int affected = stmt.executeUpdate();
      if (affected == 0)
        return null;

      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          Category category = new Category();
          category.setId(rs.getInt(1));
          category.setName(name);
          return category;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
