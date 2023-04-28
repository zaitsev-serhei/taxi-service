package mate.jdbc;

import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");
    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer test = new Manufacturer("CreateTest","Ukraine");
      //  System.out.println(manufacturerDao.create(test));
        manufacturerDao.get(5L);
        System.out.println(manufacturerDao.getAll());
        System.out.println(manufacturerDao.isDeleted(100L));
        test.setDeleted(true);
        test.setId(15);
        System.out.println(test);
        manufacturerDao.update(test);
    }
}
