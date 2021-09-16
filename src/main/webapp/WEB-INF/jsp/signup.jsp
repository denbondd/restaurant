<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<c:set var="title" value="SignUp" scope="page"/>
<%@include file="head.jspf" %>
<body>

<c:set var="currentPage" value="account" scope="page"/>
<%@include file="header.jspf" %>

<div class="ls_container shadow">
    <p class="ls_title">Sign Up</p>
    <form class="ls_form" action="${pageContext.request.contextPath}/account/signup" method="post">
        <input name="login" class="ls_et form-control" placeholder="Login">
        <input type="password" name="password" class="ls_et form-control" placeholder="Password">
        <input type="submit" class="ls_btn btn btn-primary" value="SignUp">
    </form>
    <c:if test="${param.err ne null}">
        <p class="ls_err">This login already exists</p>
    </c:if>
    <p class="ls_text">Already have an account? <a href="${pageContext.request.contextPath}/account/login">Log In</a>
    </p>
</div>

</body>
</html>
