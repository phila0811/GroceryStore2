<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: fireb
  Date: 10/12/2018
  Time: 1:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Item</title>
</head>
<body>

<form:form action="addItem" modelAttribute="item" method="post" >
    <form:hidden path="id" />

    <table>
        <tbody>

        <tr>
            <td><label><strong>Name:</strong></label></td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td><label><strong>Price:</strong></label></td>
            <td><form:input path="price"/></td>
        </tr>

        <tr>
            <td><label></label></td>
            <td><input type="submit" value="Save" class="save" /></td>
        </tr>

        </tbody>
    </table>

</form:form>

</body>
</html>
