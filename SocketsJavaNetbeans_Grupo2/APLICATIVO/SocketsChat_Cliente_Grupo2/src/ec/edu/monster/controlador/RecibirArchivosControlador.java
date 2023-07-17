package ec.edu.monster.controlador;


import ec.edu.monster.vista.PantallaPrincipal;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitorInputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class RecibirArchivosControlador implements Runnable {
    
    protected Socket socket;
    protected DataInputStream dis;
    protected DataOutputStream dos;
    protected PantallaPrincipal main;
    protected StringTokenizer st;
    protected DecimalFormat df = new DecimalFormat("##,#00");
    private final int BUFFER_SIZE = 100;
    
    public RecibirArchivosControlador(Socket soc, PantallaPrincipal m){
        this.socket = soc;
        this.main = m;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("[ReceivingFileThread]: " +e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()){
                String data = dis.readUTF();
                st = new StringTokenizer(data);
                String CMD = st.nextToken();
                
                switch(CMD){
                    
                    //Funcion encargada de manejar el envio de archivos
                    case "CMD_SENDFILE":
                        String consignee = null;
                            try {
                                String filename = st.nextToken();
                                int filesize = Integer.parseInt(st.nextToken());
                                consignee = st.nextToken(); 
                                main.asignarTitulo("El archivo se está enviando");
                                System.out.println("El archivo se está enviando");
                                System.out.println("Desde: "+ consignee);
                                String path = main.getCarpetaDestino() + filename;                                
                             
                                FileOutputStream fos = new FileOutputStream(path);
                                InputStream input = socket.getInputStream();                                
                                
                                ProgressMonitorInputStream pmis = new ProgressMonitorInputStream(main, "Descargando, por favor espere ...", input);
                              
                                BufferedInputStream bis = new BufferedInputStream(pmis);
                               
                                byte[] buffer = new byte[BUFFER_SIZE];
                                int count, percent = 0;
                                while((count = bis.read(buffer)) != -1){
                                    percent = percent + count;
                                    int p = (percent / filesize);
                                    main.asignarTitulo("Descargando imágen  "+ p +"%");
                                    fos.write(buffer, 0, count);
                                }
                                fos.flush();
                                fos.close();
                                main.asignarTitulo("Usuario actual: " + main.getNombreUsuario());
                                JOptionPane.showMessageDialog(null, "El archivo fue guardado en \n'"+ path +"'");
                                System.out.println("El archivo se guardó existosamente en "+ path);
                            } catch (IOException e) {
                               
                                DataOutputStream eDos = new DataOutputStream(socket.getOutputStream());
                                eDos.writeUTF("CMD_SENDFILERESPONSE "+ consignee + " Se perdió la conexión, inténtalo de nuevo!");
                                
                                System.out.println(e.getMessage());
                                main.asignarTitulo("Usuario actual: " + main.getNombreUsuario());
                                JOptionPane.showMessageDialog(main, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                                socket.close();
                            }
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("[ReceivingFileThread]: " +e.getMessage());
        }
    }
}

