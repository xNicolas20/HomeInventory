<%-- 
    Document   : login
    Created on : Feb 15, 2022, 8:27:41 PM
    Author     : ivanc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="login.css">
        <title>Home Inventory</title>
    </head>
    <body>
        <div class="header">
            <h1 class="logo">HOME nVentory </h1>
            <div class="header-right">
                <a href="register">Create Account</a>
            </div>
        </div>
        <div class="login">
        <form method="post" action="login">
            <label>User name: </label>
            <input type="text" name="username" value=""><br>
            <label>Password: </label>
            <input type="password" name="password" value=""><br>
            <input type="submit" name="sumbit" value="Submit">
        </form>
        </div>
        <c:if test="${isError == true}">
            <p>Invalid Login</p>
        </c:if>
        <c:if test="${deactivate == true}">
            <p>Deactivate account</p>
        </c:if>
        <p>${logoutMessage}</p>
    </body>
</html>
