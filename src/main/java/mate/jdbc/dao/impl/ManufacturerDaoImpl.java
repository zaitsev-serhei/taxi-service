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
import mate.jdbc.services.impl.DataBaseConnectionServiceImpl;
import mate.jdbc.exception.DataProcessingException;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createStatement = "INSERT INTO manufacturers(name,country) VALUES (?,?)";
        try (Connection connection = new DataBaseConnectionServiceImpl().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createStatement,
                Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Long recordId = resultSet.getLong(1);
            manufacturer.setId(recordId);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new Manufacturer "
                    + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manuf = new Manufacturer();
        String getStatement = "SELECT * FROM manufacturers WHERE id = ? AND isDeleted = false";
        try (Connection connection = new DataBaseConnectionServiceImpl().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getStatement)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                manuf = getManufacturer(resultSet);
                return Optional.of(manuf);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get Manufacturer with id "
                    + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        String getAllStatement = "SELECT * FROM manufacturers WHERE isDeleted = false ";
        try (Connection connection = new DataBaseConnectionServiceImpl().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllStatement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (!resultSet.isLast()) {
                manufacturerList.add(getManufacturer(resultSet));
            }
            connection.close();
            return manufacturerList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all Manufacturers in the List !"
                    + manufacturerList.toString(), e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateStatement = "UPDATE manufacturers SET name = ?, country = ?"
                + "WHERE id = ? AND isDeleted = false ";
        try (Connection connection = new DataBaseConnectionServiceImpl().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
            preparedStatement.setString(1, manufacturer.getName());
            preparedStatement.setString(2, manufacturer.getCountry());
            preparedStatement.setLong(3, manufacturer.getId());
            if (preparedStatement.executeUpdate() > 0){
                connection.close();
                return manufacturer;
            } else {
                connection.close();
                throw new DataProcessingException("Can`t update rows for Manufacturer"
                        + manufacturer, null);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update Manufacturer "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET isDeleted = true WHERE id = ? AND isDeleted = false";
        try (Connection connection = new DataBaseConnectionServiceImpl().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            if(preparedStatement.executeUpdate() > 0) {
                connection.close();
                return true;
            } else {
                connection.close();
                throw new RuntimeException("Can`t delete the Manufacturer with id =" + id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t extract data for Manufacturer with id "
                    + id, e);
        }

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
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get Manufacturer : "
                    + manufacturer, e);
        }
        return manufacturer;
    }

}
