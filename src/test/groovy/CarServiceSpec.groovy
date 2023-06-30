import com.taxi.dao.CarDao
import com.taxi.model.Car
import com.taxi.model.Driver
import com.taxi.services.impl.CarServiceImpl
import spock.lang.Specification

class CarServiceSpec extends Specification {
    CarDao carDao = Mock();
    def carService = new CarServiceImpl(carDao);

    def "create(Car car) test"() {
        given:
        Car car = Mock();

        when:
        carService.create(car)

        then:
        1 * carDao.create(car);
        0 * _
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
        Car car = Mock();

        when:
        carService.update(car)

        then:
        1 * carDao.update(car);
        0 * _
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
        Driver driver = Mock();

        when:
        carService.addDriverToCar(driver, car)

        then:
        1 * carDao.update(car);
        0 * _
        car.getDrivers().isEmpty() == false
    }

    def "RemoveDriverFromCar(Driver driver, Car car) both are present in the DB"() {
        given:
        Car car = new Car();
        Driver driver = Mock();
        car.getDrivers().add(driver)

        when:
        carService.removeDriverFromCar(driver, car)

        then:
        1 * carDao.update(car);
        0 * _
        car.getDrivers().isEmpty() == true
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
