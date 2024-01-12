<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Добавление лекарств</title>
</head>
<body>
    <h2>Добавьте лекарство</h2>
    <form action="MedicineServlet" method="post">
        Имя: <input type="text" name="name" required><br>
        Кол-во: <input type="number" name="quantity" required><br>
        <input type="submit" value="Add Medicine">
    </form>
</body>
</html>
