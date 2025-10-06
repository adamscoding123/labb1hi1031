<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f9fafb;
        color: #333;
        margin: 0;
        // padding: 20px;
    }

    h1 {
        text-align: center;
        color: #2c3e50;
    }

    h2 {
        margin-top: 40px;
        color: #34495e;
        border-bottom: 2px solid #2980b9;
        padding-bottom: 5px;
    }

    h3 {
        color: #2980b9;
        margin-top: 20px;
    }

    form {
        background: #fff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 3px 8px rgba(0,0,0,0.1);
        margin-bottom: 30px;
    }

    form div {
        margin-bottom: 15px;
    }

    label {
        display: block;
        margin-bottom: 5px;
        font-weight: 600;
    }

    input[type="text"],
    input[type="number"],
    textarea,
    select {
        width: 100%;
        padding: 10px;
        border-radius: 5px;
        border: 1px solid #ccc;
        box-sizing: border-box;
        transition: 0.3s;
    }

    input[type="text"]:focus,
    input[type="number"]:focus,
    textarea:focus,
    select:focus {
        border-color: #2980b9;
        outline: none;
    }

    button.btn {
        background: #2980b9;
        color: #fff;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        transition: 0.3s;
    }

    button.btn:hover {
        background: #1c5980;
    }

    button.delete-btn {
        background: #e74c3c;
    }

    button.delete-btn:hover {
        background: #c0392b;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        background: #fff;
        border-radius: 10px;
        overflow: hidden;
        box-shadow: 0 3px 8px rgba(0,0,0,0.1);
    }

    th, td {
        padding: 12px 15px;
        text-align: left;
    }

    th {
        background-color: #2980b9;
        color: #fff;
        font-weight: 600;
    }

    tr:nth-child(even) {
        background-color: #f4f6f8;
    }

    tr:hover {
        background-color: #dce6f1;
    }

    .actions form {
        display: inline-block;
        margin-right: 5px;
    }
</style>

<h1>Admin Dashboard</h1>

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
        <label>Category:</label>
        <select name="categoryId" id="categorySelect">
            <c:forEach var="category" items="${categories}">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
            <option value="new">-- Create New Category --</option>
        </select>
        <div id="newCategoryDiv" style="display:none; margin-top: 10px;">
            <label>New Category Name:</label>
            <input type="text" name="newCategoryName" placeholder="Enter new category name">
        </div>
        <script>
            const categorySelect = document.getElementById('categorySelect');
            const newCategoryDiv = document.getElementById('newCategoryDiv');

            categorySelect.addEventListener('change', function() {
                newCategoryDiv.style.display = this.value === 'new' ? 'block' : 'none';
            });
        </script>
    </div>

    <div id="newCategoryDiv" style="display:none; margin-top: 10px;">
        <label>New Category Name:</label>
        <input type="text" name="newCategoryName" placeholder="Enter new category name">
    </div>

    <button type="submit" class="btn">Add Product</button>
</form>

<h3>Existing Products</h3>
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>$${product.price}</td>
                <td>${product.stockQuantity}</td>
                <td class="actions">
                    <form method="post" action="admin">
                        <input type="hidden" name="action" value="deleteProduct">
                        <input type="hidden" name="productId" value="${product.id}">
                        <button type="submit" class="btn delete-btn">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<h2>User Management</h2>
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td class="actions">
                    <form method="post" action="admin">
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


<script>
    const categorySelect = document.getElementById('categorySelect');
    const newCategoryDiv = document.getElementById('newCategoryDiv');

    categorySelect.addEventListener('change', function() {
        if (this.value === 'new') {
            newCategoryDiv.style.display = 'block';
        } else {
            newCategoryDiv.style.display = 'none';
        }
    });
</script>


<%@ include file="footer.jsp" %>

