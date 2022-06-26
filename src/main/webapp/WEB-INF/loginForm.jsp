<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인 페이지</title>
</head>
<body>
    <h1>로그인 페이지</h1>
    <form action="/login" method="POST">
        <input type="text" name="username" placeholder="Username" value="ssar"> <br>
        <input type="password" name="password" placeholder="Password" value="1234">
        <button>로그인</button>
    </form>

    <a href="/joinForm">회원가입</a>
</body>
</html>