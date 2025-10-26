package com.vkenex.trainsmanagment.service;

import com.vkenex.trainsmanagment.exception.ValidationException;
import com.vkenex.trainsmanagment.utils.PasswordEncoder;
import com.vkenex.trainsmanagment.dao.impl.UserDAO;
import com.vkenex.trainsmanagment.entity.User;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDAO userDAO = UserDAO.getInstance();

    private UserService() {}

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<User> login(String login, String password) throws SQLException {
        Optional<User> userOptional = userDAO.findByLogin(login);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (PasswordEncoder.matches(password, userOptional.get().getPasswordHash())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public List<User> findAll() throws SQLException {
        return userDAO.findAll();
    }

    public User createUser(User user) throws SQLException, ValidationException {
        if (userDAO.findByLogin(user.getLogin()).isPresent()) {
            throw new ValidationException("Пользователь с логином '" + user.getLogin() + "' уже существует.");
        }

        String hashedPassword = PasswordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        return userDAO.save(user);
    }

    public void updateUser(User user) throws SQLException {
        userDAO.update(user);
    }

    public boolean deleteUser(Long id) throws SQLException {
        return userDAO.delete(id);
    }
}