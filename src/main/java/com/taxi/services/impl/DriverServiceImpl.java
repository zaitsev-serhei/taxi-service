package com.taxi.services.impl;

import com.taxi.dao.DriverDao;
import com.taxi.model.Driver;
import com.taxi.services.DriverService;
import java.util.List;

public class DriverServiceImpl implements DriverService {
    private DriverDao driverDao;

    public DriverServiceImpl(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Override
    public Driver create(Driver manufacturer) {
        return driverDao.create(manufacturer);
    }

    @Override
    public Driver get(Long id) {
        return driverDao.get(id).isPresent() ? driverDao.get(id).get() : null;
    }

    @Override
    public List<Driver> getAll() {
        return driverDao.getAll();
    }

    @Override
    public Driver update(Driver manufacturer) {
        return driverDao.update(manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        return driverDao.delete(id);
    }

}
