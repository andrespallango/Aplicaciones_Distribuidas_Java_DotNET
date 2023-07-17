/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.edu.monster.controlador;

import ec.edu.monster.vista.FormularioPrincipal;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author USER
 */
public class ClientesControlador implements Runnable {
    
    FormularioPrincipal main;
    
    public ClientesControlador(FormularioPrincipal main){
        this.main = main;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                String msg = "";
                for(int x=0; x < main.clientes.listaClientes.size(); x++){
                    msg = msg+" "+ main.clientes.listaClientes.elementAt(x);
                }
                
                for(int x=0; x < main.clientes.listaSockets.size(); x++){
                    Socket tsoc = (Socket) main.clientes.listaSockets.elementAt(x);
                    DataOutputStream dos = new DataOutputStream(tsoc.getOutputStream());
                    /** CMD_ONLINE [user1] [user2] [user3] **/
                    if(msg.length() > 0){
                        dos.writeUTF("CMD_ONLINE "+ msg);
                    }
                }
                
                Thread.sleep(1900);
            }
        } catch(InterruptedException e){
            main.imprimirMensaje("[InterruptedException]: "+ e.getMessage());
        } catch (IOException e) {
            main.imprimirMensaje("[IOException]: "+ e.getMessage());
        }
    }
    
    
}
