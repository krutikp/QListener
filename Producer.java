package com.services.listner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producers {
	// URL of the JMS server

	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	// Name of the queue we will receive messages from
  String queue ="MUQUEUE";
	String message= "Hello. This is test Message."
	
	public static void main(String args[]){
		
		
		// Getting JMS connection from the server
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		
		Connection connection =null;
		MessageProducer producer =null;
		
		try {
			connection=connectionFactory.createConnection();
			
			connection.start();
			
			// Creating session for sending messages
			
			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			
			// Getting the queue
			
			Destination destination = session.createQueue(queue);
			
			// Message is reallocate to queue
			producer = session.createProducer(destination);
			try {
				// Here we receive the message.
				// it will not wait for a message to arrive on the queue.

				TextMessage msg = session.createTextMessage();

				msg.setText(message);
				
				producer.send(msg);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(null!=producer)producer.close();
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try { 
				if(null!=producer)producer.close();
				if(null!=connection)connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
