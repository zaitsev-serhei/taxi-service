package com.taxi.dao.impl;

import com.taxi.dao.ManufacturerDao;
import com.taxi.exception.DataProcessingException;
import com.taxi.model.Manufacturer;
import com.taxi.utils.DbConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class ManufacturerDaoImpl implements ManufacturerDao {
    private DbConnectionUtil connectionUtil;
    private Logger logger = LogManager.getLogger(ManufacturerDaoImpl.class);

    public ManufacturerDaoImpl(DbConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        logger.info("Entered create(Manufacturer manufacturer)");
        String createStatement = "INSERT INTO manufacturers(name,country) VALUES (?,?);";
        Connection connection = connectionUtil.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(createStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            connection.commit();
            logger.info("Transaction completed in create(Manufacturer manufacturer)");
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Long recordId = resultSet.getObject(1, Long.class);
            manufacturer.setId(recordId);
            return manufacturer;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    logger.error("Transaction can`t be executed in create(Manufacturer manufacturer) with {}",
                            manufacturer);
                    connection.rollback();
                    logger.info("Transaction was rolled back");
                } catch (SQLException exception) {
                    logger.error("Can`t roll back transaction in create(Manufacturer manufacturer)");
                }
            }
            throw new DataProcessingException("Can`t update Manufacturer " + manufacturer, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                logger.error("Can`t close connection in create(Manufacturer manufacturer)");
            }
        }

    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        logger.info("Entered get(Long id) in ManufacturerDaoImpl");
        Manufacturer manufacturer = new Manufacturer();
        String getStatement = "SELECT * FROM manufacturers WHERE id = ? AND isDeleted = false";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getStatement)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = parseManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get Manufacturer with id "
                    + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        logger.info("Entered getAll() in ManufacturerDaoImpl");
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String getAllStatement = "SELECT * FROM manufacturers WHERE isDeleted = false ";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(getAllStatement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(parseManufacturer(resultSet));
            }
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all Manufacturers in the List !"
                    + manufacturerList.toString(), e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        logger.info("Entered update(Manufacturer manufacturer)");
        String updateStatement = "UPDATE manufacturers SET name = ?, country = ?"
                + "WHERE id = ? AND isDeleted = false ;";
        Connection connection = connectionUtil.getConnection();
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(updateStatement)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
            connection.commit();
            return manufacturer;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    logger.error("Transaction can`t be executed in update(Manufacturer manufacturer) with {}",
                            manufacturer);
                    connection.rollback();
                    logger.info("Transaction was rolled back");
                } catch (SQLException exception) {
                    logger.error("Can`t roll back transaction in update(Manufacturer manufacturer)");
                }
            }
            throw new DataProcessingException("Can`t update Manufacturer "
                    + manufacturer, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException exception) {
                logger.error("Connection can`t be closed in update(Driver driver)");
            }
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Entered delete(Long id)");
        String query = "UPDATE manufacturers SET isDeleted = true "
                + "WHERE id = ? AND isDeleted = false;";
        try (Connection connection = connectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t extract data for Manufacturer with id "
                    + id, e);
        }
    }

    private Manufacturer parseManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        manufacturer.setDeleted(resultSet.getBoolean("isDeleted"));
        return manufacturer;
    }
}
