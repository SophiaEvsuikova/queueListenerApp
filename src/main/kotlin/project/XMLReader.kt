package project

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@Service
class XMLReader {

    private val _logger = KotlinLogging.logger {}

    @Autowired
    lateinit var peopleService: PeopleService

    private fun getChildList(parent: Element, name: String): List<Element> {

        var child = parent.firstChild
        val children = mutableListOf<Element>()

        while (child != null) {
            if ((child is Element) && (name == child.nodeName))
                children.add(child)
            child = child.nextSibling
        }
        return children
    }

    private fun tryGetChild(parent: Element, name: String): Element? {

        var child = parent.firstChild

        while (child != null) {
            if ((child is Element) && (name == child.nodeName))
                return child
            child = child.nextSibling
        }
        return null
    }

    private fun stringToDocument(xmlSource: String?): Document {
        val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        val builder: DocumentBuilder = factory.newDocumentBuilder()
        return builder.parse(InputSource(xmlSource?.let { StringReader(it) }))
    }


    fun getPeople(messageData: String) {

        val people = mutableListOf<Person>()
        val doc = stringToDocument(messageData)
        val rootItem = doc.firstChild

        val personsList = getChildList(rootItem as Element, "person")

        for (person in personsList)
        {
            val name = tryGetChild(person, "name")?.textContent.toString()
            val lastname = tryGetChild(person, "last-name")?.textContent.toString()
            people.add(Person(name, lastname))
        }

        _logger.info { "Get $people" }

        peopleService.insertPeople(people)
    }
}