<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<c:set var="title" value="Catalog" scope="page"/>
<%@include file="head.jspf" %>
<body>
<c:set var="currentPage" value="catalog" scope="page"/>
<%@include file="header.jspf" %>

<jsp:useBean id="categories" scope="session" type="java.util.List"/>
<jsp:useBean id="sortTypes" scope="application" type="java.util.HashMap"/>
<jsp:useBean id="dishes" scope="session" type="java.util.List"/>
<jsp:useBean id="maxPage" scope="session" type="java.lang.Integer"/>

<nav class="c_header">
    <p class="c_category_name">${empty param.category || param.category == 0 ? "All dishes" : categories.get(param.category-1).name}</p>
    <form class="c_selectsort_form" action="${pageContext.request.contextPath}/catalog" method="get">
        <div>
            Category:
            <label>
                <select name="category" class="form-select">
                    <option value="0">All dishes</option>
                    <c:forEach var="category" items="${categories}">
                        <option ${param.category == category.id ? "selected" : ""} value="${category.id}">
                                ${category.name}
                        </option>
                    </c:forEach>
                </select>
            </label>
        </div>
        <div>
            SortBy:
            <select name="sortBy" class="form-select">
                <c:forEach var="sort" items="${sortTypes}">
                    <option ${param.sortBy == sort.value ? "selected" : ""} value="${sort.value}">${sort.key}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            ShowOnPage:
            <select name="dishesOnPage" class="form-select">
                <c:forTokens items="5,10,15" delims="," var="item">
                    <option ${param.dishesOnPage == item ? "selected" : ""}>${item}</option>
                </c:forTokens>
            </select>
        </div>
        <select name="page" style="display: none">
            <option value="0" selected></option>
        </select>
        <input class="btn btn-outline-secondary" type="submit" value="show"/>
    </form>
</nav>

<div class="c_dishes">
    <c:forEach var="dish" items="${dishes}">
        <%@include file="dish.jspf" %>
    </c:forEach>
</div>

<nav class="c_pagination">
    <ul class="pagination justify-content-end">
        <li class="page-item ${param.page > 0 ? "" : "disabled"}">
            <a class="page-link"
               href="${pageContext.request.contextPath}/catalog?category=${param.category}&sortBy=${param.sortBy}&page=${param.page-1}&dishesOnPage=${param.dishesOnPage}"
               tabindex="-1">
                Previous
            </a>
        </li>
        <c:forEach var="num" begin="0" end="${maxPage}">
            <li class="page-item ${param.page == num ? "active" : ""}">
                <a class="page-link"
                   href="${pageContext.request.contextPath}/catalog?category=${param.category}&sortBy=${param.sortBy}&page=${num}&dishesOnPage=${param.dishesOnPage}">
                        ${num+1}
                </a>
            </li>
        </c:forEach>
        <li class="page-item ${param.page < maxPage ? "" : "disabled"}">
            <a class="page-link"
               href="${pageContext.request.contextPath}/catalog?category=${param.category}&sortBy=${param.sortBy}&page=${param.page+1}&dishesOnPage=${param.dishesOnPage}">
                Next
            </a>
        </li>
    </ul>
</nav>

</body>
</html>
