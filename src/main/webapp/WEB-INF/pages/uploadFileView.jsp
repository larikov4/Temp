<%--
  Created by IntelliJ IDEA.
  User: Yevhn
  Date: 07.12.2015
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File upload</title>
</head>
    <body>
    <h2>Please upload a file</h2>
        <form method="post" action="upload" enctype="multipart/form-data">
            <input type="file" name="file"/>
            <input type="submit"/>
        </form>
    </body>
</html>
