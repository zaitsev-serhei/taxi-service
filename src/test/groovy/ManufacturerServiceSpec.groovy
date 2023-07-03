import com.taxi.dao.ManufacturerDao
import com.taxi.model.Manufacturer
import com.taxi.services.impl.ManufacturerServiceImpl
import spock.lang.Specification

class ManufacturerServiceSpec extends Specification {
    ManufacturerDao manufacturerDao = Mock()
    def manufacturerService = new ManufacturerServiceImpl(manufacturerDao)

    def "create(Manufacturer manufacturer) test"() {
        given:
        Manufacturer expected = new Manufacturer(id: 1, name: "BMV")

        when:
        Manufacturer actual = manufacturerService.create(expected)

        then:
        1 * manufacturerDao.create(expected) >> expected
        0 * _
        actual == expected
    }

    def "get(Long id) when manufacturer by id present in the DB"() {
        given:
        Long id = 4L
        Manufacturer expected = new Manufacturer(id: id)

        when:
        Manufacturer actual = manufacturerService.get(id)

        then:
        1 * manufacturerDao.get(id) >> Optional.of(expected)
        0 * _
        actual == expected
    }

    def "getAll() test"() {
        given:
        Manufacturer manuf_1 = new Manufacturer(id: 1,name: "test_1")
        Manufacturer manuf_2 = new Manufacturer(id: 2,name: "test_2")
        Manufacturer manuf_3 = new Manufacturer(id: 3,name: "test_3")
        List<Manufacturer> expected = [manuf_1, manuf_2, manuf_3]

        when:
        List<Manufacturer> actual = manufacturerService.getAll()

        then:
        1 * manufacturerDao.getAll() >> expected
        0 * _
        actual == expected
    }

    def "update(Manufacturer manufacturer) test"() {
        given:
        Manufacturer manufacturer = new Manufacturer(id: 1, name: "Lexus")

        when:
        manufacturer.setName("Mersedes")
        Manufacturer actual = manufacturerService.update(manufacturer)

        then:
        1 * manufacturerDao.update(manufacturer) >> manufacturer
        0 * _
        actual.getName() == "Mersedes"
        actual.getId() == manufacturer.getId()
    }

    def "delete(Long id) test"() {
        given:
        Long id = 4L

        when:
        manufacturerService.delete(id)

        then:
        1 * manufacturerDao.delete(id)
        0 * _
    }
}
