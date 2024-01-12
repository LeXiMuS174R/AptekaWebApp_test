<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Edit Medicine</title>
</head>
<body>
    <h2>Edit Medicine</h2>
    <form action="MedicineServlet" method="post">
        <input type="hidden" name="id" value="${medicine.id}">
        Name: <input type="text" name="name" value="${medicine.name}" required><br>
        Quantity: <input type="number" name="quantity" value="${medicine.quantity}" required><br>
        <input type="submit" value="Edit Medicine">
    </form>
</body>
</html>
