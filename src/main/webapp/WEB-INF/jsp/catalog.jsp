<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<c:set var="title" value="Catalog" scope="page"/>
<%@include file="head.jspf" %>
<body>

<c:set var="currentPage" value="catalog" scope="page"/>
<%@include file="header.jspf" %>
<div class="c_dishes">
    <c:forEach var="dish" items="${dishes}">
        <%@include file="dish.jspf" %>
    </c:forEach>
</div>

</body>
</html>
