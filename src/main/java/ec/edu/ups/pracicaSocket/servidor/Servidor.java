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

/**
 *
 * @author Andres
 *///uso de observable para notificar cambios en el programa y runnable para ejecutar como hilo 
public class Servidor extends Observable implements Runnable {

    private int puerto;
    private String estado;

    public Servidor(int puerto) {
        this.puerto = puerto;
    }
    public void cerrarConexion(Socket sc) throws IOException{
    sc.close();
    System.out.println("cliente desconectado");
    }
    @Override
    public void run() {
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
                out = new DataOutputStream(sc.getOutputStream());
                out.writeUTF("escribe tu nombre");
                in = new DataInputStream(sc.getInputStream());
                String mensaje = in.readUTF();
                System.out.println(mensaje);
                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();

            }
        } catch (IOException ex) {
            System.out.println("desconexion");
        }

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}

/*
public class Servidor {

    public static void main(String[] args) {
        
        
        try {
            ServerSocket server = new ServerSocket(5000);
            Socket sc;
            
            System.out.println("Servidor iniciado");
            while(true){
            
                // Espero la conexion del cliente
                sc = server.accept();
                
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                
                // Pido al cliente el nombre al cliente
                out.writeUTF("Indica tu nombre");
                String nombreCliente = in.readUTF();
                
                // Inicio el hilo
                ServidorHilo hilo = new ServidorHilo(in, out, nombreCliente);
                hilo.start();
                
                System.out.println("Creada la conexion con el cliente " + nombreCliente);
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
*/
/*
public class ServidorHilo extends Thread{
    
    private DataInputStream in;
    private DataOutputStream out;
    private String nombreCliente;

    public ServidorHilo(DataInputStream in, DataOutputStream out, String nombreCliente) {
        this.in = in;
        this.out = out;
        this.nombreCliente = nombreCliente;
    }
    
    @Override
    public void run(){
        
    }
    
}
*/