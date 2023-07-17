package ec.edu.monster.controlador;


import ec.edu.monster.vista.FormularioEnviarArchivos;
import java.awt.Color;
import ec.edu.monster.vista.PantallaPrincipal;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class EnviarArchivosControlador implements Runnable {
    
    protected Socket socket;
    private DataOutputStream dos;
    protected FormularioEnviarArchivos form;
    protected String file;
    protected String receiver;
    protected String sender;
    PantallaPrincipal main;
    protected DecimalFormat df = new DecimalFormat("##,#00");
    private final int BUFFER_SIZE = 100;
    
    public EnviarArchivosControlador(Socket soc, String file, String receiver, String sender, FormularioEnviarArchivos frm, PantallaPrincipal main){
        this.socket = soc;
        this.file = file;
        this.receiver = receiver;
        this.sender = sender;
        this.form = frm;
        this.main = main;
    }

    @Override
    public void run() {
        try {
            form.deshabilitarGUI(true);
            System.out.println("Enviando archivo..!");
            dos = new DataOutputStream(socket.getOutputStream());
          
            File filename = new File(file);
            int len = (int) filename.length();
            int filesize = (int)Math.ceil(len / BUFFER_SIZE); 
            String clean_filename = filename.getName();
            dos.writeUTF("CMD_SENDFILE "+ clean_filename.replace(" ", "_") +" "+ filesize +" "+ receiver +" "+ sender);
            System.out.println("Enviado por: "+ sender);
            System.out.println("Destino: "+ receiver);
           
            InputStream input = new FileInputStream(filename);
            OutputStream output = socket.getOutputStream();
 
            BufferedInputStream bis = new BufferedInputStream(input);
            byte[] buffer = new byte[BUFFER_SIZE];
            int count, percent = 0;
            while((count = bis.read(buffer)) > 0){
                percent = percent + count;
                int p = (percent / filesize);
               
                form.updateProgress(p);
                output.write(buffer, 0, count);
            }
          
            form.setMyTitle("Archivo enviado");
            form.updateAttachment(false); 
            JOptionPane.showMessageDialog(form, "Archivo enviado exitosamente", "Éxito :D", JOptionPane.INFORMATION_MESSAGE);
            form.closeThis();
           
            output.flush();
            output.close();
            System.out.println("Archivo enviado");
            
            main.imprimirMensajePropio("Has enviado el archivo " + filename.getName() + " a " + receiver, sender);
        } catch (IOException e) {
            form.updateAttachment(false);
            System.out.println("[SendFile]: "+ e.getMessage());
        }
    }
}