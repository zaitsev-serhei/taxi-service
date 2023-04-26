package mate.jdbc.services;

import java.sql.Connection;

public interface DataBaseConnectionService {
    Connection connectToDataBase(String url,String userName,String password);
}
