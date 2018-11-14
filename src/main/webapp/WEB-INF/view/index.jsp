<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: fireb
  Date: 10/8/2018
  Time: 3:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Welcome</title>
</head>
<body>
<table>
    <tr>
        <td>Welcome <security:authentication property="principal.username"/></td>
    </tr>



    <security:authorize access="hasRole('ADMIN')">
        <a href="${pageContext.request.contextPath}/admin/showAddItemPage">Add Item</a>
    </security:authorize>
    <br><br>
    <form:form action="${pageContext.request.contextPath}/logout"
               method="post">

        <input type="submit" value="Logout" />
    </form:form>
    <br><br>
    Number of Items in Shopping Cart: ${numberOfItems}
    <a href="${pageContext.request.contextPath}/user/showShoppingCart">Go to Cart</a>
    <br><br>

    <table>
        <tbody>
                    <tr>
                        <td><strong>Name</strong></td>
                        <td><strong>Price</strong></td>
                    </tr>
            <c:forEach var="item" items="${items}">
                <c:url var="deleteLink" value="${pageContext.request.contextPath}/admin/deleteItem">
                    <c:param name="itemId" value="${item.id}" />
                </c:url>
                <c:url var="updateLink" value="${pageContext.request.contextPath}/admin/showUpdateForm">
                    <c:param name="itemId" value="${item.id}" />
                </c:url>
                <c:url var="addItemsToCart" value="${pageContext.request.contextPath}/user/addItemsToCart">
                    <c:param name="itemId" value="${item.id}"/>
                </c:url>
                    <tr>
                        <td>${item.name}</td>
                        <td>${item.price}</td>
                        <td>
                            <form:form action="${addItemsToCart}" method="post" methodParam="numberOfItems">
                                <select name="numberOfItems">
                                <option value="0">0</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                </select>
                            <input type="submit" value="Add To Cart">
                            </form:form>

                        </td>
                        <security:authorize access="hasRole('ADMIN')">
                        <td>
                            <a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this item>')))return false">Delete</a>
                            |
                            <a href="${updateLink}">Update</a></td>
                        </security:authorize>
                    </tr>
            </c:forEach>
        </tbody>
    </table>
</table>
</body>
</html>