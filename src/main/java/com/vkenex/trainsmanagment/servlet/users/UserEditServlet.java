package com.vkenex.trainsmanagment.servlet.users;

import com.vkenex.trainsmanagment.entity.User;
import com.vkenex.trainsmanagment.entity.enums.UserRole;
import com.vkenex.trainsmanagment.exception.ValidationException;
import com.vkenex.trainsmanagment.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/users/edit")
public class UserEditServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("id"));
        try {
            Optional<User> userOptional = userService.findById(userId);
            if (userOptional.isPresent()) {
                req.setAttribute("user", userOptional.get());
                req.setAttribute("roles", UserRole.values());
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/user-edit.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Пользователь не найден");
            }
        } catch (SQLException e) {
            throw new ServletException("Ошибка при загрузке данных пользователя", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("id"));

        User user = new User();
        user.setId(userId);
        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));
        user.setLogin(req.getParameter("login"));
        user.setRole(UserRole.valueOf(req.getParameter("role")));

        String password = req.getParameter("password");
        if (password != null && !password.isEmpty()) {
            user.setPasswordHash(password);
        }

        try {
            userService.update(user);
            resp.sendRedirect(req.getContextPath() + "/users");
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка базы данных при обновлении пользователя", e);
        }
    }
}