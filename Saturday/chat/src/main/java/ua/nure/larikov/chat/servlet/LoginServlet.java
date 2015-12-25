package ua.nure.larikov.chat.servlet;

import ua.nure.larikov.chat.db.HashMapStorage;
import ua.nure.larikov.chat.entities.User;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final Logger log = Logger.getLogger(getClass().getName());

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        log.info("enter login");
        if (request.getSession().getAttribute("user") != null) {
            log.info(request.getSession().getAttribute("user").toString());
            response.sendRedirect("chatroom");
        } else {
            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request,
                    response);
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("chatroom");
        } else {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            if (HashMapStorage.getInstance().isExistingUser(login, password)) {
                User user = HashMapStorage.getInstance().findUserByLogin(login);
                request.getSession().setAttribute("user", user);
                response.sendRedirect("chatroom");
            } else {
                request.setAttribute("password", password);
                request.setAttribute("login", login);
                request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(
                        request, response);
            }
        }
    }
}
