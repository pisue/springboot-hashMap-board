<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <br>
        <c:if test="${error eq true}">
            <p>${exception}</p>
        </c:if>

    </form>

    <div>
        <a href="/oauth2/authorization/google">구글 로그인</a>
        <a href="/oauth2/authorization/facebook">페이스북 로그인</a>
        <a href="/oauth2/authorization/naver">네이버 로그인</a>
        <a href="/oauth2/authorization/kakao">카카오 로그인</a>
    </div>

    <br>
    <a href="/joinForm">회원가입</a>

</body>
</html>