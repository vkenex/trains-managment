package com.vkenex.trainsmanagment.dao.impl;

import com.vkenex.trainsmanagment.dao.AbstractDAO;
import com.vkenex.trainsmanagment.entity.Station;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StationDAO extends AbstractDAO<Long, Station> {
    private static final StationDAO INSTANCE = new StationDAO();

    private static final String CREATE = "INSERT INTO stations (id, name) VALUES (?, ?)";
    private static final String FIND_ALL = "SELECT id, name FROM stations";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";
    private static final String UPDATE = "UPDATE stations SET name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM stations WHERE id = ?";

    private StationDAO() {}

    public static StationDAO getInstance() {
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
    protected void saveStatement(PreparedStatement statement, Station station) throws SQLException {
        statement.setLong(1, station.getId());
        statement.setString(2, station.getName());
    }

    @Override
    protected void updateStatement(PreparedStatement statement, Station station) throws SQLException {
        statement.setString(1, station.getName());
        statement.setLong(2, station.getId());
    }

    @Override
    protected Station build(ResultSet resultSet) throws SQLException {
        return new Station(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }
}