package com.vkenex.trainsmanagment.service;

import com.vkenex.trainsmanagment.dao.impl.StationDAO;
import com.vkenex.trainsmanagment.dao.impl.TrainDAO;
import com.vkenex.trainsmanagment.dao.impl.WagonDAO;
import com.vkenex.trainsmanagment.dto.TrainDto;
import com.vkenex.trainsmanagment.entity.Station;
import com.vkenex.trainsmanagment.entity.Train;
import com.vkenex.trainsmanagment.entity.Wagon;
import com.vkenex.trainsmanagment.exception.ValidationException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrainService {
    private static final TrainService INSTANCE = new TrainService();
    private final TrainDAO trainDAO = TrainDAO.getInstance();
    private final WagonDAO wagonDAO = WagonDAO.getInstance();
    private final StationDAO stationDAO = StationDAO.getInstance();

    private TrainService() {}

    public static TrainService getInstance() {
        return INSTANCE;
    }

    public List<TrainDto> findAllTrains() throws SQLException {
        List<Train> trains = trainDAO.findAll();
        Map<Long, Station> stationMap = stationDAO.findAll().stream()
                .collect(Collectors.toMap(Station::getId, station -> station));

        List<TrainDto> result = new ArrayList<>();
        for (Train train : trains) {
            String departureName = stationMap.get(train.getDepartureStationId()).getName();
            String destinationName = stationMap.get(train.getArrivalStationId()).getName();

            result.add(new TrainDto(
                    train.getId(),
                    train.getNumber(),
                    departureName,
                    destinationName,
                    train.getTimeOfDeparture(),
                    train.getTimeOfArrival(),
                    train.getTravelTime()
            ));
        }

        return result;
    }

    public Optional<TrainDto> findTrainById(Long id) throws SQLException {
        Optional<Train> trainOptional = trainDAO.findById(id);
        if (trainOptional.isEmpty()) {
            return Optional.empty();
        }

        Train train = trainOptional.get();
        Optional<Station> departureStation = stationDAO.findById(train.getDepartureStationId());
        Optional<Station> arrivalStation = stationDAO.findById(train.getArrivalStationId());

        TrainDto dto = new TrainDto(
                train.getId(),
                train.getNumber(),
                departureStation.map(Station::getName).orElse("Неизвестно"),
                arrivalStation.map(Station::getName).orElse("Неизвестно"),
                train.getTimeOfDeparture(),
                train.getTimeOfArrival(),
                train.getTravelTime()
        );
        return Optional.of(dto);
    }

    public void createTrainWithWagons(Train train, List<Wagon> wagons) throws SQLException, ValidationException {
        createTrain(train);

        for (Wagon wagon : wagons) {
            wagon.setTrainId(train.getId());
            wagonDAO.save(wagon);
        }
    }

    public void createTrain(Train train) throws SQLException, ValidationException {
        if (train.getTimeOfArrival().isBefore(train.getTimeOfDeparture())) {
            throw new ValidationException("Время прибытия не может быть раньше времени отправления.");
        }
        trainDAO.save(train);
    }


    public void updateTrain(Train train) throws SQLException, ValidationException {
        if (train.getTimeOfArrival().isBefore(train.getTimeOfDeparture())) {
            throw new ValidationException("Время прибытия не может быть раньше времени отправления.");
        }
        trainDAO.update(train);
    }

    public boolean deleteTrain(Long id) throws SQLException {
        return trainDAO.delete(id);
    }

}