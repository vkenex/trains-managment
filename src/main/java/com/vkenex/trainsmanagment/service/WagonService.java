package com.vkenex.trainsmanagment.service;

import com.vkenex.trainsmanagment.dao.impl.WagonDAO;
import com.vkenex.trainsmanagment.entity.Wagon;
import com.vkenex.trainsmanagment.exception.ValidationException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class WagonService {
    private static final WagonService INSTANCE = new WagonService();
    private final WagonDAO wagonDAO = WagonDAO.getInstance();

    private WagonService() {}

    public static WagonService getInstance() {
        return INSTANCE;
    }

    public List<Wagon> findAllByTrainId(Long trainId) throws SQLException {
        return wagonDAO.findAllByTrainId(trainId);
    }

    public Optional<Wagon> findById(Long id) throws SQLException {
        return wagonDAO.findById(id);
    }

    public Wagon add(Wagon wagon) throws SQLException, ValidationException {
        Optional<Wagon> existingWagon = findAllByTrainId(wagon.getTrainId()).stream()
                .filter(w -> w.getNumber() == wagon.getNumber())
                .findFirst();

        if (existingWagon.isPresent()) {
            throw new ValidationException("Вагон с номером " + wagon.getNumber() + " уже существует в этом поезде.");
        }

        return wagonDAO.save(wagon);
    }

    public void update(Wagon wagon) throws SQLException, ValidationException {
        Optional<Wagon> conflictingWagon = findAllByTrainId(wagon.getTrainId()).stream()
                .filter(w -> w.getNumber() == wagon.getNumber() && !w.getId().equals(wagon.getId()))
                .findFirst();

        if (conflictingWagon.isPresent()) {
            throw new ValidationException("Вагон с номером " + wagon.getNumber() + " уже существует в этом поезде.");
        }

        wagonDAO.update(wagon);
    }

    public boolean delete(Long id) throws SQLException {
        return wagonDAO.delete(id);
    }

}