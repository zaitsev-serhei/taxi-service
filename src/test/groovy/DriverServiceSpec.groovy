import com.taxi.dao.DriverDao
import com.taxi.model.Driver
import com.taxi.service.impl.DriverServiceImpl
import spock.lang.Specification

class DriverServiceSpec extends Specification {
    DriverDao driverDao = Mock()
    def driverService = new DriverServiceImpl(driverDao)

    def "create(Driver driver) test"() {
        given:
        Driver expected = new Driver(id: 1, name: "Nick")

        when:
        Driver actual = driverService.create(expected)

        then:
        1 * driverDao.create(expected) >> expected
        0 * _
        actual == expected
    }

    def "get(Long id) when driver by id present in the DB"() {
        given:
        Long id = 4L
        Driver expected = new Driver(id: 4)

        when:
        Driver actual = driverService.get(id)

        then:
        1 * driverDao.get(id) >> Optional.of(expected)
        0 * _
        actual == expected
    }

    def "getAll() test"() {
        given:
        Driver driver_1 = new Driver(id: 1)
        Driver driver_2 = new Driver(id: 2)
        Driver driver_3 = new Driver(id: 3)
        List<Driver> expected = [driver_1, driver_2, driver_3]

        when:
        List<Driver> actual = driverService.getAll()

        then:
        1 * driverDao.getAll() >> expected
        0 * _
        actual == expected
    }

    def "update(Driver driver) test"() {
        given:
        Driver expexted = new Driver(id: 2, name: "Serhii")

        when:
        expexted.setName("Serj")
        Driver actual = driverService.update(expexted)

        then:
        1 * driverDao.update(expexted) >> expexted
        0 * _
        actual.getName() == "Serj"
        actual.getId() == expexted.getId()
    }

    def "call delete method in DriverService"() {
        given:
        Long id = 4L

        when:
        driverService.delete(id)

        then:
        1 * driverDao.delete(id)
        0 * _
    }
}
