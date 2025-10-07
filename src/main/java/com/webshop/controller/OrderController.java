package com.webshop.controller;

import com.webshop.model.Order;
import com.webshop.model.User;
import com.webshop.service.OrderService;
import com.webshop.dto.OrderResponseDTO;
import com.webshop.dto.DTOMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderController extends HttpServlet {
    private final OrderService orderService;

    public OrderController() {
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        User user = (User) session.getAttribute("user");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Order> orders;
        if (user.isAdmin() || user.isWarehouseStaff()) {
            orders = orderService.getAllOrders();
        } else {
            orders = orderService.getOrdersByUserId(userId);
        }

        List<OrderResponseDTO> orderDTOs = DTOMapper.toOrderResponseDTOs(orders);
        request.setAttribute("orders", orderDTOs);
        request.getRequestDispatcher("/views/orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String shippingAddress = request.getParameter("shippingAddress");
            boolean success = orderService.createOrderFromCart(userId, shippingAddress);

            if (success) {
                session.setAttribute("message", "Order placed successfully!");
            } else {
                session.setAttribute("error", "Failed to place order. Please check stock availability.");
            }

            response.sendRedirect(request.getContextPath() + "/orders");
        } else if ("update".equals(action)) {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String status = request.getParameter("status");

            User user = (User) session.getAttribute("user");
            if (user.isAdmin() || user.isWarehouseStaff()) {
                orderService.updateOrderStatus(orderId, Order.OrderStatus.valueOf(status));
            }

            response.sendRedirect(request.getContextPath() + "/orders");
        }
    }
}