<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<c:set var="title" value="Catalog" scope="page"/>
<%@include file="head.jspf" %>
<body class="ls_body">

<c:set var="currentPage" value="catalog" scope="page"/>
<%@include file="header.jspf" %>

<c:forEach var="dish" items="${dishes}">
    <%@include file="dish.jspf"%><br>
</c:forEach>

</body>
</html>
