package com.vkenex.trainsmanagment.servlet.trains;

import com.vkenex.trainsmanagment.dto.TrainDto;
import com.vkenex.trainsmanagment.entity.Station;
import com.vkenex.trainsmanagment.entity.Train;
import com.vkenex.trainsmanagment.exception.ValidationException;
import com.vkenex.trainsmanagment.service.StationService;
import com.vkenex.trainsmanagment.service.TrainService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/trains/edit")
public class TrainEditServlet extends HttpServlet {

    private final TrainService trainService = TrainService.getInstance();
    private final StationService stationService = StationService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long trainId = Long.parseLong(req.getParameter("id"));
        try {
            Optional<TrainDto> trainDto = trainService.findById(trainId);
            List<Station> stations = stationService.findAll();

            if (trainDto.isPresent()) {
                req.setAttribute("train", trainDto.get());
                req.setAttribute("stations", stations);
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/train-edit.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Поезд не найден");
            }
        } catch (SQLException e) {
            throw new ServletException("Ошибка при загрузке данных поезда", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long trainId = Long.parseLong(req.getParameter("id"));

        Train train = new Train();
        train.setId(trainId);
        train.setNumber(Integer.parseInt(req.getParameter("number")));
        train.setDepartureStationId(Long.parseLong(req.getParameter("departureStationId")));
        train.setArrivalStationId(Long.parseLong(req.getParameter("arrivalStationId")));
        train.setTimeOfDeparture(LocalDateTime.parse(req.getParameter("departureTime")));
        train.setTimeOfArrival(LocalDateTime.parse(req.getParameter("arrivalTime")));

        try {
            trainService.update(train);
            resp.sendRedirect(req.getContextPath() + "/trains");
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка базы данных при обновлении поезда", e);
        }
    }
}