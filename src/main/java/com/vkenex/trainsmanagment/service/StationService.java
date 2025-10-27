package com.vkenex.trainsmanagment.service;

import com.vkenex.trainsmanagment.dao.impl.StationDAO;
import com.vkenex.trainsmanagment.entity.Station;
import java.sql.SQLException;
import java.util.List;

public class StationService {
    private static final StationService INSTANCE = new StationService();
    private final StationDAO stationDAO = StationDAO.getInstance();

    private StationService() {}

    public static StationService getInstance() {
        return INSTANCE;
    }

    public List<Station> findAll() throws SQLException {
        return stationDAO.findAll();
    }
}