package project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jms.annotation.EnableJms

@SpringBootApplication
@EnableJms
class QueueApplication

fun main(args: Array<String>) {
    runApplication<QueueApplication>(*args)
}
