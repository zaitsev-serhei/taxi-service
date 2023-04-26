package mate.jdbc.services.impl;

import mate.jdbc.lib.Dao;
import mate.jdbc.services.DataBaseConnectionService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Dao
public class DataBaseConnectionServiceImpl implements DataBaseConnectionService {
    Connection connection = null;
    @Override
    public Connection connectToDataBase(String url, String userName, String password) {
        try {
            this.connection = DriverManager.getConnection(url,userName,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
