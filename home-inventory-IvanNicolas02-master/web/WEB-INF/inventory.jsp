<%-- 
    Document   : inventory
    Created on : Feb 15, 2022, 8:27:27 PM
    Author     : ivanc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="userInventory.css">
        <title>Home Inventory</title>
    </head>
    <body>
        <div class="header">
            <h1 class="logo">HOME nVentory </h1>
            <div class="header-right">
                <c:if test="${isAdmin == true}">
                    <a href="admin">Admin</a>
                    <a href="category">Categories</a>
                </c:if>
                <a href="edit">Edit</a>
                <a href="login?logout">Logout</a>
            </div>
        </div>
        <h2 class="h2">Inventory for ${fullname}</h2>
        <table id="inventory">
            <tr>
                <th>Category</th>
                <th>Name</th>
                <th>Price</th>
                <th>Delete</th>
                <th>Edit</th>
            </tr>
            <c:forEach var="inv" items="${inventory}">
                <form action="inventory" method="post">
                <tr>
                    <td>${inv.category.categoryName}</td>
                    <td>${inv.itemName}</td>
                    <td>$${inv.price}</td>
                    <td><Input type="submit" name="action" value="Delete"></td>
                    <td><Input type="submit" name="action" value="Edit"></td>
                    <input type="hidden" name="itemID" value="${inv.itemID}">
                </tr>
                </form>
            </c:forEach>
        </table>
        
        <div class="addItem">
        <h3 class="h3">Add Item</h3>
        <form action="inventory" method="post">
            <label>Category</label>
            <select name="category">
                <c:forEach var="category" items="${category}">
                    <option value="${category.categoryID}">${category.categoryName}</option>
                </c:forEach>
            </select><br>
            <label>Name: </label>
            <input type="text" name="itemname" value=""><br>
            <label>Price: </label>
            <input type="number" name="price" value=""><br>
            <input type="submit" name="action" value="Add">
        </form>
        </div>
        
        <div class="editItem" style="${hiddenval}">
        <h3 class="h3">Edit Item</h3>
        <form action="inventory" method="post">
            <label>Category</label>
            <select name="editCategory">
                <c:forEach var="category" items="${category}">
                    <option value="${category.categoryID}">${category.categoryName}</option>
                </c:forEach>
            </select><br>
            <label>Name: </label>
            <input type="text" name="editItemName" value="${editItem.itemName}"><br>
            <label>Price: </label>
            <input type="number" name="editPrice" value="${editItem.price}"><br>
            <input type="submit" name="action" value="Save">
            <input type="hidden" name="editItemID" value="${editItem.itemID}">
        </form>
        </div>
    </body>
</html>
