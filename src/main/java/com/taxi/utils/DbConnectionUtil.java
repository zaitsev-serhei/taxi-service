package com.taxi.utils;

import com.taxi.exception.DataProcessingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_application";
    private static final String USER_NAME = "root";
    private static final String DB_PASSWORD = "15975324865Germes!S";

    public static Connection getConnection() {
        Properties properties = new Properties();
        try {
            Connection connection = DriverManager.getConnection(DB_URL,USER_NAME,DB_PASSWORD);
            return connection;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can`t connect to data base with credentials!", exception);
        }
    }
}
