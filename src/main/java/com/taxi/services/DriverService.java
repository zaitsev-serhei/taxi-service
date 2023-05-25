package com.taxi.services;

import com.taxi.model.Driver;
import java.util.List;

public interface DriverService {

    Driver create(Driver manufacturer);

    Driver get(Long id);

    List<Driver> getAll();

    Driver update(Driver manufacturer);

    boolean delete(Long id);
}
