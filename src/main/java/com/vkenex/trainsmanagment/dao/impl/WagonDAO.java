package com.vkenex.trainsmanagment.dao.impl;

import com.vkenex.trainsmanagment.dao.AbstractDAO;
import com.vkenex.trainsmanagment.entity.Wagon;
import com.vkenex.trainsmanagment.entity.enums.WagonType;
import com.vkenex.trainsmanagment.utils.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WagonDAO extends AbstractDAO<Long, Wagon> {
    private static final WagonDAO INSTANCE = new WagonDAO();

    private static final String CREATE = "INSERT INTO wagons (id, wagon_number, wagon_type, seat_count, train_id) VALUES (?, ?, ?::wagon_type_enum, ?, ?)";
    private static final String FIND_ALL = "SELECT id, wagon_number, wagon_type, seat_count, train_id FROM wagons";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";
    private static final String FIND_BY_TRAIN_ID = FIND_ALL + " WHERE train_id = ?";
    private static final String UPDATE = "UPDATE wagons SET wagon_number = ?, wagon_type = ?::wagon_type_enum, seat_count = ?, train_id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM wagons WHERE id = ?";

    private WagonDAO() {}

    public static WagonDAO getInstance() { return INSTANCE; }

    // Реализация уникального для WagonDAO метода
    public List<Wagon> findAllByTrainId(Long trainId) throws SQLException {
        List<Wagon> wagons = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_TRAIN_ID)) {
            preparedStatement.setLong(1, trainId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                wagons.add(build(resultSet));
            }
        }
        return wagons;
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
    protected void saveStatement(PreparedStatement statement, Wagon wagon) throws SQLException {
        statement.setLong(1, wagon.getId());
        statement.setInt(2, wagon.getNumber());
        statement.setString(3, wagon.getType().name());
        statement.setInt(4, wagon.getSeatCount());
        statement.setLong(5, wagon.getTrainId());
    }

    @Override
    protected void updateStatement(PreparedStatement statement, Wagon wagon) throws SQLException {
        statement.setInt(1, wagon.getNumber());
        statement.setString(2, wagon.getType().name());
        statement.setInt(3, wagon.getSeatCount());
        statement.setLong(4, wagon.getTrainId());
        statement.setLong(5, wagon.getId());
    }

    @Override
    protected Wagon build(ResultSet resultSet) throws SQLException {
        return new Wagon(
                resultSet.getLong("id"),
                resultSet.getInt("wagon_number"),
                WagonType.valueOf(resultSet.getString("wagon_type")),
                resultSet.getInt("seat_count"),
                resultSet.getLong("train_id")
        );
    }
}