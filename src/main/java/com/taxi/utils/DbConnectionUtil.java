package com.taxi.utils;

import com.taxi.exception.DataProcessingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DbConnectionUtil {
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(dbUrl, userName, dbPassword);
            return connection;
        } catch (SQLException exception) {
            throw new DataProcessingException("Can`t connect to data base with credentials!"
                    + dbUrl + userName + dbPassword, exception);
        }
    }
}
