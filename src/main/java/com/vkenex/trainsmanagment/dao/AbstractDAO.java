package com.vkenex.trainsmanagment.dao;

import com.vkenex.trainsmanagment.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<K, T> implements DAO<K, T> {

    protected abstract String getCreateQuery();

    protected abstract String getFindByIdQuery();

    protected abstract String getFindAllQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    protected abstract T build(ResultSet resultSet) throws SQLException;

    protected abstract void saveStatement(PreparedStatement statement, T entity) throws SQLException;

    protected abstract void updateStatement(PreparedStatement statement, T entity) throws SQLException;


    @Override
    public T save(T entity) throws SQLException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(getCreateQuery())) {
            saveStatement(preparedStatement, entity);
            preparedStatement.executeUpdate();
            return entity;
        }
    }

    @Override
    public Optional<T> findById(K id) throws SQLException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(getFindByIdQuery())) {
            preparedStatement.setObject(1, id); // setObject универсален для разных типов ключей
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Optional.of(build(resultSet)) : Optional.empty();
        }
    }

    @Override
    public List<T> findAll() throws SQLException {
        List<T> entities = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(getFindAllQuery())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                entities.add(build(resultSet));
            }
        }
        return entities;
    }

    @Override
    public void update(T entity) throws SQLException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(getUpdateQuery())) {
            updateStatement(preparedStatement, entity);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public boolean delete(K id) throws SQLException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(getDeleteQuery())) {
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }
}