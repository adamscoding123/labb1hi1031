package com.webshop.service;

import com.webshop.dao.CategoryDAO;
import com.webshop.model.Category;
import java.util.List;

public class CategoryService {
  private final CategoryDAO categoryDAO;

  public CategoryService() {
    this.categoryDAO = new CategoryDAO();
  }

  public List<Category> getAllCategories() {
    return categoryDAO.getAllCategories();
  }

  public Category createCategory(String name) {
    return categoryDAO.createCategory(name);
  }
}
