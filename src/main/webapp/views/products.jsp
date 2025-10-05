<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<h1>Products</h1>

<c:if test="${not empty sessionScope.message}">
    <div class="success">${sessionScope.message}</div>
    <c:remove var="message" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.error}">
    <div class="error">${sessionScope.error}</div>
    <c:remove var="error" scope="session"/>
</c:if>

<div class="product-grid">
    <c:forEach var="product" items="${products}">
        <div class="product-card">
            <h3>${product.name}</h3>
            <p>${product.description}</p>
            <p><strong>Price:</strong> $${product.price}</p>
            <p><strong>Stock:</strong> ${product.stockQuantity}</p>

            <c:if test="${not empty sessionScope.user}">
                <form method="post" action="cart">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="number" name="quantity" value="1" min="1" max="${product.stockQuantity}">
                    <button type="submit" class="btn" ${product.stockQuantity == 0 ? 'disabled' : ''}>
                        ${product.stockQuantity == 0 ? 'Out of Stock' : 'Add to Cart'}
                    </button>
                </form>
            </c:if>
        </div>
    </c:forEach>
</div>

<%@ include file="footer.jsp" %>