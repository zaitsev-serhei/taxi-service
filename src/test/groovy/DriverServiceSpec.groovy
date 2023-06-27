import com.taxi.dao.DriverDao
import com.taxi.model.Driver
import com.taxi.services.impl.DriverServiceImpl
import spock.lang.Specification

class DriverServiceSpec extends Specification {
    def "call create method in DriverService"() {
    given:
    DriverDao driverDao =Mock()
    Driver driver = Mock()
    def driverService = new DriverServiceImpl(driverDao)

    when: driverService.create(driver)

    then: 1*driverDao.create(driver)

    }
    def "call get method in DriverService"() {
        given:
        DriverDao driverDao =Mock()
        Long id = 4L
        def driverService = new DriverServiceImpl(driverDao)

        when: driverService.get(id)

        then: 1*driverDao.get(id)
        thrown(NullPointerException)

    }
    def "call getAll method in DriverService"() {
        given:
        DriverDao driverDao =Mock()
        def driverService = new DriverServiceImpl(driverDao)

        when: driverService.getAll()

        then: 1*driverDao.getAll()

    }
    def "call update method in DriverService"() {
        given:
        DriverDao driverDao =Mock()
        Driver driver = Mock()
        def driverService = new DriverServiceImpl(driverDao)

        when: driverService.update(driver)

        then: 1*driverDao.update(driver)

    }
    def "call delete method in DriverService"() {
        given:
        DriverDao driverDao =Mock()
        def driverService = new DriverServiceImpl(driverDao)
        Long id = 4L

        when: driverService.delete(id)

        then: 1*driverDao.delete(id)

    }
}
