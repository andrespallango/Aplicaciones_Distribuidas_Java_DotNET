package ec.edu.monster.vista;



import ec.edu.monster.controlador.MensajesControlador;
import ec.edu.monster.modelo.Estilos;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class PantallaPrincipal extends javax.swing.JFrame {
    public String nombreUsuario;
    String host;
    int port;
    Socket socket;
    DataOutputStream dataOutputStream;
    public boolean abrirArchivo = false;
    private boolean conectado = false;
    //para guardar los archivos
    private String carpetaDestino = "D:\\";
    private Vector listaUsuarios;

    /**
     * Creador de la pantalla principal
     */
    public PantallaPrincipal() {
        initComponents();
    }
        public void initFrame(String username, String host, int port){
        this.nombreUsuario = username;
        this.host = host;
        this.port = port;
        setTitle("Bienvenido " + username);
        //conexion
        conectar();
    }
    
    public void conectar(){
        imprimirMensaje(" Se esta estableciendo la conexión", "Sistema", Color.BLACK, new Color(147,147,147), "", true, true, "", false, true);
        try {
            socket = new Socket(host, port);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            // envia nombre de usuario de conexion
            dataOutputStream.writeUTF("CMD_JOIN "+ nombreUsuario);
            imprimirMensaje("Conexión correcta con el servidor", "", Color.WHITE, new Color(115,173,115), "", true, true, "", false, true);
            imprimirMensaje("Puedes comenzar a chatear", "", Color.WHITE, new Color(147,147,147), "", true, true, "", false, true);
            
            // inicia hilo de cliente
            new Thread(new MensajesControlador(socket, this)).start();
            btnEnviar.setEnabled(true);
            // se ha conectado
            conectado = true;
            
        }
        catch(IOException e) {
            conectado = false;
            JOptionPane.showMessageDialog(this, "Ocurrio un error al conectar intentelo mas tarde","Conexión fallida",JOptionPane.ERROR_MESSAGE);
            imprimirMensaje("[IOException]: "+ e.getMessage(), "Error", Color.RED, Color.RED, "", true, true, "", false, false);
        }
    }
    
    /*
       Conectarse
    */
    public boolean verificarConexion(){
        return this.conectado;
    }
    
    /*
        Ver mensajes del chat
    */
    public void imprimirMensaje(String msg, String header, Color headerColor, Color contentColor, String fontFamilyHeader, boolean boldHeader, boolean italicHeader, String fontFamilyContent, boolean boldContent, boolean italicContent){
        txpPanelChat.setEditable(true);
        imprimirEncabezado(header, headerColor, fontFamilyHeader, boldHeader, italicHeader);
        imprimirContenido(msg, contentColor, fontFamilyContent, boldContent, italicContent);
        txpPanelChat.setEditable(false);
    }
    
    /*
        mensaje de chat
    */
    public void imprimirMensajePropio(String msg, String header){
        txpPanelChat.setEditable(true);
        imprimirEncabezado(header, new Color(52,105,177), "", true, false);
        imprimirContenido(msg, new Color(72,149,209), "", false, false);
        txpPanelChat.setEditable(false);
    }
    
    /*
        asunto
    */
    public void imprimirEncabezado(String header, Color color, String fontFamily, boolean bold, boolean italic){
        if (!header.isEmpty()) {
            int len = txpPanelChat.getDocument().getLength();
            txpPanelChat.setCaretPosition(len);
            txpPanelChat.setCharacterAttributes(Estilos.styleMessageContent(color, fontFamily.isEmpty() ? "Malgun Gothic" : fontFamily, 13, bold, italic), false);
            txpPanelChat.replaceSelection(header+"\n");
        }
    }
    /*
        Contenido
    */
    public void imprimirContenido(String msg, Color color, String fontFamily, boolean bold, boolean italic){
        int len = txpPanelChat.getDocument().getLength();
        txpPanelChat.setCaretPosition(len);
        txpPanelChat.setCharacterAttributes(Estilos.styleMessageContent(color, fontFamily.isEmpty() ? "Microsoft YaHei UI" : fontFamily, 13, bold, italic), false);
        txpPanelChat.replaceSelection(msg +"\n\n");
    }
    
    public void añadirUsuarioConectados(Vector list){
        imprimirListaUsuarios(list); 
    }
    
    /*
        Usuarios en linea
    */
    public void imprimirUsuariosConectados(Vector list){
        try {
            txpUsuariosConectados.setEditable(true);
            txpUsuariosConectados.setContentType("text/html");
            StringBuilder sb = new StringBuilder();
            Iterator it = list.iterator();
            sb.append("<html><table>");
            while(it.hasNext()){
                Object e = it.next();
                URL url = obtenerImagen();
                Icon icon = new ImageIcon(this.getClass().getResource("/images/online.png"));
                sb.append("<tr><td><b>></b></td><td>").append(e).append("</td></tr>");
                System.out.println("Online: "+ e);
            }
            sb.append("</table></body></html>");
            txpUsuariosConectados.removeAll();
            txpUsuariosConectados.setText(sb.toString());
            txpUsuariosConectados.setEditable(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /*
      mostrar la lista usuarios
    */
    private void imprimirListaUsuarios(Vector list){
        listaUsuarios = list;
        txpUsuariosConectados.setEditable(true);
        txpUsuariosConectados.removeAll();
        txpUsuariosConectados.setText("");
        Iterator i = list.iterator();
        while(i.hasNext()){
            Object e = i.next();
            /*  nombre de usuario que envio el mensaje  */
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(Color.white);
            
            Icon icon = new ImageIcon(this.getClass().getResource("/images/usuarioOnline_25.png"));
            JLabel label = new JLabel(icon);
            label.setText(" "+ e);
            panel.add(label);
            int len = txpUsuariosConectados.getDocument().getLength();
            txpUsuariosConectados.setCaretPosition(len);
            txpUsuariosConectados.insertComponent(panel);
            /*  nueva linea luego de cada mensaje   */
            saltarLinea();
        }
        txpUsuariosConectados.setEditable(false);
    }
    private void saltarLinea(){
        int len = txpUsuariosConectados.getDocument().getLength();
        txpUsuariosConectados.setCaretPosition(len);
        txpUsuariosConectados.replaceSelection("\n");
    }
   
    
    
    
    
    /*
        Ruta de la imagen para mostrar la lista
    */
    public URL obtenerImagen(){
        URL url = this.getClass().getResource("/images/online.png");
        return url;
    }
    
    
    /*
        Set myTitle
    */
    public void asignarTitulo(String s){
        setTitle(s);
    }
    
    /*
        descarga
    */
    public String getCarpetaDestino(){
        return this.carpetaDestino;
    }
    
    /*
        
    */
    public String getHost(){
        return this.host;
    }
    
    /*
      
    */
    public int getPort(){
        return this.port;
    }
    
    /*
      
    */
    public String getNombreUsuario(){
        return this.nombreUsuario;
    }
    
    /*
        
    */
    public void actualizarArchivoAdjunto(boolean b){
        this.abrirArchivo = b;
    }
    
    /*
        file chooser
    */
    public void abrirCarpeta(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int open = chooser.showDialog(this, "Carpeta abierta");
        if(open == chooser.APPROVE_OPTION){
            carpetaDestino = chooser.getSelectedFile().toString()+"\\";
        } else {
            carpetaDestino = "D:\\";
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

        spcChat = new javax.swing.JScrollPane();
        txpPanelChat = new javax.swing.JTextPane();
        txtMensaje = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        spcUsuarios = new javax.swing.JScrollPane();
        txpUsuariosConectados = new javax.swing.JTextPane();
        lblUsuariosConectados = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnArchivo = new javax.swing.JButton();
        mnbOpciones = new javax.swing.JMenuBar();
        btnMenuCerrarSesion = new javax.swing.JMenu();
        btnCerrarSesion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));

        txpPanelChat.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        spcChat.setViewportView(txpPanelChat);

        txtMensaje.setToolTipText("");
        txtMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMensajeActionPerformed(evt);
            }
        });

        btnEnviar.setOpaque(false);
        btnEnviar.setFocusPainted(false);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setContentAreaFilled(false);
        btnEnviar.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/imgEnviar.png"))); // NOI18N
        btnEnviar.setBorderPainted(false);
        btnEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEnviar.setEnabled(false);
        btnEnviar.setFocusPainted(false);
        btnEnviar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEnviar.setIconTextGap(0);
        btnEnviar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEnviar.setMaximumSize(new java.awt.Dimension(566, 506));
        btnEnviar.setPreferredSize(new java.awt.Dimension(25, 25));
        btnEnviar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/imgEnviar_press.png"))); // NOI18N
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        txpUsuariosConectados.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        txpUsuariosConectados.setForeground(new java.awt.Color(120, 14, 3));
        txpUsuariosConectados.setAutoscrolls(false);
        txpUsuariosConectados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        spcUsuarios.setViewportView(txpUsuariosConectados);

        lblUsuariosConectados.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblUsuariosConectados.setForeground(new java.awt.Color(53, 112, 188));
        lblUsuariosConectados.setText("Usuarios conectados");

        btnArchivo.setOpaque(false);
        btnArchivo.setFocusPainted(false);
        btnArchivo.setBorderPainted(false);
        btnArchivo.setContentAreaFilled(false);
        btnArchivo.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        btnArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/archivo_25.png"))); // NOI18N
        btnArchivo.setBorderPainted(false);
        btnArchivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnArchivo.setFocusPainted(false);
        btnArchivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnArchivo.setIconTextGap(0);
        btnArchivo.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnArchivo.setMaximumSize(new java.awt.Dimension(566, 506));
        btnArchivo.setPreferredSize(new java.awt.Dimension(25, 25));
        btnArchivo.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/archivo_presionado_25.png"))); // NOI18N
        btnArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchivoActionPerformed(evt);
            }
        });

        btnMenuCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/config.png"))); // NOI18N
        btnMenuCerrarSesion.setText("Configuración");
        btnMenuCerrarSesion.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/config_press.png"))); // NOI18N

        btnCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });
        btnMenuCerrarSesion.add(btnCerrarSesion);

        mnbOpciones.add(btnMenuCerrarSesion);

        setJMenuBar(mnbOpciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuariosConectados)
                    .addComponent(spcUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spcChat, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(spcChat, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(btnArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMensaje)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUsuariosConectados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spcUsuarios)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer salir de la sesión?");
        if(confirm == 0){
            try {
                socket.close();
                setVisible(false);
                /** Login Form **/
                new FormularioLogin().setVisible(true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void txtMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeActionPerformed
        // TODO add your handling code here:
        try {
            String content = nombreUsuario+" "+ evt.getActionCommand();
            dataOutputStream.writeUTF("CMD_CHATALL "+ content);
            imprimirMensajePropio(" "+evt.getActionCommand(), nombreUsuario);
            txtMensaje.setText("");
        } catch (IOException e) {
            imprimirMensaje(" No se pudo enviar por falta de conexión!", "Error", Color.RED, Color.RED, "", true, true, "", false, true);
        }
    }//GEN-LAST:event_txtMensajeActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // TODO add your handling code here:
        try {
            String content = nombreUsuario+" "+ txtMensaje.getText();
            dataOutputStream.writeUTF("CMD_CHATALL "+ content);
            imprimirMensajePropio(" "+txtMensaje.getText(), nombreUsuario);
            txtMensaje.setText("");
        } catch (IOException e) {
            imprimirMensaje(" No se pudo enviar por falta de conexión!", "Error", Color.RED, Color.RED, "", true, true, "", false, true);
        }
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivoActionPerformed
        if(!abrirArchivo){
            FormularioEnviarArchivos s = new FormularioEnviarArchivos();
            if(s.preparar(nombreUsuario, host, port, this, listaUsuarios)){
                s.setLocationRelativeTo(null);
                s.setVisible(true);
                abrirArchivo = true;
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo compartir el archivo!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnArchivoActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnArchivo;
    private javax.swing.JMenuItem btnCerrarSesion;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JMenu btnMenuCerrarSesion;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblUsuariosConectados;
    private javax.swing.JMenuBar mnbOpciones;
    private javax.swing.JScrollPane spcChat;
    private javax.swing.JScrollPane spcUsuarios;
    private javax.swing.JTextPane txpPanelChat;
    private javax.swing.JTextPane txpUsuariosConectados;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables

    private boolean isEmptyString(String header) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
