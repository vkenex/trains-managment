package com.vkenex.trainsmanagment.dao.impl;

import com.vkenex.trainsmanagment.dao.AbstractDAO;
import com.vkenex.trainsmanagment.entity.Train;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TrainDAO extends AbstractDAO<Long, Train> {
    private static final TrainDAO INSTANCE = new TrainDAO();

    private static final String CREATE = "INSERT INTO trains (id, number, departure_station_id, arrival_station_id, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "SELECT id, number, departure_station_id, arrival_station_id, departure_time, arrival_time FROM trains";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";
    private static final String UPDATE = "UPDATE trains SET number = ?, departure_station_id = ?, arrival_station_id = ?, departure_time = ?, arrival_time = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM trains WHERE id = ?";

    private TrainDAO() {}

    public static TrainDAO getInstance() {
        return INSTANCE;
    }

    @Override
    protected String getCreateQuery() { return CREATE; }

    @Override
    protected String getFindByIdQuery() { return FIND_BY_ID; }

    @Override
    protected String getFindAllQuery() { return FIND_ALL; }

    @Override
    protected String getUpdateQuery() { return UPDATE; }

    @Override
    protected String getDeleteQuery() { return DELETE; }

    @Override
    protected void saveStatement(PreparedStatement statement, Train train) throws SQLException {
        statement.setLong(1, train.getId());
        statement.setInt(2, train.getNumber());
        statement.setLong(3, train.getDepartureStationId());
        statement.setLong(4, train.getArrivalStationId());
        statement.setTimestamp(5, Timestamp.valueOf(train.getTimeOfDeparture()));
        statement.setTimestamp(6, Timestamp.valueOf(train.getTimeOfArrival()));
    }

    @Override
    protected void updateStatement(PreparedStatement statement, Train train) throws SQLException {
        statement.setInt(1, train.getNumber());
        statement.setLong(2, train.getDepartureStationId());
        statement.setLong(3, train.getArrivalStationId());
        statement.setTimestamp(4, Timestamp.valueOf(train.getTimeOfDeparture()));
        statement.setTimestamp(5, Timestamp.valueOf(train.getTimeOfArrival()));
        statement.setLong(6, train.getId());
    }

    @Override
    protected Train build(ResultSet resultSet) throws SQLException {
        return new Train(
                resultSet.getLong("id"),
                resultSet.getInt("number"),
                resultSet.getLong("departure_station_id"),
                resultSet.getLong("arrival_station_id"),
                resultSet.getTimestamp("departure_time").toLocalDateTime(),
                resultSet.getTimestamp("arrival_time").toLocalDateTime()
        );
    }
}