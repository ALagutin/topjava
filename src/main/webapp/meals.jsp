<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Подсчет калорий</title>
    <style>
        <%@include file="css/meals.css"%>
    </style>
</head>
<body>
<h3>Моя еда</h3>
<table>
    <thead>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="mealTo" items="${mealsTo}">
        <tr excess= ${mealTo.excess}>
            <td>${mealTo.date} ${mealTo.time}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>