package com.services.controllers;

import java.util.Iterator;
import java.util.Set;

import javax.jms.*;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;

public class Consumer {
	// URL of the JMS server
	
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	 
	
	// Name of the queue we will receive messages from
	
	private static String subject = "MYQUEUE";
	
	public Consumer(){
		
	}
	
	public static void main(String[] args) {
	

		// Getting JMS connection from the server

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

		Connection connection =null;
		MessageConsumer consumer =null;

		try {
			connection=connectionFactory.createConnection();

			connection.start();

			// Creating session for sending messages

			Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

			// Getting the queue

			Destination destination = session.createQueue(subject);

			// MessageConsumer is used for receiving (consuming) messages
			
				consumer = session.createConsumer(destination);

				try {
					// Here we receive the message.
					// it will not wait for a message to arrive on the queue.
					Message message = consumer.receiveNoWait();
					// There are many types of Message and TextMessage
					// is just one of them. Producer sent us a TextMessage
					// so we must cast to it to get access to its .getText()
					// method.	
					if(null!=message){

						
												TextMessage textMessage=null;
												if (message instanceof TextMessage) {
						
													textMessage = (TextMessage) message;
						
													System.out.println("Received message '" + textMessage.getText()+ "'");
						            
												}
											
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(null!=consumer)consumer.close();
				}
			}
			//		}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try { 
				if(null!=consumer)consumer.close();
				if(null!=connection)connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	}

