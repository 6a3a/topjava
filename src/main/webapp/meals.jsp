<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<h2>Meals</h2>

<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>

        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach var="meal" items="${meals}">
    <tr>
    <tr style="color:${meal.isExcess() ? 'red' : 'green'}">
        <td> ${meal.getDateTime()} </td>
        <td> ${meal.getDescription()} </td>
        <td> ${meal.getCalories()} </td>
    </tr>
    </c:forEach>


</table>
</body>
</html>