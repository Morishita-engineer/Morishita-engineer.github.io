package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.MemoDAO;
import model.Memo;
import model.User;

@WebServlet("/MemoListServlet")
public class MemoListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/welcome.jsp");
            return;
        }

        MemoDAO memoDAO = new MemoDAO();
        List<Memo> memoList = memoDAO.findByUserId(loginUser.getUsername());

        request.setAttribute("memoList", memoList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/memoList.jsp");
        dispatcher.forward(request, response);
    }
}

