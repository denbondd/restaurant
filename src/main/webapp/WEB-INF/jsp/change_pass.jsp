<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="title" value="Change password" scope="page"/>
<%@include file="head.jspf" %>
<body>
<c:set var="currentPage" value="account" scope="page"/>
<%@include file="header.jspf" %>

<div class="ls_container shadow">
    <p class="ls_title">Change password</p>
    <form class="ls_form" action="${pageContext.request.contextPath}/account/change-password" method="post">
        <input type="password" name="old-pass" class="ls_et form-control" placeholder="Old password" data-toggle="password">
        <input type="password" name="new-pass" class="ls_et form-control" placeholder="New password" data-toggle="password">
        <input type="submit" class="ls_btn btn btn-primary" value="Change">
    </form>
    <c:if test="${param.err ne null}">
        <p class="ls_err">You entered wrong old password</p>
    </c:if>
</div>

</body>
</html>
