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
import javax.swing.JOptionPane;

/**
 *
 * @author NALE COMPUTERS
 */
public class ClienteV implements Runnable {
    private int puerto;
    private String hostname;
    private String mensaje;

    public ClienteV(int puerto, String hostname, String mensaje) {
        this.puerto = puerto;
        this.hostname = hostname;
        this.mensaje = mensaje;
    }
    
    public void cerrarConexion(Socket sc) throws IOException{
    sc.close();
    System.out.println("cliente desconectado");
    }
     public static String decodificar(String mensaje){
    String textoDecodificado="";
    String letras="abcdefghijklmnñopqrstuvwxyz1234567890";
        char caracter;
        for (int i = 0; i < mensaje.length(); i++) {
            caracter = mensaje.charAt(i);
            
            int pos = letras.indexOf(caracter);
            
            if(pos == -1){
                textoDecodificado += caracter;
            }else{
                if(pos-3 < 0){
                 textoDecodificado += letras.charAt(letras.length()+(pos-3));   
                }else{
                textoDecodificado += letras.charAt((pos-3) % letras.length());
            }
        }
        }
        System.out.println("texto decodificado: "+textoDecodificado);
        return textoDecodificado;
    }
    
     @Override
    public void run() {
        DataInputStream in;
     DataOutputStream out;   
     try {
         Socket sc = new Socket(hostname, puerto);
         in = new DataInputStream(sc.getInputStream());
         out = new DataOutputStream(sc.getOutputStream());
         //enviar el mensaje
         out.writeUTF(mensaje);
         /*String mensaje = in.readUTF();
         System.out.println(mensaje);*/
         //sc.close();
                
     } catch (IOException ex) {
         JOptionPane.showMessageDialog(null, "cliente con el puerto "+ puerto +" desconectado o cliente sin conexion");
     }
     }  
     
}
