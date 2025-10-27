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

@WebServlet("/wagons/add")
public class WagonAddServlet extends HttpServlet {

    private final WagonService wagonService = WagonService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("trainId", req.getParameter("trainId"));
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/wagon-add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long trainId = Long.parseLong(req.getParameter("trainId"));

        Wagon wagon = new Wagon();
        wagon.setTrainId(trainId);
        wagon.setNumber(Integer.parseInt(req.getParameter("number")));
        wagon.setType(WagonType.valueOf(req.getParameter("type")));
        wagon.setSeatCount(wagon.getType().getSeatCount());

        try {
            wagonService.add(wagon);
            resp.sendRedirect(req.getContextPath() + "/wagons?trainId=" + trainId);
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Ошибка базы данных при добавлении вагона", e);
        }
    }
}