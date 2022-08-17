package project

import mu.KotlinLogging
import org.apache.activemq.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jms.annotation.JmsListener
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component
import javax.jms.JMSException
import javax.jms.TextMessage

@Component
class Listener {

    private val _logger = KotlinLogging.logger {}

    @Autowired
    lateinit var xmlReader: XMLReader

    @JmsListener(destination = "inbound.queue")
    @SendTo("outbound.queue")
    @Throws(JMSException::class)
    fun receiveMessage(xmlMessage: Message) {

        val messageData: String
        _logger.info { "Received message $xmlMessage" }

        if (xmlMessage is TextMessage) {
            val textMessage: TextMessage = xmlMessage
            messageData = textMessage.text
            xmlReader.getPeople(messageData)
        }
    }
}
