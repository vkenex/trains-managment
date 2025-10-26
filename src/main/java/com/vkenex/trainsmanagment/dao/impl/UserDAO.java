package com.vkenex.trainsmanagment.dao.impl;

import com.vkenex.trainsmanagment.dao.AbstractDAO;
import com.vkenex.trainsmanagment.entity.User;
import com.vkenex.trainsmanagment.entity.enums.UserRole;
import com.vkenex.trainsmanagment.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDAO extends AbstractDAO<Long, User> {
    private static final UserDAO INSTANCE = new UserDAO();

    private static final String CREATE = "INSERT INTO users (id, first_name, last_name, login, password_hash, role) VALUES (?, ?, ?, ?, ?, ?::user_role_enum)";
    private static final String FIND_ALL = "SELECT id, first_name, last_name, login, password_hash, role FROM users";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE id = ?";
    private static final String FIND_BY_LOGIN = FIND_ALL + " WHERE login = ?";
    private static final String UPDATE = "UPDATE users SET first_name = ?, last_name = ?, login = ?, password_hash = ?, role = ?::user_role_enum WHERE id = ?";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";

    private UserDAO() {}

    public static UserDAO getInstance() {
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

    public Optional<User> findByLogin(String login) throws SQLException {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN)) {
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Optional.of(build(resultSet)) : Optional.empty();
        }
    }

    @Override
    protected void saveStatement(PreparedStatement statement, User user) throws SQLException {
        statement.setLong(1, user.getId());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getLogin());
        statement.setString(5, user.getPasswordHash());
        statement.setString(6, user.getRole().name());
    }

    @Override
    protected void updateStatement(PreparedStatement statement, User user) throws SQLException {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getLogin());
        statement.setString(4, user.getPasswordHash());
        statement.setString(5, user.getRole().name());
        statement.setLong(6, user.getId());
    }

    @Override
    protected User build(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("login"),
                resultSet.getString("password_hash"),
                UserRole.valueOf(resultSet.getString("role"))
        );
    }
}