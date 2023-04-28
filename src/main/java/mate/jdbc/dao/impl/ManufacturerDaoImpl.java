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
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.services.DataBaseConnectionService;
import mate.jdbc.services.impl.DataBaseConnectionServiceImpl;
import mate.jdbc.exception.DataProcessingException;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private Connection connection = null;
    //private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private DataBaseConnectionService dbConnector;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        connection = dbConnector.getConnection();
        String createStatement = "INSERT INTO manufacturers(name,country) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createStatement,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Long recordId = resultSet.getLong(1);
            manufacturer.setId(recordId);
            connection.close();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t create new Manufacturer !"
                    + manufacturer.toString(), throwables);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manuf = new Manufacturer();
        connection = dbConnector.getConnection();
        String getStatement = "SELECT * FROM manufacturers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getStatement)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            manuf = getManufacturer(resultSet);
            connection.close();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get Manufacturer with id "
                    + id.toString(), throwables);
        }
        return Optional.of(manuf);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String getAllStatement = "SELECT * FROM manufacturers";
        connection = dbConnector.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(getAllStatement)) {
            resultSet = preparedStatement.executeQuery();
            while (!resultSet.isLast()) {
                manufacturerList.add(getManufacturer(resultSet));
            }
            connection.close();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get all Manufacturers in the List !"
                    + manufacturerList.toString(), throwables);
        }

        return manufacturerList;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateStatement = "UPDATE manufacturers SET name = ?, country = ?,"
                + " isDeleted = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setBoolean(3, manufacturer.isDeleted());
            preparedStatement.setLong(4, manufacturer.getId());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t update Manufacturer!"
                    + manufacturer.toString(), throwables);
        }
        return manufacturer;
    }

    @Override
    public boolean isDeleted(Long id) {
        boolean isDeleted = false;
        String query = "SELECT isDeleted FROM manufacturers WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            isDeleted = resultSet.getBoolean("isDeleted");
            connection.close();
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t extract data for Manufacturer with id "
                    + id.toString(), throwables);
        }
        return isDeleted;
    }

    private Manufacturer getManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getLong("id"));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                manufacturer.setDeleted(resultSet.getBoolean("isDeleted"));
            }
        } catch (SQLException throwables) {
            throw new DataProcessingException("Can`t get Manufacturer : "
                    + manufacturer.toString(), throwables);
        }
        return manufacturer;
    }

    public ManufacturerDaoImpl() {
        this.dbConnector = new DataBaseConnectionServiceImpl();
    }

}
