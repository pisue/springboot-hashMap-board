<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입 페이지</title>
</head>
<body>
<h1>회원가입 페이지</h1>
<form action="/join" method="POST">
    <input type="text" name="username" placeholder="Username" value="ssar"> <br>
    <input type="password" name="password" placeholder="Password" value="1234"> <br>
    <input type="email" name="email" placeholder="email" value="ssar@naver.com"> <br>
    <button>회원가입</button>
</form>
</body>
</html>