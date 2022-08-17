package project

import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.core.JmsTemplate

@Configuration
class JmsConnection {

    @Bean
    fun connectionFactory(): ActiveMQConnectionFactory {
        val connFactory = ActiveMQConnectionFactory()
        val conn = connFactory.createConnection()!!
        conn.start()

        return connFactory
    }

    @Bean
    fun jmsTemplate(): JmsTemplate {
        val template = JmsTemplate()
        template.connectionFactory = connectionFactory()
        return template
    }

    @Bean
    fun jmsListenerContainerFactory(): DefaultJmsListenerContainerFactory {
        val factory = DefaultJmsListenerContainerFactory()
        connectionFactory().let { factory.setConnectionFactory(it) }
        factory.setConcurrency("1-1")
        return factory
    }
}