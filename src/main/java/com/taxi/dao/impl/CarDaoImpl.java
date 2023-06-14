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
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CarDaoImpl implements CarDao {
    private DbConnectionUtil connectionUtil;

    public CarDaoImpl(DbConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Transactional
    @Override
    public Car create(Car car) {
        String createStatement = "INSERT INTO cars(model,manufacturer_id) VALUES (?,?);";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(createStatement,
                         Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, (car.getModel()));
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Long recordId = resultSet.getObject(1, Long.class);
            car.setId(recordId);
            removeDriversForCar(car, connection);
            car.setDriverSet(updateDriversForCar(car, connection));
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new Car "
                    + car, e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        Car car = new Car();
        String getStatement = "SELECT * FROM cars c "
                + " JOIN manufacturers m on c.manufacturer_id = m.id"
                + " WHERE c.id = ? AND c.isDeleted = false";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getStatement)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car = getCar(resultSet);
            }
            car.setDriverSet(getDriverSet(car,connection));
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
                Car car = getCar(resultSet);
                car.setDriverSet(getDriverSet(car, connection));
                carList.add(car);
            }
            return carList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all Cars to the List !"
                    + carList.toString(), e);
        }
    }

    @Transactional
    @Override
    public Car update(Car car) {
        String updateStatement = "UPDATE cars SET model = ?, manufacturer_id = ?"
                + " WHERE id = ? AND isDeleted = false ;";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(updateStatement)) {
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setLong(2, car.getManufacturer().getId());
            preparedStatement.setLong(3, car.getId());
            preparedStatement.executeUpdate();
            removeDriversForCar(car, connection);
            car.setDriverSet(updateDriversForCar(car, connection));
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update Car "
                    + car, e);
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
                carList.add(getCar(resultSet));
            }
            return carList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all Cars to the List !"
                    + carList.toString(), e);
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        Manufacturer manufacturer = getManufacturer(resultSet);
        car.setId(resultSet.getObject("id", Long.class));
        car.setModel(resultSet.getString("model"));
        car.setDeleted(resultSet.getBoolean("isDeleted"));
        car.setManufacturer(manufacturer);
        return car;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("m.id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        manufacturer.setDeleted(resultSet.getBoolean("m.isDeleted"));
        return manufacturer;
    }

    private Driver getDriver(ResultSet resultSet) throws SQLException {
        Driver driver = new Driver();
        driver.setId(resultSet.getObject("id", Long.class));
        driver.setName(resultSet.getString("name"));
        driver.setLicenseNumber(resultSet.getString("licence"));
        driver.setDeleted(resultSet.getBoolean("isDeleted"));
        return driver;
    }

    private Set<Driver> updateDriversForCar(Car car, Connection connection) {
        Set<Driver> driverSet = car.getDriverSet();
        String addDriversString = "INSERT INTO cars_drivers(car_id, driver_id) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(addDriversString)) {
            for (Driver driver : driverSet) {
                preparedStatement.setLong(1, car.getId());
                preparedStatement.setLong(2, driver.getId());
                preparedStatement.execute();
            }
        } catch (SQLException exception) {
            throw new DataProcessingException("Can`t update Driver list for Car "
            + car, exception);
        }
        return driverSet;
    }

    private void removeDriversForCar(Car car, Connection connection) {
        String removeDriversString = "DELETE FROM cars_drivers WHERE car_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeDriversString)) {
            preparedStatement.setLong(1, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataProcessingException("Can`t remove Driver from list in Car "
                    + car, exception);
        }
    }

    private Set<Driver> getDriverSet(Car car, Connection connection) {
        Set<Driver> driverSet = new HashSet<>();
        String removeDriversString = "SELECT d.id, d.name, d.licence, d.isDeleted"
                + " FROM drivers  d"
                + " JOIN cars_drivers cd ON d.id = cd.driver_id "
                + " WHERE car_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeDriversString)) {
            preparedStatement.setLong(1, car.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                driverSet.add(getDriver(resultSet));
            }
        } catch (SQLException exception) {
            throw new DataProcessingException("Can`t get set of Drivers for the Car "
                    + car, exception);
        }
        return driverSet;
    }

}
