package com.example.demo

import javax.jms.Session
import org.apache.activemq.ActiveMQConnectionFactory


fun main() {
	val connFactory = ActiveMQConnectionFactory()
	val conn = connFactory.createConnection()!!
	conn.start()
	val sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)!!

	// Producer
	val dest = sess.createQueue("People")

	//val prod = sess.createProducer(dest)!!
	val msg = sess.createTextMessage("Simples Assim")!!

	//prod.send(msg)


	// Consumer
	val cons = sess.createConsumer(dest)!!

	val msgFromQueue = cons.receive()

	println(msgFromQueue)

	conn.close()
}
