/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.practicaSocket.mom;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.qpid.jms.JmsConnectionFactory;


/**
 *
 * @author Andres
 */
public class RabbitMQyJMS {
    private static final String BROKER_URL = "amqp://guest:guest@localhost:5672";
    private static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) {
        try {
            // Configurar la conexión a RabbitMQ usando la fábrica de conexiones JMS
            ConnectionFactory connectionFactory = (ConnectionFactory) new JmsConnectionFactory(BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Crear una sesión JMS
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Crear una cola de destino
            Destination destination = session.createQueue(QUEUE_NAME);

            // Enviar un mensaje a la cola
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage("Hola, RabbitMQ!");
            producer.send(message);
            System.out.println("Mensaje enviado: " + message.getText());

            // Recibir un mensaje de la cola
            MessageConsumer consumer = session.createConsumer(destination);
            TextMessage receivedMessage = (TextMessage) consumer.receive();
            System.out.println("Mensaje recibido: " + receivedMessage.getText());

            // Cerrar recursos
            producer.close();
            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
