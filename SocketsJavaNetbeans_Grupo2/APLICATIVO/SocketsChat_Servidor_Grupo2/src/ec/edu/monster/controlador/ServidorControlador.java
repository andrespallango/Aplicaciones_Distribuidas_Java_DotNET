/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.edu.monster.controlador;

import ec.edu.monster.controlador.SocketsControlador;
import ec.edu.monster.vista.FormularioPrincipal;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author USER
 */
public class ServidorControlador implements Runnable {
    
    ServerSocket server;
    FormularioPrincipal main;
    boolean keepGoing = true;
    
    public ServidorControlador(int port, FormularioPrincipal main){
        main.imprimirMensaje("[Servidor]: Corriendo en el puerto " + port + ". Cargando... ");
        try {
            this.main = main;
            server = new ServerSocket(port);
            main.imprimirMensaje("[Servidor]: Servidor iniciado exitosamente");
        } 
        catch (IOException e) { main.imprimirMensaje("[IOException]: "+ e.getMessage()); }
    }

    @Override
    public void run() {
        try {
            while(keepGoing){
                Socket socket = server.accept();

                
                new Thread(new SocketsControlador(socket, main)).start();
            }
        } catch (IOException e) {
            main.imprimirMensaje("[ServerThreadIOException]: "+ e.getMessage());
        }
    }
    
    
    public void stop(){
        try {
            server.close();
            keepGoing = false;
            System.out.println("Servidor apagado, se han cortado todas las conexiones");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
