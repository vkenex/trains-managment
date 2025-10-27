package com.vkenex.trainsmanagment.servlet.trains;

import com.vkenex.trainsmanagment.dto.TrainDto;
import com.vkenex.trainsmanagment.service.TrainService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/trains")
public class TrainListServlet extends HttpServlet {

    private final TrainService trainService = TrainService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<TrainDto> trainDtos = trainService.findAll();
            req.setAttribute("trains", trainDtos);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/trains.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Не удалось получить список поездов", e);
        }
    }
}