<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WebShop</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
        .navbar { background: #333; color: white; padding: 1rem; }
        .navbar a { color: white; text-decoration: none; margin-right: 1rem; }
        .container { max-width: 1200px; margin: 0 auto; padding: 2rem; }
        .product-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 2rem; }
        .product-card { border: 1px solid #ddd; padding: 1rem; border-radius: 5px; }
        .btn { background: #007bff; color: white; padding: 0.5rem 1rem; border: none; border-radius: 3px; cursor: pointer; }
        .btn:hover { background: #0056b3; }
        .error { color: red; margin: 1rem 0; }
        .success { color: green; margin: 1rem 0; }
    </style>
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products">Products</a>
        <c:if test="${not empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/cart">Cart (${sessionScope.cartCount})"</a>
            <a href="${pageContext.request.contextPath}/orders">Orders</a>
            <c:if test="${sessionScope.user.admin}">
                <a href="${pageContext.request.contextPath}/admin">Admin</a>
            </c:if>
            <span style="float: right;">
                Welcome, ${sessionScope.username} |
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </span>
        </c:if>
        <c:if test="${empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/login" style="float: right;">Login</a>
        </c:if>
    </nav>
    <div class="container">