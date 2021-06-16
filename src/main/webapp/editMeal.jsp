<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@include file="css/editMeal.css"%>
    </style>
</head>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<body>
<h3><a href="meals">Home</a></h3>
<hr/>
<h2>Edit meal</h2>
<form method="POST" action="meals">
    <input type="hidden" name="id" value="<c:out value="${meal.id}" />"/>
    <table>
        <tbody>
        <tr>
            <td class="fieldName">DateTime :</td>
            <td><input class="fieldValue" type="datetime-local" name="dateTime"
                       value="<c:out value="${meal.dateTime}" />"/></td>
        </tr>
        <tr>
            <td class="fieldName">Description :</td>
            <td><input class="fieldValue" type="text" name="description" value="<c:out value="${meal.description}"/>"/>
            </td>
        </tr>
        <tr>
            <td class="fieldName">Calories :</td>
            <td><input class="fieldValue" type="number" name="calories" value="<c:out value="${meal.calories}" />"/>
            </td>
        </tr>
        </tbody>
    </table>
    <input type="submit" value="Save"/> <input type="button" onclick="window.location.href='meals';" value="Cancel">
</form>
</body>
</html>
