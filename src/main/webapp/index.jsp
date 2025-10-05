<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="views/header.jsp" %>

<h1>Welcome to WebShop</h1>
<p>Your one-stop shop for all your needs!</p>

<c:if test="${not empty sessionScope.user}">
    <p>Welcome back, ${sessionScope.username}! Browse our <a href="products">products</a> or check your <a href="cart">cart</a>.</p>
</c:if>

<c:if test="${empty sessionScope.user}">
    <p>Please <a href="login">login</a> to start shopping.</p>
</c:if>

<%@ include file="views/footer.jsp" %>