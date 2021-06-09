<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Подсчет калорий</title>
    <style>
        <%@include file="css/meals.css"%>
    </style>
</head>
<jsp:useBean id="mealsTo" scope="request" type="java.util.ArrayList"/>
<body>
<h2>Моя еда</h2>
<a href="?action=create">
    <span class="addMeal">Добавить</span>
</a>
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
            <td>
                <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
            </td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td>
                <a href="?action=edit&id=<c:out value="${mealTo.id}"/>">
                    <span class="pencil"></span>
                </a>
            </td>
            <td>
                <a href="?action=delete&id=<c:out value="${mealTo.id}"/>">
                    <span class="delete"></span>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>