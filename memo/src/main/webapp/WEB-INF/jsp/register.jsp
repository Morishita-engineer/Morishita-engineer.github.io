<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ユーザー登録</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/register.css">
</head>
<body>
    <header class="page-header">
        <a href="login.jsp" class="btn back-to-list">ログインに戻る</a>
    </header>

    <div class="register-container">
        <h2>ユーザー登録</h2>
        <form action="RegisterServlet" method="post" id="registerForm">
            <div class="form-group">
                <label for="username">ユーザー名</label>
                <input type="text" id="username" name="username" required placeholder="希望のユーザー名を入力してください">
                <div class="error-message-inline" id="register-username-error"></div>
            </div>
            <div class="form-group">
                <label for="password">パスワード</label>
                <input type="password" id="password" name="password" required placeholder="パスワードを入力してください">
                <p class="password-requirements">8文字以上、半角英数字と記号を組み合わせてください。</p>
                <div class="error-message-inline" id="register-password-error"></div>
            </div>
            <div class="form-group">
                <label for="passwordConfirm">パスワード確認</label>
                <input type="password" id="passwordConfirm" name="passwordConfirm" required placeholder="パスワードをもう一度入力してください">
                <div class="error-message-inline" id="register-password-confirm-error"></div>
            </div>
            <button type="submit" class="btn-register">登録</button>
        </form>
        
        <div id="error-message" class="general-error-message">
            <%
                String error = (String) request.getAttribute("error");
                if (error != null && !error.isEmpty()) {
            %>
                <p><%= error %></p>
            <%
                }
            %>
        </div>
    </div>
    
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/register.js"></script>
</body>
</html>