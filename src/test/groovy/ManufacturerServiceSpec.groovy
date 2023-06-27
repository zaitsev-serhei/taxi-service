import com.taxi.dao.ManufacturerDao
import com.taxi.model.Manufacturer
import com.taxi.services.impl.ManufacturerServiceImpl
import spock.lang.Specification

class ManufacturerServiceSpec extends  Specification{
    def "call create method in ManufacturerService"() {
        given:
        ManufacturerDao manufacturerDao = Mock()
        Manufacturer manufacturer = Mock()
        def manufacturerService = new ManufacturerServiceImpl(manufacturerDao)
        when:
        manufacturerService.create(manufacturer)
        then:
        1*manufacturerDao.create(manufacturer)
    }
    def "call get method in ManufacturerService"() {
        given:
        ManufacturerDao manufacturerDao = Mock()
        Long id = 4L
        def manufacturerService = new ManufacturerServiceImpl(manufacturerDao)
        when:
        manufacturerService.get(id)
        then:
        1*manufacturerDao.get(id)
        thrown(NullPointerException)
    }
    def "call getAll method in ManufacturerService"() {
        given:
        ManufacturerDao manufacturerDao = Mock()
        def manufacturerService = new ManufacturerServiceImpl(manufacturerDao)
        when:
        manufacturerService.getAll()
        then:
        1*manufacturerDao.getAll()
    }
    def "call update method in ManufacturerService"() {
        given:
        ManufacturerDao manufacturerDao = Mock()
        Manufacturer manufacturer = Mock()
        def manufacturerService = new ManufacturerServiceImpl(manufacturerDao)
        when:
        manufacturerService.update(manufacturer)
        then:
        1*manufacturerDao.update(manufacturer)
    }
    def "call delete method in ManufacturerService"() {
        given:
        ManufacturerDao manufacturerDao = Mock()
        Long id = 4L
        def manufacturerService = new ManufacturerServiceImpl(manufacturerDao)
        when:
        manufacturerService.delete(id)
        then:
        1*manufacturerDao.delete(id)
    }
}
