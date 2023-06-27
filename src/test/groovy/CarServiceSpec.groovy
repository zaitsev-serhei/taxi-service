import com.taxi.dao.CarDao
import com.taxi.model.Car
import com.taxi.model.Driver
import com.taxi.services.impl.CarServiceImpl
import spock.lang.Specification

class CarServiceSpec extends Specification {
    def "call create method in CarService" () {
        given:
            CarDao carDao = Mock();
            def carService = new CarServiceImpl(carDao);
            Car car = Mock();

        when: carService.create(car)

        then: 1*carDao.create(car);
    }
    def "call get method in CarService"() {
        given:
        CarDao carDao = Mock();
        def id =4L;
        def carService = new CarServiceImpl(carDao);

        when: carService.get(id)

        then: 1*carDao.get(id);
        thrown(NullPointerException)
    }
    def "call getAll method in CarService"() {
        given:
        CarDao carDao = Mock();
        def carService = new CarServiceImpl(carDao);

        when: carService.getAll()

        then: 1*carDao.getAll()
    }
    def "call update method in CarService"() {
        given:
        CarDao carDao = Mock();
        Car car = Mock();
        def carService = new CarServiceImpl(carDao);

        when: carService.update(car)

        then: 1*carDao.update(car);
    }
    def "call delete method in CarService"() {
        given:
        CarDao carDao = Mock();
        def carService = new CarServiceImpl(carDao);
        def id;

        when: carService.delete(id)

        then: 1*carDao.delete(id);
    }
    def "call addDriverToCar method in CarService"() {
        given:
        CarDao carDao = Mock();
        Car car = new Car();
        Driver driver = Mock();
        def carService = new CarServiceImpl(carDao);

        when: carService.addDriverToCar(driver,car)

        then:
        1*carDao.update(car);
        car.getDrivers().isEmpty() == false
    }
    def "call RemoveDriverFromCar method in CarService"() {
        given:
        CarDao carDao = Mock();
        Car car = new Car();
        Driver driver = Mock();
        car.getDrivers().add(driver)
        def carService = new CarServiceImpl(carDao);

        when: carService.removeDriverFromCar(driver,car)

        then: 1*carDao.update(car);
        car.getDrivers().isEmpty() == true
    }
    def "call getAllByDriver method in CarService"() {
        given:
        CarDao carDao = Mock();
        def carService = new CarServiceImpl(carDao);
        def id;

        when: carService.getAllByDriver(id)

        then: 1*carDao.getAllByDriver(id)
    }


}
