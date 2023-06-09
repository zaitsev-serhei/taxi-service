package com.taxi.model;

import java.util.HashSet;
import java.util.Set;

public class Car {
    private Long id;
    private String model;
    private Manufacturer manufacturer;
    private Set<Driver> driverSet;
    private Boolean isDeleted;

    public Car() {
        this.driverSet = new HashSet<>();
    }

    public Car(String model, Manufacturer manufacturer) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.driverSet = new HashSet<>();
    }

    public Set<Driver> getDrivers() {
        return driverSet;
    }

    public void setDrivers(Set<Driver> driverSet) {
        this.driverSet = new HashSet<>(driverSet);
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Car{"
                + "id=" + id + ", model ='" + model + '\''
                + ", manufacturer =" + manufacturer
                + ", drivers = " + driverSet
                + '}';

    }
}
