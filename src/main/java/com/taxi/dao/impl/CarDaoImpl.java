package com.taxi.dao.impl;

import com.taxi.dao.CarDao;
import com.taxi.exception.DataProcessingException;
import com.taxi.model.Car;
import com.taxi.model.Driver;
import com.taxi.model.Manufacturer;
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
import org.springframework.stereotype.Repository;

@Repository
public class CarDaoImpl implements CarDao {
    private DbConnectionUtil connectionUtil;

    public CarDaoImpl(DbConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Car create(Car car) {
        Connection connection = connectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            createCar(car,connection);
            addDriversForCar(car, connection);
            connection.commit();
            return car;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    // TODO: 21.06.2023 add logs instead of console output
                    connection.rollback();
                } catch (SQLException exception) {
                    // TODO: 21.06.2023 add logs instead of printStackTrace
                    exception.printStackTrace();
                }
            }
            throw new DataProcessingException("Can`t update Car "
                    + car, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                // TODO: 21.06.2023 add logs instead of printStackTrace
                exception.printStackTrace();
            }
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        Car car = null;
        String getStatement = "SELECT * FROM cars c "
                + " JOIN manufacturers m on c.manufacturer_id = m.id"
                + " WHERE c.id = ? AND c.isDeleted = false";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getStatement)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = parseCar(resultSet);
                car.setDrivers(getDrivers(car, connection));
            }
            return Optional.ofNullable(car);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get Car with id "
                    + id, e);
        }
    }

    @Override
    public List<Car> getAll() {
        List<Car> carList = new ArrayList<>();
        String getAllStatement = "SELECT * FROM cars c "
                + " JOIN manufacturers m on c.manufacturer_id = m.id"
                + " WHERE c.isDeleted = FALSE ";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(getAllStatement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car car = parseCar(resultSet);
                car.setDrivers(getDrivers(car, connection));
                carList.add(car);
            }
            return carList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all Cars to the List !"
                    + carList.toString(), e);
        }
    }

    @Override
    public Car update(Car car) {
        Connection connection = connectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            updateCar(car, connection);
            removeDriversForCar(car, connection);
            addDriversForCar(car, connection);
            connection.commit();
            return car;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    // TODO: 21.06.2023 add logs instead of printStackTrace
                    connection.rollback();
                } catch (SQLException exception) {
                    // TODO: 21.06.2023 add logs instead of printStackTrace
                    exception.printStackTrace();
                }
            }
            throw new DataProcessingException("Can`t update Car "
                    + car, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                // TODO: 21.06.2023 add logs instead of printStackTrace
                exception.printStackTrace();
            }
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE cars SET isDeleted = true "
                + "WHERE id = ?;";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t extract data for Car with id "
                    + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        List<Car> carList = new ArrayList<>();
        String getAllStatement = "SELECT c.id, c.model, c.manufacturer_id, c.isDeleted,"
                + " m.id, m.name, m.country, m.isDeleted"
                + " FROM (cars c JOIN cars_drivers cd ON c.id = cd.car_id"
                + " JOIN manufacturers m on c.manufacturer_id = m.id)"
                + " WHERE cd.driver_id = ? ";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(getAllStatement)) {
            preparedStatement.setLong(1, driverId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car car = parseCar(resultSet);
                car.setDrivers(getDrivers(car, connection));
                carList.add(car);
            }
            return carList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all Cars to the List !"
                    + carList.toString(), e);
        }
    }

    private Car parseCar(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        Manufacturer manufacturer = parseManufacturer(resultSet);
        car.setId(resultSet.getObject("id", Long.class));
        car.setModel(resultSet.getString("model"));
        car.setDeleted(resultSet.getBoolean("isDeleted"));
        car.setManufacturer(manufacturer);
        return car;
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("m.id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        manufacturer.setDeleted(resultSet.getBoolean("m.isDeleted"));
        return manufacturer;
    }

    private Driver parseDriver(ResultSet resultSet) throws SQLException {
        Driver driver = new Driver();
        driver.setId(resultSet.getObject("id", Long.class));
        driver.setName(resultSet.getString("name"));
        driver.setLicenseNumber(resultSet.getString("licence"));
        driver.setDeleted(resultSet.getBoolean("isDeleted"));
        return driver;
    }

    private void createCar(Car car, Connection connection) throws SQLException {
        String createStatement = "INSERT INTO cars(model,manufacturer_id) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(createStatement,
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, (car.getModel()));
        preparedStatement.setLong(2, car.getManufacturer().getId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Long recordId = resultSet.getObject(1, Long.class);
        car.setId(recordId);
    }

    private void updateCar(Car car, Connection connection) throws SQLException {
        String updateStatement = "UPDATE cars SET model = ?, manufacturer_id = ?"
                + " WHERE id = ? AND isDeleted = false ;";
        PreparedStatement preparedStatement =
                connection.prepareStatement(updateStatement);
        preparedStatement.setString(1, car.getModel());
        preparedStatement.setLong(2, car.getManufacturer().getId());
        preparedStatement.setLong(3, car.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private void addDriversForCar(Car car, Connection connection) throws SQLException {
        Set<Driver> driverSet = car.getDrivers();
        String addDriversString = "INSERT INTO cars_drivers(car_id, driver_id) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(addDriversString);
        for (Driver driver : driverSet) {
            preparedStatement.setLong(1, car.getId());
            preparedStatement.setLong(2, driver.getId());
            preparedStatement.execute();
        }
    }

    private void removeDriversForCar(Car car, Connection connection) throws SQLException {
        String removeDriversString = "DELETE FROM cars_drivers WHERE car_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(removeDriversString);
        preparedStatement.setLong(1, car.getId());
        preparedStatement.executeUpdate();
    }

    private Set<Driver> getDrivers(Car car, Connection connection) throws SQLException {
        Set<Driver> driverSet = new HashSet<>();
        String getDriversString = "SELECT d.id, d.name, d.licence, d.isDeleted"
                + " FROM drivers  d"
                + " JOIN cars_drivers cd ON d.id = cd.driver_id "
                + " WHERE car_id = ? AND isDeleted = FALSE";
        PreparedStatement preparedStatement = connection.prepareStatement(getDriversString);
        preparedStatement.setLong(1, car.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            driverSet.add(parseDriver(resultSet));
        }
        return driverSet;
    }
}
