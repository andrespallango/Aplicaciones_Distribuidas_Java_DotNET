package ec.edu.monster.controlador;


import ec.edu.monster.controlador.RecibirArchivosControlador;
import ec.edu.monster.modelo.Sonidos;
import ec.edu.monster.vista.PantallaPrincipal;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.Vector;
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
public class MensajesControlador implements Runnable{
    
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    PantallaPrincipal main;
    StringTokenizer st;
    protected DecimalFormat df = new DecimalFormat("##,#00");
    
    public MensajesControlador(Socket socket, PantallaPrincipal main){
        this.main = main;
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            main.imprimirMensaje("[IOException]: "+ e.getMessage(), "Error", Color.RED, Color.RED, "", true, true, "", false, true);
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
                    case "CMD_MESSAGE":
                        Sonidos.MessageReceive.reproducir(); //  Play Audio clip
                        String msg = "";
                        String frm = st.nextToken();
                        while(st.hasMoreTokens()){
                            msg = msg +" "+ st.nextToken();
                        }
                        main.imprimirMensaje(msg, frm, Color.BLACK, Color.BLACK, "", true, false, "", false, false);
                        break;
                        
                    case "CMD_ONLINE":
                        Vector online = new Vector();
                        while(st.hasMoreTokens()){
                            String list = st.nextToken();
                            if(!list.equalsIgnoreCase(main.nombreUsuario)){
                                online.add(list);
                            }
                        }
                        main.añadirUsuarioConectados(online);
                        break;
                    
                        
                   
                    case "CMD_FILE_XD": 
                        String sender = st.nextToken();
                        String receiver = st.nextToken();
                        String fname = st.nextToken();
                        int confirm = JOptionPane.showConfirmDialog(main, "De "+sender+"\nNombre archivo: "+fname+"\n¿Quiere aceptar el archivo?");
                       
                        if(confirm == 0){ 
                            main.abrirCarpeta();
                            try {
                                dos = new DataOutputStream(socket.getOutputStream());
                               
                                String format = "CMD_SEND_FILE_ACCEPT "+sender+" Aceptado";
                                dos.writeUTF(format);
                                
                              
                                Socket fSoc = new Socket(main.getHost(), main.getPort());
                                DataOutputStream fdos = new DataOutputStream(fSoc.getOutputStream());
                                fdos.writeUTF("CMD_SHARINGSOCKET "+ main.getNombreUsuario());
                               
                                new Thread(new RecibirArchivosControlador(fSoc, main)).start();
                            } catch (IOException e) {
                                System.out.println("[CMD_FILE_XD]: "+e.getMessage());
                            }
                        } else { 
                            try {
                                dos = new DataOutputStream(socket.getOutputStream());
                               
                                String format = "CMD_SEND_FILE_ERROR "+sender+"El usuario rechazó el envio o se desconecto!";
                                dos.writeUTF(format);
                            } catch (IOException e) {
                                System.out.println("[CMD_FILE_XD]: "+e.getMessage());
                            }
                        }                       
                        break;   
                        
                    default: 
                        main.imprimirMensaje("[CMDException]: Comando inválido "+ CMD, "CMDException", Color.RED, Color.RED, "", true, true, "", false, true);
                    break;
                }
            }
        } catch(IOException e){
            main.imprimirMensaje(" Se ha perdido la conexión con el servidor, verifique el estado", "Error", Color.RED, Color.RED, "", true, true, "", false, true);
        }
    }
}