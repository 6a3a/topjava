<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <title>New</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<form method="POST" action="${pageContext.request.contextPath}/meals">

    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <input type="hidden" name="id" value="<c:out value="${meal.id}" />">
    <h2>${meal.id == null ? "Add Meal" : "Edit Meal"}</h2>
    <table>
        <tbody>
        <tr>
            <td>Date:</td>
            <td><label>
                <input type="datetime-local" name="date"
                       value="<c:out value="${meal.dateTime}" />"/>
            </label> <br/>
            </td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><label>
                <input type="text" name="description"
                       value="<c:out value="${meal.description}" />"/>
            </label> <br/>
            </td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><label>
                <input type="text" name="calories"
                       value="<c:out value="${meal.calories}" />"/>
            </label> <br/>
            </td>
        </tr>
        </tbody>
    </table>
    <input type="submit" value="Save"/>
    <button onclick="location.href='${pageContext.request.contextPath}/meals'" type="button">
        Cancel
    </button>
</form>
</body>
</html>