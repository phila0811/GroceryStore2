<%@ taglib prefix="for" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: fireb
  Date: 10/23/2018
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
 <h2>Shopping Cart</h2>
    <table>
        <tbody>
        <tr>
            <td>Name</td>
            <td>Quantity</td>
            <td>Price</td>
        </tr>
        <c:forEach var="shoppingCartItem" items="${shoppingCartItems}">
            <tr>
                <td>${shoppingCartItem.itemName}</td>
                <td>${shoppingCartItem.quantity}</td>
                <td>$${shoppingCartItem.total}</td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
 <br><br>
 <p>
     Your total price is: $${total}
 </p>
<br>
<a href="${pageContext.request.contextPath}/user/showItemPage">Back To Home</a>

</body>
</html>
