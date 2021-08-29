<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/account/signup" method="post">
    <input name="login">
    <input type="password" name="password">
    <input type="submit" title="SignUp">
</form>
Already have an account? <a href="${pageContext.request.contextPath}/account/signup">LogIn</a>
</body>
</html>
