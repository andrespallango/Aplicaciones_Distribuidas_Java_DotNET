/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.controlador;

import ec.edu.monster.modelo.Cliente;
import ec.edu.monster.vista.FormularioPrincipal;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author USER
 */
public class SocketsControlador implements Runnable {

    Socket socket;
    FormularioPrincipal main;
    DataInputStream dis;
    StringTokenizer st;
    String cliente, nombreClienteArchivo;

    private final int BUFFER_SIZE = 100;

    public SocketsControlador(Socket socket, FormularioPrincipal main) {
        this.main = main;
        this.socket = socket;

        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            main.imprimirMensaje("[SocketThreadIOException]: " + e.getMessage());
        }
    }

    private void crearConexion(String receiver, String sender, String filename) {
        try {
            main.imprimirMensaje("[Conexion creada]: Conexión creada entre los usuarios para la tranferencia de archivos");
            Socket s = main.obtenerClientes(receiver);
            if (s != null) { // Client đã tồn tại
                main.imprimirMensaje("[Conexion creada]: Socket OK");
                DataOutputStream dosS = new DataOutputStream(s.getOutputStream());
                main.imprimirMensaje("[createConnection]: DataOutputStream OK");
              
                String format = "CMD_FILE_XD " + sender + " " + receiver + " " + filename;
                dosS.writeUTF(format);
                main.imprimirMensaje("[Conexion creada]: " + format);
            } else {
                main.imprimirMensaje("[Conexion creada]: El cliente solicitado no fue encontrado '" + receiver + "'");
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("CMD_SENDFILEERROR " + "El destinatario '" + receiver + "' no se encuentra conectado");
            }
        } catch (IOException e) {
            main.imprimirMensaje("[createConnection]: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                /**
                 * Recibir datos del cliente *
                 */
                String data = dis.readUTF();
                st = new StringTokenizer(data);
                String CMD = st.nextToken();
                /**
                 * Verificando CMD para la tranferencia *
                 */
                switch (CMD) {
                    case "CMD_JOIN":
                        /**
                         * CMD_JOIN [clientUsername] *
                         */
                        String nombreCliente = st.nextToken();
                        cliente = nombreCliente;
                        main.añadirClientes(nombreCliente);
                        main.añadirSockets(socket);
                        main.imprimirMensaje("[Client]: " + nombreCliente + " Se ha unido al chat!");
                        break;


                    case "CMD_CHAT":
                        
                        String desde = st.nextToken();
                        String para = st.nextToken();
                        String msg = "";
                        while (st.hasMoreTokens()) {
                            msg = msg + " " + st.nextToken();
                        }
                        Socket tsoc = main.obtenerClientes(para);
                        try {
                            DataOutputStream dos = new DataOutputStream(tsoc.getOutputStream());
                           
                            String content = desde + ": " + msg;
                            dos.writeUTF("CMD_MESSAGE " + content);
                            main.imprimirMensaje("[Message]:De " + desde + " para " + para + " : " + msg);
                        } catch (IOException e) {
                            main.imprimirMensaje("[IOException]: No se puede enviar el mensaje a " + para);
                        }
                        break;




                    case "CMD_CHATALL":
                       
                        String chatall_from = st.nextToken();
                        String chatall_msg = "";
                        while (st.hasMoreTokens()) {
                            chatall_msg = chatall_msg + " " + st.nextToken();
                        }
                        String chatall_content = chatall_from + " " + chatall_msg;
                        for (int x = 0; x < main.clientes.listaClientes.size(); x++) {
                            if (!main.clientes.listaClientes.elementAt(x).equals(chatall_from)) {
                                try {
                                    Socket tsoc2 = (Socket) main.clientes.listaSockets.elementAt(x);
                                    DataOutputStream dos2 = new DataOutputStream(tsoc2.getOutputStream());
                                    dos2.writeUTF("CMD_MESSAGE " + chatall_content);
                                } catch (IOException e) {
                                    main.imprimirMensaje("[CMD_CHATALL]: " + e.getMessage());
                                }
                            }
                        }
                        main.imprimirMensaje("[CMD_CHATALL]: " + chatall_content);
                        break;

                    case "CMD_SHARINGSOCKET":
                        main.imprimirMensaje("CMD_SHARINGSOCKET : Estableciendo conexión para el intercambio de archivos...");
                        String file_sharing_username = st.nextToken();
                        nombreClienteArchivo = file_sharing_username;
                        main.añadirClientesArchivos(file_sharing_username);
                        main.añadirSocketsClientesArchivos(socket);
                        main.imprimirMensaje("CMD_SHARINGSOCKET : Usuario: " + file_sharing_username);
                        main.imprimirMensaje("CMD_SHARINGSOCKET : Aceptar la tranferencia de archivos");
                        break;

                    case "CMD_SENDFILE":
                        main.imprimirMensaje("CMD_SENDFILE : El cliente está enviando un archivo...");
                        /*
                         Format: CMD_SENDFILE [Filename] [Size] [Recipient] [Consignee]  desde: Sender Format
                         Format: CMD_SENDFILE [Filename] [Size] [Consignee] to Receiver Format
                         */
                        String file_name = st.nextToken();
                        String filesize = st.nextToken();
                        String sendto = st.nextToken();
                        String consignee = st.nextToken();
                        main.imprimirMensaje("CMD_SENDFILE : De: " + consignee);
                        main.imprimirMensaje("CMD_SENDFILE : Para: " + sendto);
                        /**
                         * Obtener el socket del cliente
                         */
                        main.imprimirMensaje("CMD_SENDFILE : Servidor listo para conexiones..");
                        Socket cSock = main.obtenerSocketsClientesArchivos(sendto); /* Consignee Socket  */
                        /*   Verificar si existe conexion con el destinatario   */

                        if (cSock != null) { /* Exists   */

                            try {
                                main.imprimirMensaje("CMD_SENDFILE : Se ha establecido la conexión");
                               
                                main.imprimirMensaje("CMD_SENDFILE : Enviando archivo...");
                                DataOutputStream cDos = new DataOutputStream(cSock.getOutputStream());
                                cDos.writeUTF("CMD_SENDFILE " + file_name + " " + filesize + " " + consignee);
                               
                                InputStream input = socket.getInputStream();
                                OutputStream sendFile = cSock.getOutputStream();
                                byte[] buffer = new byte[BUFFER_SIZE];
                                int cnt;
                                while ((cnt = input.read(buffer)) > 0) {
                                    sendFile.write(buffer, 0, cnt);
                                }
                                sendFile.flush();
                                sendFile.close();
                               
                                main.quitarClienteArchivo(sendto);
                                main.quitarClienteArchivo(consignee);
                                main.imprimirMensaje("CMD_SENDFILE : El archivo fue enviado exitosamente");
                            } catch (IOException e) {
                                main.imprimirMensaje("[CMD_SENDFILE]: " + e.getMessage());
                            }
                        } else { 
                            

                            main.quitarClienteArchivo(consignee);
                            main.imprimirMensaje("CMD_SENDFILE : Client '" + sendto + "Extraviado");
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeUTF("CMD_SENDFILEERROR " + "Client '" + sendto + "' no se encuentra, se cerrará el uso compartido de archivos.");
                        }
                        break;

                    case "CMD_SENDFILERESPONSE":
                        
                        String receiver = st.nextToken(); 
                        String rMsg = ""; 
                        main.imprimirMensaje("[CMD_SENDFILERESPONSE]: Usuario: " + receiver);
                        while (st.hasMoreTokens()) {
                            rMsg = rMsg + " " + st.nextToken();
                        }
                        try {
                            Socket rSock = (Socket) main.obtenerSocketsClientesArchivos(receiver);
                            DataOutputStream rDos = new DataOutputStream(rSock.getOutputStream());
                            rDos.writeUTF("CMD_SENDFILERESPONSE" + " " + receiver + " " + rMsg);
                        } catch (IOException e) {
                            main.imprimirMensaje("[CMD_SENDFILERESPONSE]: " + e.getMessage());
                        }
                        break;

                    case "CMD_SEND_FILE_XD":                         
                        try {
                            String send_sender = st.nextToken();
                            String send_receiver = st.nextToken();
                            String send_filename = st.nextToken();
                            main.imprimirMensaje("[CMD_SEND_FILE_XD]: Host: " + send_sender);
                            this.crearConexion(send_receiver, send_sender, send_filename);
                        } catch (Exception e) {
                            main.imprimirMensaje("[CMD_SEND_FILE_XD]: " + e.getLocalizedMessage());
                        }
                        break;

                    case "CMD_SEND_FILE_ERROR":  
                        String eReceiver = st.nextToken();
                        String eMsg = "";
                        while (st.hasMoreTokens()) {
                            eMsg = eMsg + " " + st.nextToken();
                        }
                        try {
                           
                            Socket eSock = main.obtenerSocketsClientesArchivos(eReceiver); 
                            DataOutputStream eDos = new DataOutputStream(eSock.getOutputStream());
                           
                            eDos.writeUTF("CMD_RECEIVE_FILE_ERROR " + eMsg);
                        } catch (IOException e) {
                            main.imprimirMensaje("[CMD_RECEIVE_FILE_ERROR]: " + e.getMessage());
                        }
                        break;

                    case "CMD_SEND_FILE_ACCEPT": 
                        String aReceiver = st.nextToken();
                        String aMsg = "";
                        while (st.hasMoreTokens()) {
                            aMsg = aMsg + " " + st.nextToken();
                        }
                        try {
                            
                            Socket aSock = main.obtenerSocketsClientesArchivos(aReceiver); 
                            DataOutputStream aDos = new DataOutputStream(aSock.getOutputStream());
                            
                            aDos.writeUTF("CMD_RECEIVE_FILE_ACCEPT " + aMsg);
                        } catch (IOException e) {
                            main.imprimirMensaje("[CMD_RECEIVE_FILE_ERROR]: " + e.getMessage());
                        }
                        break;

                    default:
                        main.imprimirMensaje("[CMDException]: El comando ingresado para la tranferencia es desconocido " + CMD);
                        break;
                }
            }
        } catch (IOException e) {
           
            System.out.println(cliente);
            System.out.println("Archivo compartido: " + nombreClienteArchivo);
            main.quitarClienteLista(cliente);
            if (nombreClienteArchivo != null) {
                main.quitarClienteArchivo(nombreClienteArchivo);
            }
            main.imprimirMensaje("[SocketThread]: La conexión creada con el cliente esta cerrada");
        }
    }

}
