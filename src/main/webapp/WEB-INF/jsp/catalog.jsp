<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<c:set var="title" value="Catalog" scope="page"/>
<%@include file="head.jspf" %>
<body>
<c:set var="currentPage" value="catalog" scope="page"/>
<%@include file="header.jspf" %>

<jsp:useBean id="categories" scope="request" type="java.util.List"/>
<nav class="c_header">
    <p class="c_category_name">${empty param.category || param.category == 0 ? "All dishes" : categories.get(param.category-1).name}</p>
    <form class="c_selectsort_form" action="${pageContext.request.contextPath}/catalog" method="get">
        <div>
        Category:
        <select name="category" class="form-select">
            <option value="">All dishes</option>
            <c:forEach var="category" items="${categories}">
                <option ${param.category == category.id ? "selected" : ""} value="${category.id}">
                        ${category.name}
                </option>
            </c:forEach>
        </select>
        </div>
        <div>
        SortBy:
        <select name="sortBy" class="form-select">
            <option value="default">Default</option>
            <c:forEach var="sort" items="${sortTypes}">
                <option ${param.sortBy == sort.value ? "selected" : ""} value="${sort.value}">${sort.key}</option>
            </c:forEach>
        </select>
        </div>
        <input class="btn btn-outline-secondary" type="submit" value="show"/>
    </form>
</nav>

<div class="c_dishes">
    <c:forEach var="dish" items="${dishes}">
        <%@include file="dish.jspf" %>
    </c:forEach>
</div>

</body>
</html>
