package com.vkenex.trainsmanagment.servlet.wagons;

import com.vkenex.trainsmanagment.dto.TrainDto;
import com.vkenex.trainsmanagment.entity.Wagon;
import com.vkenex.trainsmanagment.service.TrainService;
import com.vkenex.trainsmanagment.service.WagonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/wagons")
public class WagonListServlet extends HttpServlet {

    private final WagonService wagonService = WagonService.getInstance();
    private final TrainService trainService = TrainService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long trainId = Long.parseLong(req.getParameter("trainId"));
        try {
            List<Wagon> wagons = wagonService.findAllByTrainId(trainId);
            Optional<TrainDto> train = trainService.findById(trainId);

            req.setAttribute("wagons", wagons);
            req.setAttribute("train", train.orElse(null));

            getServletContext().getRequestDispatcher("/WEB-INF/jsp/wagons.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Не удалось получить список вагонов", e);
        }
    }
}