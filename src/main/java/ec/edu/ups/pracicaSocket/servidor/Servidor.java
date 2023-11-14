/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.pracicaSocket.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;


/**
 *
 * @author Andres
 *///uso de observable para notificar cambios en el programa y runnable para ejecutar como hilo 
public class Servidor extends Observable implements Runnable {
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
    Connection connection;   
    private final int puerto;
    Session session;
    Destination destination;
    MessageProducer producer;

    public Servidor(int puerto) {
        this.puerto = puerto;
    }
    public void cerrarConexion(Socket sc) throws IOException{
    sc.close();
    System.out.println("cliente desconectado");
    }
    @Override
    public void run() {
        try {
            connection=connectionFactory.createConnection();
            connection.start();
            System.out.println("conexion del productor con activemq iniciada");
            // Crear una sesión JMS
             session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
             destination = session.createQueue("ChatCola");

        // Crear un productor
            producer = session.createProducer(destination);
        } catch (JMSException ex) {
            System.out.println("error al conectarse");
        }
        ServerSocket servidor = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;
       

        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");
            while (true) {
                sc = servidor.accept();
                System.out.println("Cliente Conectado");
                in = new DataInputStream(sc.getInputStream());
                String mensaje = in.readUTF();
                System.out.println(mensaje);
                enviarMensaje(producer, session, mensaje);
                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();
            }
        } catch (IOException ex) {
            System.out.println("desconexion");
        }

    }
   public void enviarMensaje(MessageProducer producer,Session session,String mensaje){
        try {
            TextMessage message = session.createTextMessage(mensaje);
            producer.send(message);
            System.out.println("Mensaje enviado: " + message.getText());
        } catch (JMSException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
   } 
    
}