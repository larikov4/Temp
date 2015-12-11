<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>List View</title>
</head>
    <body>
        <h2>Users</h2>
        <table>
            <tr>
                <td>id</td>
                <td>name</td>
                <td>email</td>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
