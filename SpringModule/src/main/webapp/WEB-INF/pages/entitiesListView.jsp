<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>List View</title>
</head>
    <body>
        <c:forEach items="${users}" var="user">
           ${user.name}
        </c:forEach>
    </body>
</html>
