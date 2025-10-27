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

@WebServlet("/users/add")
public class UserAddServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", UserRole.values());
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/user-add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setFirstName(req.getParameter("firstName"));
        user.setLastName(req.getParameter("lastName"));
        user.setLogin(req.getParameter("login"));
        user.setPasswordHash(req.getParameter("password"));
        user.setRole(UserRole.valueOf(req.getParameter("role")));

        try {
            userService.create(user);
            resp.sendRedirect(req.getContextPath() + "/users");
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка базы данных при создании пользователя", e);
        }
    }
}