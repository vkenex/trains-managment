package com.vkenex.trainsmanagment.servlet.trains;

import com.vkenex.trainsmanagment.service.TrainService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/trains/delete")
public class TrainDeleteServlet extends HttpServlet {

    private final TrainService trainService = TrainService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long trainId = Long.parseLong(req.getParameter("id"));
        try {
            trainService.delete(trainId);
            resp.sendRedirect(req.getContextPath() + "/trains");
        } catch (SQLException e) {
            throw new ServletException("Ошибка при удалении поезда", e);
        }
    }
}