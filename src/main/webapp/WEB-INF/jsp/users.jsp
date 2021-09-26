<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<c:set var="title" value="Users" scope="page"/>
<%@include file="head.jspf" %>
<body>
<c:set var="currentPage" value="users" scope="page"/>
<%@include file="header.jspf" %>

<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">id</th>
        <th scope="col">login</th>
        <th scope="col">role</th>
        <th scope="col">createDate</th>
        <th scope="col"></th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="varUser" items="${users}">
        <tr>
            <th scope="col">${varUser.id}</th>
            <th scope="col">${varUser.login}</th>
            <th scope="col">${varUser.roleId}</th>
            <th scope="col">${varUser.createDate}</th>
            <th scope="col">
                <form method="post" action="${pageContext.request.contextPath}/users" class="u-form">
                    <input name="user_id" value="${varUser.id}" style="display: none">
                    <input name="role_id" value="${3 - varUser.roleId}" style="display: none">
                    <input type="submit" value="Make ${varUser.roleId == 2 ? "not admin" : "admin"}">
                </form>
            </th>
            <th scope="col">
                <form method="post" action="${pageContext.request.contextPath}/users" class="u-form">
                    <input name="user_id" value="${varUser.id}" style="display: none">
                    <input name="role_id" value="-1" style="display: none">
                    <input type="submit" value="Delete user">
                </form>
            </th>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
