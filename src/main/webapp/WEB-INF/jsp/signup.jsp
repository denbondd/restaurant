<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp</title>
    <style>
        <%@include file="../css/login_signup.css" %>
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
</head>
<body class="ls_body">
<div class="ls_container shadow">
    <p class="ls_title">Sign Up</p>
    <form class="ls_form" action="${pageContext.request.contextPath}/account/signup" method="post">
        <input name="login" class="ls_et form-control" placeholder="Login">
        <input type="password" name="password" class="ls_et form-control" placeholder="Password">
        <input type="submit" class="ls_btn btn btn-primary" value="SignUp">
    </form>
    <p class="ls_text">Already have an account? <a href="${pageContext.request.contextPath}/account/login">Log In</a>
    </p>
</div>
</body>
</html>
