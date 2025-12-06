<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>メモ一覧</title>
    <%-- Font Awesomeのアイコンを使うためのリンク --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <div class="container">
        <header class="page-header">
            <h1><c:out value="${sessionScope.loginUser.username}" />さんのメモ一覧</h1>
            <div class="header-actions">
                <%-- ログアウト処理を行うサーブレットへのリンク --%>
                <a href="LogoutServlet" class="btn btn-secondary"><i class="fas fa-sign-out-alt"></i> ログアウト</a>
            </div>
        </header>

        <%-- メッセージ表示エリア --%>
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="message success">${sessionScope.successMessage}</div>
            <% session.removeAttribute("successMessage"); %>
        </c:if>
        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="message error">${sessionScope.errorMessage}</div>
            <% session.removeAttribute("errorMessage"); %>
        </c:if>

        <%-- 新規メモ作成フォーム --%>
        <div class="memo-form-container">
            <h2><i class="fas fa-plus"></i> 新規メモ作成</h2>
            <form action="CreateMemoServlet" method="post">
                <div class="form-group">
                    <label for="title">タイトル</label>
                    <input type="text" id="title" name="title" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="content">内容</label>
                    <textarea id="content" name="content" rows="4" class="form-control"></textarea>
                </div>
                <button type="submit" class="btn btn-success">作成する</button>
            </form>
        </div>

        <hr class="section-divider">

        <%-- メモ一覧表示エリア --%>
        <div class="memo-list">
            <c:choose>
                <%-- メモが存在する場合 --%>
                <c:when test="${not empty memoList}">
                    <c:forEach var="memo" items="${memoList}">
                        <div class="memo-item">
                            <h3>
                                <c:out value="${memo.title}" />
                                <span class="memo-date">
                                    <fmt:formatDate value="${memo.updatedAt}" pattern="yyyy/MM/dd" />
                                </span>
                            </h3>
                            <%-- 改行を<br>タグに変換して表示 --%>
                            <p>${memo.content.replace(System.lineSeparator(), "<br>")}</p>
                            <div class="actions">
                                <a href="EditMemoServlet?id=${memo.id}" class="btn btn-primary"><i class="fas fa-edit"></i> 編集</a>
                                <a href="DeleteMemoServlet?id=${memo.id}" class="btn btn-danger" onclick="return confirm('本当に削除しますか？');"><i class="fas fa-trash-alt"></i> 削除</a>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <%-- メモが存在しない場合 --%>
                <c:otherwise>
                    <p class="no-memos">まだメモはありません。最初のメモを作成してみましょう！</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>
