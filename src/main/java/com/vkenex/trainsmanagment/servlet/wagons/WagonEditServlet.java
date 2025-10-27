package com.vkenex.trainsmanagment.servlet.wagons;

import com.vkenex.trainsmanagment.entity.Wagon;
import com.vkenex.trainsmanagment.entity.enums.WagonType;
import com.vkenex.trainsmanagment.exception.ValidationException;
import com.vkenex.trainsmanagment.service.WagonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/wagons/edit")
public class WagonEditServlet extends HttpServlet {

    private final WagonService wagonService = WagonService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long wagonId = Long.parseLong(req.getParameter("id"));
        try {
            Optional<Wagon> wagon = wagonService.findById(wagonId);
            if (wagon.isPresent()) {
                req.setAttribute("wagon", wagon.get());
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/wagon-edit.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Вагон не найден");
            }
        } catch (SQLException e) {
            throw new ServletException("Ошибка при загрузке данных вагона", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long wagonId = Long.parseLong(req.getParameter("id"));
        Long trainId = Long.parseLong(req.getParameter("trainId"));

        Wagon wagon = new Wagon();
        wagon.setId(wagonId);
        wagon.setTrainId(trainId);
        wagon.setNumber(Integer.parseInt(req.getParameter("number")));
        wagon.setType(WagonType.valueOf(req.getParameter("type")));
        wagon.setSeatCount(wagon.getType().getSeatCount());

        try {
            wagonService.update(wagon);
            resp.sendRedirect(req.getContextPath() + "/wagons?trainId=" + trainId);
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка базы данных при обновлении вагона", e);
        }
    }
}