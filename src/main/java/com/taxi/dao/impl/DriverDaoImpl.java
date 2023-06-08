package com.taxi.dao.impl;

import com.taxi.dao.DriverDao;
import com.taxi.exception.DataProcessingException;
import com.taxi.model.Driver;
import com.taxi.utils.DbConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class DriverDaoImpl implements DriverDao {
    private DbConnectionUtil connectionUtil;

    public DriverDaoImpl(DbConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Driver create(Driver driver) {
        String createStatement = "INSERT INTO drivers(name,licence) VALUES (?,?);";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(createStatement,
                         Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenseNumber());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Long recordId = resultSet.getObject(1, Long.class);
            driver.setId(recordId);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new Driver "
                    + driver, e);
        }
        return driver;
    }

    @Override
    public Optional<Driver> get(Long id) {
        Driver driver = new Driver();
        String getStatement = "SELECT * FROM drivers WHERE id = ? AND isDeleted = false";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getStatement)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                driver = getDriver(resultSet);
            }
            return Optional.ofNullable(driver);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get Driver with id "
                    + id, e);
        }
    }

    @Override
    public List<Driver> getAll() {
        List<Driver> driverList = new ArrayList<>();
        String getAllStatement = "SELECT * FROM drivers WHERE isDeleted = false ";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(getAllStatement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                driverList.add(getDriver(resultSet));
            }
            return driverList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all Driver in the List !"
                    + driverList.toString(), e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        String updateStatement = "UPDATE drivers SET name = ?, licence = ?"
                + "WHERE id = ? AND isDeleted = false ;";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(updateStatement)) {
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenseNumber());
            preparedStatement.setLong(3, driver.getId());
            preparedStatement.executeUpdate();
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update Driver "
                    + driver, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE drivers SET isDeleted = true "
                + "WHERE id = ? AND isDeleted = false;";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t extract data for Driver with id "
                    + id, e);
        }
    }

    private Driver getDriver(ResultSet resultSet) throws SQLException {
        Driver driver = new Driver();
        driver.setId(resultSet.getObject("id", Long.class));
        driver.setName(resultSet.getString("name"));
        driver.setLicenseNumber(resultSet.getString("licence"));
        driver.setDeleted(resultSet.getBoolean("isDeleted"));
        return driver;
    }
}
