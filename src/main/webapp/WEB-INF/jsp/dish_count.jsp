<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="Dish count" scope="page"/>
<%@include file="head.jspf" %>
<body>
<c:set var="currentPage" value="dishCount" scope="page"/>
<%@include file="header.jspf" %>

<table class="table">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>count</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ordersCount" items="${ordersCount}">
        <tr>
            <th>${ordersCount.key}</th>
            <th>${ordersCount.value.key}</th>
            <th>${ordersCount.value.value}</th>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
