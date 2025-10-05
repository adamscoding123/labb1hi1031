<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<h1>Orders</h1>

<c:if test="${not empty sessionScope.message}">
    <div class="success">${sessionScope.message}</div>
    <c:remove var="message" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.error}">
    <div class="error">${sessionScope.error}</div>
    <c:remove var="error" scope="session"/>
</c:if>

<c:if test="${empty orders}">
    <p>No orders found.</p>
</c:if>

<c:if test="${not empty orders}">
    <table style="width: 100%; border-collapse: collapse;">
        <thead>
            <tr>
                <th style="border: 1px solid #ddd; padding: 8px;">Order ID</th>
                <c:if test="${sessionScope.user.admin or sessionScope.user.warehouseStaff}">
                    <th style="border: 1px solid #ddd; padding: 8px;">Customer</th>
                </c:if>
                <th style="border: 1px solid #ddd; padding: 8px;">Date</th>
                <th style="border: 1px solid #ddd; padding: 8px;">Total</th>
                <th style="border: 1px solid #ddd; padding: 8px;">Status</th>
                <th style="border: 1px solid #ddd; padding: 8px;">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td style="border: 1px solid #ddd; padding: 8px;">#${order.id}</td>
                    <c:if test="${sessionScope.user.admin or sessionScope.user.warehouseStaff}">
                        <td style="border: 1px solid #ddd; padding: 8px;">${order.user.username}</td>
                    </c:if>
                    <td style="border: 1px solid #ddd; padding: 8px;">${order.orderDate}</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">$${order.totalAmount}</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">${order.status}</td>
                    <td style="border: 1px solid #ddd; padding: 8px;">
                        <c:if test="${sessionScope.user.admin or sessionScope.user.warehouseStaff}">
                            <form method="post" action="orders" style="display: inline;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <select name="status">
                                    <option value="PENDING" ${order.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                                    <option value="CONFIRMED" ${order.status == 'CONFIRMED' ? 'selected' : ''}>Confirmed</option>
                                    <option value="SHIPPED" ${order.status == 'SHIPPED' ? 'selected' : ''}>Shipped</option>
                                    <option value="DELIVERED" ${order.status == 'DELIVERED' ? 'selected' : ''}>Delivered</option>
                                    <option value="CANCELLED" ${order.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                                </select>
                                <button type="submit" class="btn">Update</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<%@ include file="footer.jsp" %>