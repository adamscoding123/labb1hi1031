package com.webshop.controller;

import com.webshop.service.CartService;
import com.webshop.dto.CartItemResponseDTO;
import com.webshop.dto.DTOMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private final CartService cartService;

    public CartController() {
        this.cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<CartItemResponseDTO> cartItemDTOs = DTOMapper.toCartItemResponseDTOs(cartService.getCartItems(userId));
        request.setAttribute("cartItems", cartItemDTOs);
        request.setAttribute("cartTotal", cartService.calculateCartTotal(userId));
        request.getRequestDispatcher("/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            boolean success = cartService.addToCart(userId, productId, quantity);
            if (!success) {
                session.setAttribute("error", "Not enough stock available");
            }
        } else if ("update".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            boolean success = cartService.updateCartItemQuantity(userId, productId, quantity);
            if (!success) {
                session.setAttribute("error", "Not enough stock available");
            }
        } else if ("remove".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            cartService.removeFromCart(userId, productId);
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
