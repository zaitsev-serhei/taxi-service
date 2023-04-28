package mate.jdbc.services.impl;

import mate.jdbc.lib.Dao;
import mate.jdbc.services.DataBaseConnectionService;
import mate.jdbc.sqlException.DataProcessingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Dao
public class DataBaseConnectionServiceImpl implements DataBaseConnectionService {
    Connection connection = null;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taxi_application";
    private static final String USER_NAME = "root";
    private static final String DB_PASSWORD = "15975324865Germes!S";

    @Override
    public Connection getConnection() {
        try {
            this.connection = DriverManager.getConnection(DB_URL,USER_NAME,DB_PASSWORD);
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t connect to data base with present credentials!" +
                    "URL = " + DB_URL + System.lineSeparator() +
                    "Login = " + USER_NAME + System.lineSeparator(),throwables);
        }
        return connection;
    }
}
