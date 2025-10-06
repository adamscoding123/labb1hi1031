<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<h1>Login</h1>

<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>

<form method="post" action="login">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <button type="submit" class="btn">Login</button>
</form>

<p>Demo accounts:</p>
<ul>
    <li>Admin: username="admin", password="admin"</li>
    <li>Warehouse: username="warehouse", password="warehouse"</li>
    <li>Customer: username="customer", password="customer"</li>
</ul>

<%@ include file="footer.jsp" %>
