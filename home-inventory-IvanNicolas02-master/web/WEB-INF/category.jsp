<%-- 
    Document   : category
    Created on : Apr 20, 2022, 7:26:41 PM
    Author     : ivanc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="categories.css">
        <title>Categories</title>
    </head>
    <body>
        <div class="header">
            <h1 class="logo">HOME nVentory </h1>
            <div class="header-right">
                <c:if test="${isAdmin == true}">
                    <a href="admin">Admin</a>
                </c:if>
                <a href="inventory">Inventory</a>
                <a href="edit">Edit</a>
                <a href="login?logout">Logout</a>
            </div>
        </div>
        <h2 class="h2">Categories</h2>
        <table id="category">
            <tr>
                <th>Category</th>
                <th>Edit</th>
            </tr>
            <c:forEach var="cat" items="${categoriesList}">
                <form action="category" method="post">
                <tr>
                    <td>${cat.categoryName}</td>
                    <td><Input type="submit" name="action" value="Edit"></td>
                    <input type="hidden" name="categoryID" value="${cat.categoryID}">
                </tr>
                </form>
            </c:forEach>
        </table>
        
        <div class="addCategory">
        <h3 class="h3">Add Category</h3>
        <form action="category" method="post">
            <label>Category:  </label>
            <input type="text" name="categoryName" value=""><br>
            <input type="submit" name="action" value="Add">
        </form>
        </div>
        
        <div class="editCategory" style="${hiddenval}">
        <h3 class="h3">Edit Category</h3>
        <form action="category" method="post">
            <label>Category: </label>
            <input type="text" name="editCategoryName" value="${editCategory.categoryName}"><br>
            <input type="submit" name="action" value="Save">
            <input type="hidden" name="editCategoryID" value="${editCategory.categoryID}">
        </form>
        </div>
    </body>
</html>
