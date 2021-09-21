<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<c:set var="title" value="Cart" scope="page"/>
<%@include file="head.jspf"%>
<body>
<c:set var="currentPage" value="cart" scope="page"/>
<%@include file="header.jspf" %>

<jsp:useBean id="cart" scope="session" type="java.util.Map"/>
<c:forEach items="${cart}" var="item">
    <%@ include file="dish_cart.jspf"%>
</c:forEach>


</body>
</html>
