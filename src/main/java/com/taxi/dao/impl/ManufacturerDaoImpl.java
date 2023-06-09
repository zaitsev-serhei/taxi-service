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
import org.springframework.stereotype.Repository;

@Repository
public class ManufacturerDaoImpl implements ManufacturerDao {
    private DbConnectionUtil connectionUtil;

    public ManufacturerDaoImpl(DbConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createStatement = "INSERT INTO manufacturers(name,country) VALUES (?,?);";
        Connection connection = connectionUtil.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(createStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            connection.commit();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Long recordId = resultSet.getObject(1, Long.class);
            manufacturer.setId(recordId);
            return manufacturer;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    System.out.println("Transaction in Create Manufacturer was rolled back ");
                    connection.rollback();
                } catch (SQLException exception) {
                    // TODO: 21.06.2023 add logs instead of printStackTrace
                    exception.printStackTrace();
                }
            }
            throw new DataProcessingException("Can`t update Manufacturer " + manufacturer, e);
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
    public Optional<Manufacturer> get(Long id) {
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
                    // TODO: 21.06.2023 add logs instead of sout
                    System.out.println("Transaction in Update Manufacturer was rolled back ");
                    connection.rollback();
                } catch (SQLException exception) {
                    // TODO: 21.06.2023 add logs instead of printStackTrace
                    exception.printStackTrace();
                }
            }
            throw new DataProcessingException("Can`t update Manufacturer "
                    + manufacturer, e);
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
