package com.taxi;

import com.taxi.config.AppConfig;
import com.taxi.model.Driver;
import com.taxi.model.Manufacturer;
import com.taxi.services.DriverService;
import com.taxi.services.ManufacturerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        ManufacturerService manufacturerService = context.getBean(ManufacturerService.class);
        Manufacturer test = new Manufacturer("CreateTest","Ukraine");
        System.out.println(manufacturerService.create(test));
        manufacturerService.get(5L);
        System.out.println(manufacturerService.getAll());
        System.out.println(manufacturerService.delete(10L));
        //test.setDeleted(true);
        test.setId(5);
        System.out.println(test);
        manufacturerService.update(test);
        DriverService driverService = context.getBean(DriverService.class);
        System.out.println(driverService.get(3L));
        System.out.println(driverService.getAll());
        //System.out.println(driverService.delete(4L));
        Driver driverToUpdate = new Driver("William","LT126578");
        driverToUpdate.setId(3L);
        System.out.println(driverService.update(driverToUpdate));
    }
}
