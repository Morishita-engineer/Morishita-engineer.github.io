package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.MemoDAO;
import model.Memo;
import model.User;

@WebServlet("/CreateMemoServlet")
public class CreateMemoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/welcome.jsp");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if (title == null || title.isEmpty()) {
            session.setAttribute("errorMessage", "タイトルは必須です。");
            response.sendRedirect(request.getContextPath() + "/MemoListServlet");
            return;
        }

        Memo memo = new Memo();
        memo.setUserId(loginUser.getUsername());
        memo.setTitle(title);
        memo.setContent(content);

        MemoDAO memoDAO = new MemoDAO();
        memoDAO.create(memo);

        session.setAttribute("successMessage", "新しいメモを作成しました。");
        response.sendRedirect(request.getContextPath() + "/MemoListServlet");
    }
}

