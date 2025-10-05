<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<h1>Admin Panel</h1>

<h2>Products Management</h2>

<h3>Add New Product</h3>
<form method="post" action="admin">
    <input type="hidden" name="action" value="addProduct">
    <div>
        <label>Name:</label>
        <input type="text" name="name" required>
    </div>
    <div>
        <label>Description:</label>
        <textarea name="description" required></textarea>
    </div>
    <div>
        <label>Price:</label>
        <input type="number" name="price" step="0.01" required>
    </div>
    <div>
        <label>Stock Quantity:</label>
        <input type="number" name="stockQuantity" required>
    </div>
    <div>
        <label>Category ID:</label>
        <input type="number" name="categoryId" value="1" required>
    </div>
    <button type="submit" class="btn">Add Product</button>
</form>

<h3>Existing Products</h3>
<table style="width: 100%; border-collapse: collapse;">
    <thead>
        <tr>
            <th style="border: 1px solid #ddd; padding: 8px;">ID</th>
            <th style="border: 1px solid #ddd; padding: 8px;">Name</th>
            <th style="border: 1px solid #ddd; padding: 8px;">Price</th>
            <th style="border: 1px solid #ddd; padding: 8px;">Stock</th>
            <th style="border: 1px solid #ddd; padding: 8px;">Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td style="border: 1px solid #ddd; padding: 8px;">${product.id}</td>
                <td style="border: 1px solid #ddd; padding: 8px;">${product.name}</td>
                <td style="border: 1px solid #ddd; padding: 8px;">$${product.price}</td>
                <td style="border: 1px solid #ddd; padding: 8px;">${product.stockQuantity}</td>
                <td style="border: 1px solid #ddd; padding: 8px;">
                    <form method="post" action="admin" style="display: inline;">
                        <input type="hidden" name="action" value="deleteProduct">
                        <input type="hidden" name="productId" value="${product.id}">
                        <button type="submit" class="btn" style="background: #dc3545;">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<h2>User Management</h2>
<table style="width: 100%; border-collapse: collapse;">
    <thead>
        <tr>
            <th style="border: 1px solid #ddd; padding: 8px;">ID</th>
            <th style="border: 1px solid #ddd; padding: 8px;">Username</th>
            <th style="border: 1px solid #ddd; padding: 8px;">Email</th>
            <th style="border: 1px solid #ddd; padding: 8px;">Role</th>
            <th style="border: 1px solid #ddd; padding: 8px;">Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td style="border: 1px solid #ddd; padding: 8px;">${user.id}</td>
                <td style="border: 1px solid #ddd; padding: 8px;">${user.username}</td>
                <td style="border: 1px solid #ddd; padding: 8px;">${user.email}</td>
                <td style="border: 1px solid #ddd; padding: 8px;">${user.role}</td>
                <td style="border: 1px solid #ddd; padding: 8px;">
                    <form method="post" action="admin" style="display: inline;">
                        <input type="hidden" name="action" value="updateUser">
                        <input type="hidden" name="userId" value="${user.id}">
                        <select name="role">
                            <option value="CUSTOMER" ${user.role == 'CUSTOMER' ? 'selected' : ''}>Customer</option>
                            <option value="WAREHOUSE_STAFF" ${user.role == 'WAREHOUSE_STAFF' ? 'selected' : ''}>Warehouse Staff</option>
                            <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                        </select>
                        <button type="submit" class="btn">Update Role</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@ include file="footer.jsp" %>