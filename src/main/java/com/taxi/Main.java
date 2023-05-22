package com.taxi;

import com.taxi.config.AppConfig;
import com.taxi.dao.ManufacturerDao;
import com.taxi.model.Manufacturer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args) {
        /*ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);*/
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        ManufacturerDao manufacturerDao = context.getBean(ManufacturerDao.class);
        Manufacturer test = new Manufacturer("CreateTest","Ukraine");
        System.out.println(manufacturerDao.create(test));
        manufacturerDao.get(5L);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.delete(10L));
        //test.setDeleted(true);
        test.setId(5);
        System.out.println(test);
        manufacturerDao.update(test);
    }
}
