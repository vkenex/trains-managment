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

@WebServlet("/users/delete")
public class UserDeleteServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        try {
            User currentUser = (User) req.getSession().getAttribute("user");
            if (currentUser != null && currentUser.getId() == userId) {
                req.getSession().setAttribute("error", "Вы не можете удалить свою собственную учетную запись.");
                resp.sendRedirect(req.getContextPath() + "/users");
                return;
            }

            userService.delete(userId);
            resp.sendRedirect(req.getContextPath() + "/users");
        } catch (SQLException e) {
            throw new ServletException("Ошибка при удалении пользователя", e);
        }
    }
}