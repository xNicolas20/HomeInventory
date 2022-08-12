<%-- 
    Document   : admin
    Created on : Feb 15, 2022, 8:27:16 PM
    Author     : ivanc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory for Admin</title>
        <link rel="stylesheet" href="admin.css">
    </head>
    <body>
        <div class="header">
            <h1 class="logo">HOME nVentory </h1>
            <div class="header-right">
                <a href="inventory">Inventory</a>
                <a href="category">Category</a>
                <a href="edit">Edit</a>
                <a href="login?logout">Logout</a>
            </div>
        </div>
        <h2 class="h2">Manage Users</h2>
            <table id="user">
            <tr>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Delete</th>
                <th>Edit</th>
            </tr>
            <c:forEach var="user" items="${users}">
                <form action="admin" method="post">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td><Input type="submit" name="action" value="Delete"></td>
                    <td><Input type="submit" name="action" value="Edit"></td>
                    <input type="hidden" name="usernameValue" value="${user.username}">
                </tr>
                </form>
            </c:forEach>
            <c:if test="${deleteError == true}">
                <p>Could Not Delete This User</p>
            </c:if>
            </table>
        <div class="addUSer">
            <h3>Add User</h3>
            <form action="admin" method="post">
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
                <input type="submit" name="action" value="Add">
            </form>
            <c:if test="${addError == true}">
                <p>Username Already Taken</p>
            </c:if>
            <c:if test="${nullValue == true}">
                <p>Fill Out All Input</p>
            </c:if>
        </div>
        <div class="editUSer" style="${hiddenval}">
            <h3>Edit User</h3>
            <form action="admin" method="post">
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
                <label class="admin" for="adminid">Admin</label>
                <input type="checkbox" name="isAdmin" value="isAdmin" id="adminid" <c:if test="${editUser.isAdmin == true}">checked</c:if>><br>
                <label class="active" for="activeid">Active</label>
                <input type="checkbox" name="isActive" value="isActive" id="activeid" <c:if test="${editUser.active == true}">checked</c:if>><br>
                <input type="submit" name="action" value="Save">
                <input type="hidden" name="usernameValue" value="${editUser.username}">
            </form>
        </div>
    </body>
</html>
