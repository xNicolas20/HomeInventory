<%-- 
    Document   : editUser
    Created on : Apr 12, 2022, 12:11:58 AM
    Author     : ivanc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="edit.css">
        <title>Edit User</title>
    </head>
    <body>
        <div class="header">
            <h1 class="logo">HOME nVentory </h1>
            <div class="header-right">
                <a href="inventory">Inventory</a>
                <c:if test="${isAdmin == true}">
                    <a href="admin">Admin</a>
                    <a href="category">Categories</a>
                </c:if>
                <a href="login?logout">Logout</a>
            </div>
        </div>
        <div class="editUSer">
            <h2>Edit User</h2>
            <form action="edit" method="post">
                <label>Username: </label>
                <input type="text" name="username" value="${editUser.username}"><br>
                <label>Password: </label>
                <input type="password" name="password" value="${editUser.password}"><br>
                <label>Email: </label>
                <input type="email" name="email" value="${editUser.email}"><br>
                <label>First Name: </label>
                <input type="text" name="firstName" value="${editUser.firstName}"><br>
                <label>Last Name: </label>
                <input type="text" name="lastName" value="${editUser.lastName}"><br>
                <input type="submit" name="action" value="Save">
                <input type="submit" name="action" value="Deactivate">
                <input type="hidden" name="usernameValue" value="${editUser.username}">
            </form>
        </div>
    </body>
</html>
