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
public class Cliente implements Runnable {
    private int puerto;
    private String hostname;
    private String mensaje;

    public Cliente(int puerto, String mensaje) {
        this.puerto = puerto;
        this.mensaje = decodificar(mensaje);;
    }

    public Cliente(int puerto, String hostname, String mensaje) {
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
        final String host="192.168.18.60";
     DataInputStream in;
     DataOutputStream out; 
         while (true) {             
         try {
         Socket sc = new Socket(host, puerto);
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
             try {
               JOptionPane.showMessageDialog(null, "reconectando");
               Thread.sleep(10000);
             } catch (Exception e) {
             }
         }
     
     }  
    
}



/*
/*
public class Cliente {

    public static void main(String[] args) {
        
        try {
            Scanner sn = new Scanner(System.in);
            sn.useDelimiter("\n");
            
            Socket sc = new Socket("127.0.0.1", 5000);
            
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            
            // Leer mensaje del servidor
            String mensaje = in.readUTF();
            System.out.println(mensaje);
            
            // Escribe el nombre y se lo manda al servidor
            String nombre = sn.next();
            out.writeUTF(nombre);
            
            // ejecutamos el hilo
            ClienteHilo hilo = new ClienteHilo(in, out);
            hilo.start();
            hilo.join();
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }
    
}
*/

/*
public class ClienteHilo extends Thread{
    
    private DataInputStream in;
    private DataOutputStream out;

    public ClienteHilo(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }
    
    @Override
    public void run(){
        
    }
}
*/