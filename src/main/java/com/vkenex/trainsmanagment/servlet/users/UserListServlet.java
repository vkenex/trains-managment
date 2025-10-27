package com.vkenex.trainsmanagment.servlet.users;

import com.vkenex.trainsmanagment.entity.User;
import com.vkenex.trainsmanagment.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
public class UserListServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users = userService.findAll();
            req.setAttribute("users", users);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Не удалось получить список пользователей", e);
        }
    }
}