<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <style type="text/css">
        TABLE {
            /*width: 300px; !* Ширина таблицы *!*/
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
        }

        TD, TH {
            padding: 10px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
        }
    </style>
</head>
<body>


<h3><a href="index.html">Home</a></h3>
<hr>
<h3><a href="${pageContext.request.contextPath}/meals?action=add">Add Meal</a></h3>
<h2>Meals</h2>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>

        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach var="meal" items="${meals}">
    <tr>
    <tr style="color:${meal.isExcess() ? 'red' : 'green'}">
        <td> ${meal.dateTime.toString().replace("T", " ")} </td>
        <td> ${meal.description} </td>
        <td> ${meal.calories} </td>
        <td>
            <a href="${pageContext.request.contextPath}/meals?action=edit&id=<c:out value="${meal.id}"/>">Update</a>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a>
        </td>
    </tr>
    </c:forEach>


</table>
</body>
</html>