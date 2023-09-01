package com.taxi.dao.impl;

import com.taxi.dao.DriverDao;
import com.taxi.exception.DataProcessingException;
import com.taxi.model.Driver;
import com.taxi.model.Role;
import com.taxi.utils.DbConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class DriverDaoImpl implements DriverDao {
    private DbConnectionUtil connectionUtil;
    private Logger logger = LogManager.getLogger(DriverDaoImpl.class);

    public DriverDaoImpl(DbConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Driver create(Driver driver) {
        logger.info("Entered create(Driver driver)");
        String createStatement = "INSERT INTO drivers(name,licence,login,passw,seed) VALUES (?,?,?,?,?);";
        Connection connection = connectionUtil.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(createStatement,
                         Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenseNumber());
            preparedStatement.setString(3, driver.getLogin());
            preparedStatement.setString(4, driver.getPassword());
            preparedStatement.setBytes(5, driver.getHashSeed());
            preparedStatement.executeUpdate();
            addRolesForDriver(driver,connection);
            connection.commit();
            logger.info("Transaction completed in create(Driver driver)");
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Long recordId = resultSet.getObject(1, Long.class);
            driver.setId(recordId);
            return driver;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    logger.error("Transaction can`t be executed in create(Driver driver) with {}", driver, e);
                    connection.rollback();
                    logger.info("Transaction was rolled back");
                } catch (SQLException exception) {
                    logger.error("Can`t roll back transaction in create(Driver driver)", exception);
                }
            }
            throw new DataProcessingException("Can`t create Driver " + driver, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                logger.error("Can`t close connection in create(Driver driver)", exception);
            }
        }
    }

    private void addRolesForDriver(Driver driver, Connection connection) throws SQLException {
        String statement = "INSERT INTO users_roles VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setLong(1, driver.getId());
            for (Role role : driver.getRoles()) {
                preparedStatement.setLong(2, role.getRoleName().ordinal() + 1);
                preparedStatement.executeUpdate();
            }
        }
    }

    private void removeRolesForDriver(Driver driver, Connection connection) throws SQLException {
        String removeDriversString = "DELETE FROM users_roles WHERE user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(removeDriversString);
        preparedStatement.setLong(1, driver.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public Optional<Driver> get(Long id) {
        logger.info("Entered get(Long id) in DriverDaoImpl");
        Driver driver = new Driver();
        String getStatement = "SELECT d.id, d.name, d.licence, d.login, d.passw, d.seed, d.isDeleted, r.role "
                + "FROM (drivers d "
                + "LEFT JOIN users_roles ur on d.id = ur.user_id "
                + "LEFT JOIN roles r on ur.role_id = r.id) "
                + "WHERE d.id = ? AND isDeleted = false;";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getStatement)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                driver = getDriver(resultSet);
                driver.setRoles(getDriverRoles(driver,connection));
            }
            return Optional.ofNullable(driver);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get Driver with id "
                    + id, e);
        }
    }

    @Override
    public List<Driver> getAll() {
        logger.info("Entered getAll() in DriverDaoImpl");
        List<Driver> driverList = new ArrayList<>();
        String getAllStatement = "SELECT * FROM drivers WHERE isDeleted = false ";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(getAllStatement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Driver driver = getDriver(resultSet);
                driver.setRoles(getDriverRoles(driver, connection));
                driverList.add(driver);
            }
            return driverList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all Driver in the List !"
                    + driverList.toString(), e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        logger.info("Entered update(Driver driver)");
        String updateStatement = "UPDATE drivers "
                + "SET name = ?, licence = ?, login = ?, passw = ?, seed = ? "
                + "WHERE id = ? AND isDeleted = false ;";
        Connection connection = connectionUtil.getConnection();
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(updateStatement)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, driver.getName());
            preparedStatement.setString(2, driver.getLicenseNumber());
            preparedStatement.setString(3, driver.getLogin());
            preparedStatement.setString(4, driver.getPassword());
            preparedStatement.setBytes(5, driver.getHashSeed());
            preparedStatement.setLong(6, driver.getId());
            preparedStatement.executeUpdate();
            removeRolesForDriver(driver,connection);
            addRolesForDriver(driver,connection);
            connection.commit();
            logger.info("Transaction completed in update(Driver driver)");
            return driver;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    logger.error("Transaction can`t be executed in create(Car car) with {}", driver, e);
                    connection.rollback();
                    logger.info("Transaction was rolled back");
                } catch (SQLException exception) {
                    logger.error("Transaction can`t be rolled back in update(Driver driver)", exception);
                }
            }
            throw new DataProcessingException("Can`t update Driver "
                    + driver, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                logger.error("Connection can`t be closed in update(Driver driver)", exception);
            }
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Entered delete(Long id) in DriverDaoImpl");
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
        driver.setLogin(resultSet.getString("login"));
        driver.setPassword(resultSet.getString("passw"));
        driver.setHashSeed(resultSet.getBytes("seed"));
        driver.setDeleted(resultSet.getBoolean("isDeleted"));
        return driver;
    }

    private Set<Role> getDriverRoles(Driver driver, Connection connection) throws SQLException {
        Set<Role> roleSet = new HashSet<>();
        String getDriverRoles = "SELECT r.role "
                + "FROM roles r "
                + "JOIN users_roles ur ON ur.role_id = r.id "
                + "WHERE ur.user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(getDriverRoles);
        preparedStatement.setLong(1, driver.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            roleSet.add(Role.of(resultSet.getString("role")));
        }
        return roleSet;
    }
}
