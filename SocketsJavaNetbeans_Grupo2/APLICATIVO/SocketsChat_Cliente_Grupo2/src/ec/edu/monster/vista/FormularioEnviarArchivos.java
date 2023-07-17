package ec.edu.monster.vista;


import ec.edu.monster.controlador.EnviarArchivosControlador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template archivo, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Diego
 */

public class FormularioEnviarArchivos extends javax.swing.JFrame {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String miNombreUsuario;
    private String host;
    private int port;
    private StringTokenizer st;
    private String destinatario;
    private String archivo;
    private PantallaPrincipal main;
    private Object seleccionar;
    
    
     // Crea form FormularioEnviarArchivos
     
    public FormularioEnviarArchivos() {
        initComponents();
        pgbCarga.setVisible(false);
    }
    
    
    /*
       
    */
    public boolean preparar(String u, String h, int p, PantallaPrincipal m, Vector lista){
        this.host = h;
        this.miNombreUsuario = u;
        this.port = p;
        this.main = m;
        /*  COnexion al servidor */
        try {
            socket = new Socket(host, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            //  Format: CMD_SHARINGSOCKET [sender]
            String format = "CMD_SHARINGSOCKET "+ miNombreUsuario;
            dos.writeUTF(format);
            System.out.println(format);
                       
            /*  hilo de envio   */
            new Thread(new EnviarArchivo(this, lista)).start();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
    
    /*   enviar datos al servidor   */
    class EnviarArchivo implements Runnable{
        private FormularioEnviarArchivos form;
        private Vector listaUsuarios;
        public EnviarArchivo(FormularioEnviarArchivos form, Vector listaUsuarios){
            this.form = form;
            this.listaUsuarios = listaUsuarios;
        }
        
        private void cerrarVentanaUsuario(){
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("[closeMe]: "+e.getMessage());
            }
            dispose();
        }
        
        @Override
        public void run() {
            try {
                Iterator i = listaUsuarios.iterator();
                int contador = 0;
                form.limpiarListaUsuarios();
                while(i.hasNext()){
                    Object e = i.next();
                    form.añadirListaUsuarios(" "+ e);
                    contador++;
                }
                if (contador == 0){
                    form.añadirListaUsuarios("No hay usuarios conectados");
                }
               
                while(!Thread.currentThread().isInterrupted()){
                    String data = dis.readUTF();  // 
                    st = new StringTokenizer(data);
                    String cmd = st.nextToken();  //  
                    switch(cmd){
                        case "CMD_RECEIVE_FILE_ERROR":  // 
                            String msg = "";
                            while(st.hasMoreTokens()){
                                msg = msg+" "+st.nextToken();
                            }
                            form.updateAttachment(false);
                            JOptionPane.showMessageDialog(FormularioEnviarArchivos.this, msg, "Error", JOptionPane.ERROR_MESSAGE);
                            this.cerrarVentanaUsuario();
                            break;
                            
                        case "CMD_RECEIVE_FILE_ACCEPT":  

                            new Thread(new EnviarArchivosControlador(socket, archivo, destinatario, miNombreUsuario, FormularioEnviarArchivos.this, main)).start();
                            break;
                            
                        case "CMD_SENDFILEERROR":
                            String emsg = "";
                            while(st.hasMoreTokens()){
                                emsg = emsg +" "+ st.nextToken();
                            }                                                     
                            System.out.println(emsg);                            
                            JOptionPane.showMessageDialog(FormularioEnviarArchivos.this, emsg,"Error", JOptionPane.ERROR_MESSAGE);
                            form.updateAttachment(false);
                            form.deshabilitarGUI(false);
                            form.updateBtn("Enviar archivo");
                            break;
                        
                        
                        case "CMD_SENDFILERESPONSE":
                            /*
                            Format: CMD_SENDFILERESPONSE [username] [Message]
                            */
                            String rReceiver = st.nextToken();
                            String rMsg = "";
                            while(st.hasMoreTokens()){
                                rMsg = rMsg+" "+st.nextToken();
                            }
                            form.updateAttachment(false);
                            JOptionPane.showMessageDialog(FormularioEnviarArchivos.this, rMsg, "Error", JOptionPane.ERROR_MESSAGE);
                            dispose();
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSeleccionarArchivo = new javax.swing.JLabel();
        txtRutaArchivo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lblDestinatario = new javax.swing.JLabel();
        pgbCarga = new javax.swing.JProgressBar();
        btnEnviarArchivo = new javax.swing.JButton();
        lblEnviarArchivo = new javax.swing.JLabel();
        cmbDestinatario = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Selecionar Archivo - Chat Monster");

        lblSeleccionarArchivo.setText("Seleccionar archivo:");

        txtRutaArchivo.setEditable(false);
        txtRutaArchivo.setBackground(new java.awt.Color(255, 255, 255));
        txtRutaArchivo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtRutaArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutaArchivoActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar...");
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        lblDestinatario.setText("Para :");

        pgbCarga.setStringPainted(true);

        btnEnviarArchivo.setBackground(new java.awt.Color(52, 105, 177));
        btnEnviarArchivo.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarArchivo.setText("Enviar archivo");
        btnEnviarArchivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEnviarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarArchivoActionPerformed(evt);
            }
        });

        lblEnviarArchivo.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        lblEnviarArchivo.setForeground(new java.awt.Color(52, 105, 177));
        lblEnviarArchivo.setText("ENVÍO DE ARCHIVOS");

        cmbDestinatario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(lblEnviarArchivo)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSeleccionarArchivo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtRutaArchivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblDestinatario)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cmbDestinatario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEnviarArchivo)))
                        .addGap(29, 29, 29))))
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(pgbCarga, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblEnviarArchivo)
                .addGap(13, 13, 13)
                .addComponent(lblSeleccionarArchivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRutaArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar))
                        .addGap(23, 23, 23)
                        .addComponent(lblDestinatario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbDestinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEnviarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pgbCarga, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtRutaArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutaArchivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutaArchivoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        showOpenDialog();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEnviarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarArchivoActionPerformed
        // TODO add your handling code here:
        if (cmbDestinatario.getItemCount() == 0){
            destinatario = "";
        } else {
            destinatario = (String) cmbDestinatario.getSelectedItem();   
        }
        
        archivo = txtRutaArchivo.getText();

        if((destinatario.length() > 0) && (archivo.length() > 0)){
            try {
                // Format: CMD_SEND_FILE_XD [sender] [receiver] [filename]
                txtRutaArchivo.setText("");
                String fname = getThisFilename(archivo);
                String format = "CMD_SEND_FILE_XD "+miNombreUsuario+" "+destinatario+" "+fname;
                dos.writeUTF(format);
                System.out.println(format);
                updateBtn("Enviando...");
                btnEnviarArchivo.setEnabled(false);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }else{
            JOptionPane.showMessageDialog(this, "Por favor llene los espacios","Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEnviarArchivoActionPerformed
    public void showOpenDialog(){
        JFileChooser chooser = new JFileChooser();
        int intval = chooser.showOpenDialog(this);
        if(intval == chooser.APPROVE_OPTION){
            txtRutaArchivo.setText(chooser.getSelectedFile().toString());
        }else{
            txtRutaArchivo.setText("");
        }
    }
    
    public void deshabilitarGUI(boolean d){
        if(d){ 
            cmbDestinatario.setEditable(false);
            btnBuscar.setEnabled(false);
            btnEnviarArchivo.setEnabled(false);
            txtRutaArchivo.setEditable(false);
            pgbCarga.setVisible(true);
        } else { 
            cmbDestinatario.setEditable(true);
            btnEnviarArchivo.setEnabled(true);
            btnBuscar.setEnabled(true);
            txtRutaArchivo.setEditable(true);
            pgbCarga.setVisible(false);
        }
    }
    
    public void limpiarListaUsuarios(){
        cmbDestinatario.removeAllItems();
    }
    
    public void añadirListaUsuarios(String usuario){
        cmbDestinatario.addItem(usuario);
    }
   
    public void setMyTitle(String s){
        setTitle(s);
    }
    

    public void closeThis(){
        dispose();
    }
    
 
    public String getThisFilename(String path){
        File p = new File(path);
        String fname = p.getName();
        return fname.replace(" ", "_");
    }
    

    public void updateAttachment(boolean b){
        main.actualizarArchivoAdjunto(b);
    }
    
   
    public void updateBtn(String str){
        btnEnviarArchivo.setText(str);
    }
    
    /**
     * Update progress bar
     * @param val 
     */
    public void updateProgress(int val){
        pgbCarga.setValue(val);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       /*Inicio del programa*/
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioEnviarArchivos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEnviarArchivo;
    private javax.swing.JComboBox<String> cmbDestinatario;
    private javax.swing.JLabel lblDestinatario;
    private javax.swing.JLabel lblEnviarArchivo;
    private javax.swing.JLabel lblSeleccionarArchivo;
    private javax.swing.JProgressBar pgbCarga;
    private javax.swing.JTextField txtRutaArchivo;
    // End of variables declaration//GEN-END:variables
}
