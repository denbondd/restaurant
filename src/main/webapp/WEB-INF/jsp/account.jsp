<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="Account" scope="page"/>
<%@include file="head.jspf" %>
<body>
<c:set var="currentPage" value="account" scope="page"/>
<%@include file="header.jspf" %>

<jsp:useBean id="user" scope="session" type="com.denbondd.restaurant.db.entity.User"/>

<p class="a_login">${user.login}</p>
<a href="${pageContext.request.contextPath}/account/change-password" class="a_changepass">Change password</a>
<a href="${pageContext.request.contextPath}/account?signout=" class="a_changepass">Sign out</a>

<div class="a_rec_container">
    <p class="a_rec_title">Your receipts:</p>

    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Status</th>
            <th scope="col">Date of order</th>
            <th scope="col">Total</th>
            <th scope="col">Products</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="receipt" items="${receipts}">
            <tr>
                <th scope="col">${receipt.id}</th>
                <th scope="col">${receipt.status.value}</th>
                <th scope="col">${receipt.createDate}</th>
                <th scope="col">${receipt.total}</th>
                <th scope="col">
                    <c:forEach var="dish" items="${receipt.dishes}">
                        ${dish.name}: ${dish.price}&#8372; * ${dish.count}<br>
                    </c:forEach>
                </th>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

</body>
</html>
