package com.taxi.service.impl;

import com.taxi.dao.DriverDao;
import com.taxi.model.Driver;
import com.taxi.service.DriverService;
import java.util.List;

import com.taxi.utils.HashUtil;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {
    private DriverDao driverDao;

    public DriverServiceImpl(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Override
    public Driver create(Driver driver) {
        byte[] seed = HashUtil.getSalt();
        String hashPassword = HashUtil.hashPassword(driver.getPassword(), seed);
        driver.setPassword(hashPassword);
        driver.setHashSeed(seed);
        return driverDao.create(driver);
    }

    @Override
    public Driver get(Long id) {
        return driverDao.get(id).get();
    }

    @Override
    public List<Driver> getAll() {
        return driverDao.getAll();
    }

    @Override
    public Driver update(Driver driver) {
        return driverDao.update(driver);
    }

    @Override
    public boolean delete(Long id) {
        return driverDao.delete(id);
    }

}
