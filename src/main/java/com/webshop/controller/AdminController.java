package com.webshop.controller;

import com.webshop.model.Product;
import com.webshop.model.User;
import com.webshop.service.ProductService;
import com.webshop.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    private final ProductService productService;
    private final UserService userService;

    public AdminController() {
        this.productService = new ProductService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Product> products = productService.getAllProducts();
        List<User> users = userService.getAllUsers();

        request.setAttribute("products", products);
        request.setAttribute("users", users);
        request.getRequestDispatcher("/views/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if ("addProduct".equals(action)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStockQuantity(stockQuantity);
            product.setCategoryId(categoryId);

            productService.createProduct(product);
        } else if ("updateProduct".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            Product product = productService.getProductById(productId);
            if (product != null) {
                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);
                product.setStockQuantity(stockQuantity);
                product.setCategoryId(categoryId);
                productService.updateProduct(product);
            }
        } else if ("deleteProduct".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            productService.deleteProduct(productId);
        } else if ("updateUser".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String role = request.getParameter("role");

            User userToUpdate = userService.getUserById(userId);
            if (userToUpdate != null) {
                userToUpdate.setRole(User.UserRole.valueOf(role));
                userService.updateUser(userToUpdate);
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin");
    }
}