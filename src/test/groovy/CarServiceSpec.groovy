import com.taxi.dao.CarDao
import com.taxi.model.Car
import com.taxi.model.Driver
import com.taxi.model.Manufacturer
import com.taxi.services.impl.CarServiceImpl
import spock.lang.Specification

class CarServiceSpec extends Specification {
    CarDao carDao = Mock();
    def carService = new CarServiceImpl(carDao);

    def "create(Car car) test"() {
        given:
        Car expected = new Car(id:1);

        when:
        Car actual = carService.create(expected)

        then:
        1 * carDao.create(expected) >> expected
        0 * _
        actual == expected
    }

    def "get(Long id) when car by id present in the DB"() {
        given:
        long id = 4L;
        Car expected = new Car(id: id)

        when:
        Car actual = carService.get(id)

        then:
        1 * carDao.get(id) >> Optional.of(expected);
        0 * _
        actual == expected
    }

    def "getAll() test"() {
        given:
        Car car_1 = new Car(id: 1, model: "model_1")
        Car car_2 = new Car(id: 2, model: "model_2")
        Car car_3 = new Car(id: 3, model: "model_3")
        List<Car> expected = [car_1, car_2, car_3]

        when:
        List<Car> actual = carService.getAll()

        then:
        1 * carDao.getAll() >> expected
        0 * _
        actual == expected
    }

    def "update(Car car) test"() {
        given:
        Manufacturer manufacturer = new Manufacturer(name: "BMV" )
        Car expected = new Car(id: 1, manufacturer: manufacturer)

        when:
        expected.getManufacturer().setName("BTR")
        Car actual = carService.update(expected)

        then:
        1 * carDao.update(expected) >> expected
        0 * _
        actual.getManufacturer().getName() == "BTR"
        actual.getId() == expected.getId()
    }

    def "delete(Long id) test"() {
        given:
        long id = 4;

        when:
        carService.delete(id)

        then:
        1 * carDao.delete(id);
        0 * _
    }

    def "addDriverToCar(Driver driver, Car car) both are present in the DB"() {
        given:
        Car car = new Car();
        Driver driver = new Driver(id: 1, name: "Dave");
        Driver drive_2 = new Driver(id: 3,name: "Den");
        Driver drive_3 = new Driver(id: 2,name: "Denise");
        car.getDrivers().addAll([drive_2,drive_3]);

        when:
        carService.addDriverToCar(driver, car)

        then:
        1 * carDao.update(car);
        0 * _
        !car.getDrivers().isEmpty()
        car.getDrivers().size() == 3
        car.getDrivers().contains(driver)
    }

    def "RemoveDriverFromCar(Driver driver, Car car) both are present in the DB"() {
        given:
        Car car = new Car();
        Driver driver = new Driver(id: 1, name: "John")
        Driver drive_2 = new Driver(id: 3, name: "Den");
        Driver drive_3 = new Driver(id: 2, name: "Denise");
        car.getDrivers().addAll(driver, drive_2, drive_3);

        when:
        carService.removeDriverFromCar(driver, car)

        then:
        1 * carDao.update(car);
        0 * _
        !car.getDrivers().isEmpty()
        !car.getDrivers().contains(driver)
        car.getDrivers().size() == 2

    }

    def "getAllByDriver(Long driverId) driver exists in the DB"() {
        given:
        Driver driver = new Driver(id: 4)
        Car car_1 = new Car(id: 1)
        car_1.getDrivers().add(driver)
        Car car_2 = new Car(id: 2)
        car_2.getDrivers().add(driver)
        List<Car> expected = [car_1, car_2]

        when:
        List<Car> actual = carService.getAllByDriver(driver.getId())

        then:
        1 * carDao.getAllByDriver(driver.getId()) >> expected
        0 * _
        actual == expected
    }
}
