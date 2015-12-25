<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Micha Kops">

    <script src="resource/js/jquery-1.10.2.min.js"></script>
    <!-- Le styles -->
    <link href="resource/css/bootstrap.css" rel="stylesheet">
    <link href="resource/css/bootstrap-responsive.css" rel="stylesheet">

</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">

            <form class="form-signin" action="login" method="post">
                <h2 class="form-signin-heading">Please sign in</h2>
                <label for="inputLogin" class="sr-only">Login</label>
                <input type="text" id="inputLogin" name="login" class="form-control" placeholder="Login" required
                       autofocus>
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password"
                       required>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>
</body>
</html>