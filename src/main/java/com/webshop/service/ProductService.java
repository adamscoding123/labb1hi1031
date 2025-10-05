package com.webshop.service;

import com.webshop.dao.ProductDAO;
import com.webshop.model.Product;
import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }

    public List<Product> getProductsByCategory(int categoryId) {
        return productDAO.getProductsByCategory(categoryId);
    }

    public boolean createProduct(Product product) {
        return productDAO.createProduct(product);
    }

    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    public boolean updateStockQuantity(int productId, int newQuantity) {
        return productDAO.updateStockQuantity(productId, newQuantity);
    }

    public boolean deleteProduct(int id) {
        return productDAO.deleteProduct(id);
    }

    public boolean hasSufficientStock(int productId, int requestedQuantity) {
        Product product = productDAO.getProductById(productId);
        return product != null && product.hasSufficientStock(requestedQuantity);
    }

    public boolean isInStock(int productId) {
        Product product = productDAO.getProductById(productId);
        return product != null && product.isInStock();
    }
}