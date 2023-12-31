/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ec.edu.ups.practicaSocket.vista;

import ec.edu.ups.practicaSocket.cliente.Cliente;
import ec.edu.ups.pracicaSocket.servidor.Servidor;
import ec.edu.ups.practicaSocket.cliente.ClienteV;
import static ec.edu.ups.practicaSocket.vista.ClienteVista1.codificar;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author NALE COMPUTERS
 */
public class ClienteVista3 extends javax.swing.JFrame implements Observer{
private Cliente c;
    /**
     * Creates new form Cliente
     * @param host
     * @param port
     * @throws java.io.IOException
     */
    public ClienteVista3(String host, int port) throws IOException {
        initComponents();
        this.getRootPane().setDefaultButton(jEnviar);
        Servidor s = new Servidor(6001);
        s.addObserver(this);
        Thread t = new Thread(s);
        t.start();
        
    }

    private ClienteVista3() {
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        txtMensaje = new javax.swing.JTextField();
        jEnviar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setTitle("Cliente 3");

        txtArea.setColumns(20);
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        txtMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMensajeActionPerformed(evt);
            }
        });

        jEnviar.setText("Enviar");
        jEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEnviarActionPerformed(evt);
            }
        });

        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(txtMensaje))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jEnviar))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jEnviar))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEnviarActionPerformed
       this.getRootPane().setDefaultButton(jEnviar);
       String mensaje = "Cliente 3: "+this.txtMensaje.getText()+"\n";
       this.txtArea.append(mensaje);
       Cliente c = new Cliente(5000,codificar(mensaje));
       Cliente c2 = new Cliente(6000,codificar(mensaje));
       Cliente c4 = new Cliente(6002,codificar(mensaje));
       //ClienteV cV = new ClienteV(5000, "192.168.18.61", mensaje);
       Thread t = new Thread(c);
       Thread t2= new Thread(c2);
       Thread t4= new Thread(c4);
       //Thread tV = new Thread(cV);
       t.start();
       t2.start();
       t4.start();
       //tV.start();
 txtMensaje.setText("");       
    }//GEN-LAST:event_jEnviarActionPerformed

    private void txtMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMensajeActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     try {
        String mensaje = "Cliente desconectado";
        Socket sc = new Socket("192.168.18.60",6001);
        c= new Cliente(5001,mensaje);
        c.cerrarConexion(sc);
        JOptionPane.showMessageDialog(null, mensaje);
        this.setVisible(false);
    } catch (IOException ex) {
        System.out.println("no hay conexion");
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClienteVista3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClienteVista3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClienteVista3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClienteVista3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClienteVista3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jEnviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
       this.txtArea.append((String) arg);
    }
}
