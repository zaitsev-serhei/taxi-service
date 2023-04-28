package mate.jdbc.services;

import java.sql.Connection;

public interface DataBaseConnectionService {
    Connection getConnection();
}
