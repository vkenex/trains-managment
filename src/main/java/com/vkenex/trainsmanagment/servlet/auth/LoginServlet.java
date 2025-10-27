package com.vkenex.trainsmanagment.servlet.auth;

import com.vkenex.trainsmanagment.entity.User;
import com.vkenex.trainsmanagment.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            Optional<User> userOptional = userService.login(login, password);

            if (userOptional.isPresent()) {
                req.getSession().setAttribute("user", userOptional.get());
                resp.sendRedirect(req.getContextPath() + "/trains");
            } else {
                req.setAttribute("error", "Неверный логин или пароль");
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException("Ошибка базы данных при попытке входа", e);
        }
    }
}