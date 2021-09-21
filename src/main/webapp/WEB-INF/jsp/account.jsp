<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="Account" scope="page"/>
<%@include file="head.jspf"%>
<body>
<c:set var="currentPage" value="account" scope="page"/>
<%@include file="header.jspf" %>

login: ${user.login}<br><br>

Receipts:<br>
<c:forEach var="receipt" items="${receipts}">
    id:${receipt.id}; total:${receipt.total}; status_id:${receipt.statusId}<br>
</c:forEach>

</body>
</html>
