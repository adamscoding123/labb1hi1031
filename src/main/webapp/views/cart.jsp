<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<h1>Shopping Cart</h1>

<c:if test="${not empty sessionScope.error}">
    <div class="error">${sessionScope.error}</div>
    <c:remove var="error" scope="session"/>
</c:if>

<c:if test="${empty cartItems}">
    <p>Your cart is empty.</p>
</c:if>

<c:if test="${not empty cartItems}">
    <table style="width: 100%; border-collapse: collapse;">
        <thead>
            <tr>
                <th style="border: 1px solid #ddd; padding: 8px;">Product</th>
                <th style="border: 1px solid #ddd; padding: 8px;">Price</th>
                <th style="border: 1px solid #ddd; padding: 8px;">Quantity</th>
                <th style="border: 1px solid #ddd; padding: 8px;">Total</th>
                <th style="border: 1px solid #ddd; padding: 8px;">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${cartItems}">
                <tr>
                    <td style="border: 1px solid #ddd; padding: 8px;">${item.product.name}</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">$${item.product.price}</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">
                        <form method="post" action="cart" style="display: inline;">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="productId" value="${item.product.id}">
                            <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.product.stockQuantity}">
                            <button type="submit" class="btn">Update</button>
                        </form>
                    </td>
                    <td style="border: 1px solid #ddd; padding: 8px;">$${item.totalPrice}</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">
                        <form method="post" action="cart" style="display: inline;">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="productId" value="${item.product.id}">
                            <button type="submit" class="btn" style="background: #dc3545;">Remove</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div style="margin-top: 2rem;">
        <h3>Total: $${cartTotal}</h3>

        <form method="post" action="orders">
            <input type="hidden" name="action" value="create">
            <div>
                <label for="shippingAddress">Shipping Address:</label>
                <textarea id="shippingAddress" name="shippingAddress" required style="width: 100%; height: 100px;"></textarea>
            </div>
            <button type="submit" class="btn" style="background: #28a745;">Place Order</button>
        </form>
    </div>
</c:if>

<%@ include file="footer.jsp" %>