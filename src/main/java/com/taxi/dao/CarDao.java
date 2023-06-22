package com.taxi.dao;

import com.taxi.model.Car;
import java.util.List;
import java.util.Optional;

public interface CarDao {
    Car create(Car driver);

    Optional<Car> get(Long id);

    List<Car> getAll();

    Car update(Car driver);

    boolean delete(Long id);

    List<Car> getAllByDriver(Long driverId);
}
