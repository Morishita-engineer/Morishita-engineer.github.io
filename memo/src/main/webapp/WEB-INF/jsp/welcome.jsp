<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/welcome.css">
</head>
<body>
    <div class="login-container">
        <h2>ログイン</h2>
        <form action="LoginServlet" method="post" id="loginForm">
            <div class="form-group">
                <label for="username">ユーザー名</label>
                <input type="text" id="username" name="username" placeholder="ユーザー名を入力してください" required>
                <div class="error-message-inline" id="username-error"></div> </div>

            <div class="form-group">
                <label for="password">パスワード</label>
                <input type="password" id="password" name="password" placeholder="パスワードを入力してください" required>
                <div class="error-message-inline" id="password-error"></div>
            </div>

            <button type="submit">ログイン</button>
        </form>
        <div id="error-message" class="general-error-message"></div> <div class="register-link-container">
            <p>アカウントをお持ちでないですか？ <a href="${pageContext.request.contextPath}/RegisterServlet">新規登録はこちら</a></p>
        </div>
    </div>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/welcome.js"></script>
</body>
</html>