/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.practicaSocket.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JOptionPane;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author NALE COMPUTERS
 */
public class Cliente implements Runnable {

    private int puerto;
    private String hostname;
    private String mensaje;
    private final Object lock = new Object();

    public Cliente(int puerto, String mensaje) {
        this.puerto = puerto;
        this.mensaje = decodificar(mensaje);;
    }

    public Cliente(int puerto, String hostname, String mensaje) {
        this.puerto = puerto;
        this.hostname = hostname;
        this.mensaje = mensaje;
    }

    public void cerrarConexion(Socket sc) throws IOException {
        sc.close();
        System.out.println("cliente desconectado");
    }

    public static String decodificar(String mensaje) {
        String textoDecodificado = "";
        String letras = "abcdefghijklmnñopqrstuvwxyz1234567890";
        char caracter;
        for (int i = 0; i < mensaje.length(); i++) {
            caracter = mensaje.charAt(i);

            int pos = letras.indexOf(caracter);

            if (pos == -1) {
                textoDecodificado += caracter;
            } else {
                if (pos - 3 < 0) {
                    textoDecodificado += letras.charAt(letras.length() + (pos - 3));
                } else {
                    textoDecodificado += letras.charAt((pos - 3) % letras.length());
                }
            }
        }
        System.out.println("texto decodificado: " + textoDecodificado);
        return textoDecodificado;
    }

    public void notificarTerminacion() {
        synchronized (lock) {
            lock.notify();
        }
    }

    @Override
    public void run() {
        final String host = "192.168.18.60";
        try (
                Socket sc = new Socket(host, puerto); DataInputStream in = new DataInputStream(sc.getInputStream()); DataOutputStream out = new DataOutputStream(sc.getOutputStream())) {

            // Enviar el mensaje codificado
            out.writeUTF(mensaje);

            // Establecer la conexión con ActiveMQ
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Crear una sesión JMS
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Crear una cola (asegúrate de que coincida con la cola del productor)
            Destination destination = session.createQueue("ChatCola");

            // Crear un consumidor
            MessageConsumer consumer = session.createConsumer(destination);

            // Establecer un escuchador para recibir mensajes
            consumer.setMessageListener(message -> {
                try {
                    // Obtener el texto codificado del mensaje
                    String encodedMessage = ((TextMessage) message).getText();
                    // Decodificar el mensaje
                    String decodedMessage = decodificar(encodedMessage);
                    System.out.println("Otro usuario: " + decodedMessage);
                } catch (JMSException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            // Mantener el consumidor en ejecución
            while (true) {
                try {
                    // Mantener el consumidor en ejecución
                    synchronized (lock) {
                        lock.wait(); // Esperar hasta que se reciba una notificación
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cliente con el puerto " + puerto + " desconectado o sin conexión");
        } catch (JMSException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
