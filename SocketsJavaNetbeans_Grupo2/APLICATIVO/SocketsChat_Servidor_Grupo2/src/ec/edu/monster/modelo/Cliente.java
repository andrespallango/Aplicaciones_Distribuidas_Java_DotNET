/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.modelo;

import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author User
 */
public class Cliente {
    public Vector listaSockets = new Vector();
    public Vector listaClientes = new Vector();

    public Cliente() {
    }

    /**
     * @return the listaSockets
     */
    public Vector getListaSockets() {
        return listaSockets;
    }

    /**
     * @param listaSockets the listaSockets to set
     */
    public void setListaSockets(Vector listaSockets) {
        this.listaSockets = listaSockets;
    }

    /**
     * @return the listaClientes
     */
    public Vector getListaClientes() {
        return listaClientes;
    }

    /**
     * @param listaClientes the listaClientes to set
     */
    public void setListaClientes(Vector listaClientes) {
        this.listaClientes = listaClientes;
    }
    
}
