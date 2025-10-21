package com.vkenex.trainsmanagment.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private final String DB_URL = "db.url";
    private final String DB_USER = "db.user";
    private final String DB_PASSWORD = "db.password";

    public ConnectionManager() {}

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(PropertiesManager.getProperty(DB_URL),
                    PropertiesManager.getProperty(DB_USER),
                    PropertiesManager.getProperty(DB_PASSWORD));
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
