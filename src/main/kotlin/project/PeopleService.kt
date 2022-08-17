package project

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

data class Person(val name: String, val lastname: String)

@Component
class PeopleService {

    @Autowired
    lateinit var dataBaseService: DataBaseService

    private val _logger = KotlinLogging.logger {}

    fun insertPeople(people: MutableList<Person>) {

        for (person in people) {
            if (dataBaseService.findPerson(person.name, person.lastname) > 0) {
                _logger.info { "Ignore Person ${person.name} ${person.lastname}" }
            } else {
                dataBaseService.insert(person.name, person.lastname)
                _logger.info { "Inserted Person ${person.name} ${person.lastname}" }
            }
        }
    }
}