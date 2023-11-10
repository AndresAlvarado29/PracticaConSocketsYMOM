/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.practicaSocket.mom;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Andres
 */
public class ApacheMQyJMS {
    public static void main(String[] args) {
        // URL de conexión al broker ActiveMQ
        String brokerUrl = "tcp://localhost:61616";

        // Nombre de la cola
        String queueName = "cola";

        try {
            // Establecer la conexión al broker ActiveMQ
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Crear una sesión
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Crear una cola
            Destination destination = session.createQueue(queueName);

            // Crear un productor para enviar mensajes
            MessageProducer producer = session.createProducer(destination);

            // Crear un mensaje
            TextMessage message = session.createTextMessage("Hola, este es un mensaje de ejemplo!");

            // Enviar el mensaje a la cola
            producer.send(message);
            System.out.println("Mensaje enviado: " + message.getText());

            // Crear un consumidor para recibir mensajes
            MessageConsumer consumer = session.createConsumer(destination);

            // Esperar y recibir un mensaje de la cola
            Message receivedMessage = consumer.receive();
            if (receivedMessage instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) receivedMessage;
                System.out.println("Mensaje recibido: " + textMessage.getText());
            }

            // Cerrar las conexiones y sesiones
            producer.close();
            consumer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    } 
}
