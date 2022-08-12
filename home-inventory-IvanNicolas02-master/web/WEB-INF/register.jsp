<%-- 
    Document   : register
    Created on : Apr 11, 2022, 9:08:03 PM
    Author     : ivanc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="register.css">
        <title>Register</title>
    </head>
    <body>
        <div class="header">
            <h1 class="logo">HOME nVentory </h1>
            <div class="header-right">
                <a href="login">Log In</a>
            </div>
        </div>
        <h2 class="h2">Register</h2>
        <div class="createUser">
            <form action="register" method="post">
                <label>Username: </label>
                <input type="text" name="username" value=""><br>
                <label>Password: </label>
                <input type="password" name="password" value=""><br>
                <label>Email: </label>
                <input type="email" name="email" value=""><br>
                <label>First Name: </label>
                <input type="text" name="firstName" value=""><br>
                <label>Last Name: </label>
                <input type="text" name="lastName" value=""><br>
                <input type="submit" name="action" value="Register">
                <c:if test="${error == true}">
                    <p>Username Already Used</p>
                </c:if>
                <c:if test="${complete == true}">
                    <p>Account Created</p>
                </c:if>   
        </form>
        </div>
        
    </body>
</html>
