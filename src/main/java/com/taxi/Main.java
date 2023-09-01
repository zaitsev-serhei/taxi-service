package com.taxi;

import com.taxi.config.AppConfig;
import com.taxi.model.Car;
import com.taxi.model.Driver;
import com.taxi.model.Manufacturer;
import com.taxi.service.CarService;
import com.taxi.service.DriverService;
import com.taxi.service.ManufacturerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        ManufacturerService manufacturerService = context.getBean(ManufacturerService.class);
       // Manufacturer manufacturer = manufacturerService.get(6L);
        //System.out.println(manufacturerService.create(manufacturer));
        //System.out.println(manufacturerService.get(5L));
        //System.out.println(manufacturerService.getAll());
        //System.out.println(manufacturerService.delete(10L));
        //manufacturer.setDeleted(true);
        //manufacturer.setId(5);
        //System.out.println(manufacturer);
        //manufacturerService.update(manufacturer);
        DriverService driverService = context.getBean(DriverService.class);
        //System.out.println(driverService.get(3L));
        //System.out.println(driverService.getAll());
        //System.out.println(driverService.delete(4L));
        //Driver driverToUpdate = driverService.get(1L);
        //System.out.println(driverToUpdate);
        //System.out.println(driverService.getAll());
        //driverToUpdate.setId(4L);
        //System.out.println(driverService.update(driverToUpdate));
        CarService carService = context.getBean(CarService.class);
        //Car testCar = carService.get(4L);
        //Car carToCreate = new Car("NTM", manufacturer);
        //System.out.println(carService.create(carToCreate));
        //testCar.setModel("MR2");
        //System.out.println(carService.update(testCar));
        //System.out.println(carService.get(testCar.getId()));
        //carService.addDriverToCar(driverToUpdate,testCar);
        //System.out.println(testCar);
        //System.out.println(carService.getAllByDriver(3L));
        //System.out.println(carService.get(testCar.getId()));
        //testCar.setModel("4555");
        //System.out.println(carService.get(testCar.getId()));
        //carService.removeDriverFromCar(driverToUpdate,testCar);
        //System.out.println(carService.get(testCar.getId()));
        System.out.println(carService.getAll());
    }
}
