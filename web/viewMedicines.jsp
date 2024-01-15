<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список лекарств</title>
</head>
<body>
    <h2>Список лекарств</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Название</th>
                <th>Количество</th>
                <th>Действия</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="medicine" items="${medicines}">
                <tr>
                    <td>${medicine.id}</td>
                    <td>${medicine.name}</td>
                    <td>${medicine.quantity}</td>
                    <td>
                        <a href="MedicineServlet?action=edit&id=${medicine.id}">Редактировать</a>
                        <!-- Добавьте ссылку для удаления, если нужно -->
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
    <p><a href="addMedicine.jsp">Добавить лекарство</a></p>
</body>
</html>
