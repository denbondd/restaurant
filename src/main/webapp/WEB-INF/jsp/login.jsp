<html>
<head>
    <title>LogIn</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/account/login" method="post">
    <input name="login">
    <input type="password" name="password">
    <input type="submit" title="LogIn">
</form>
Don't have an account? <a href="${pageContext.request.contextPath}/account/signup">SignUp</a>
</body>
</html>
