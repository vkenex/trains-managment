package com.vkenex.trainsmanagment.servlet.wagons;

import com.vkenex.trainsmanagment.service.WagonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/wagons/delete")
public class WagonDeleteServlet extends HttpServlet {

    private final WagonService wagonService = WagonService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long wagonId = Long.parseLong(req.getParameter("id"));
        Long trainId = Long.parseLong(req.getParameter("trainId"));

        try {
            wagonService.delete(wagonId);
            resp.sendRedirect(req.getContextPath() + "/wagons?trainId=" + trainId);
        } catch (SQLException e) {
            throw new ServletException("Ошибка при удалении вагона", e);
        }
    }
}