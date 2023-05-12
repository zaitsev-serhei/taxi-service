package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.utils.DbConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createStatement = "INSERT INTO manufacturers(name,country) VALUES (?,?);";
        try (Connection connection = DbConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(createStatement,
                         Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Long recordId = resultSet.getObject(1, Long.class);
            manufacturer.setId(recordId);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new Manufacturer "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = new Manufacturer();
        String getStatement = "SELECT * FROM manufacturers WHERE id = ? AND isDeleted = false";
        try (Connection connection = DbConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(getStatement)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
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
        try (Connection connection = DbConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(getAllStatement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                manufacturerList.add(getManufacturer(resultSet));
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
        try (Connection connection = DbConnectionUtil.getConnection();
                 PreparedStatement preparedStatement =
                         connection.prepareStatement(updateStatement)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            preparedStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update Manufacturer "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET isDeleted = true "
                + "WHERE id = ? AND isDeleted = false;";
        try (Connection connection = DbConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t extract data for Manufacturer with id "
                    + id, e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        manufacturer.setDeleted(resultSet.getBoolean("isDeleted"));
        return manufacturer;
    }
}
