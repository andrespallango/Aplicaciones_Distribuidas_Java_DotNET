/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ec.edu.monster.main;

import ec.edu.monster.controlador.BancoControlador;
import ec.edu.monster.controlador.TransferenciasControlador;
import ec.edu.monster.modelo.Banco;

/**
 *
 * @author Adrian Mosquera
 */
public class HilosJava_Video176_Grupo2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Banco banco = new Banco();
        BancoControlador bancoControlador = new BancoControlador();

        bancoControlador.acreditarSaldoCuentas(banco, 2000);

        for (int i = 0; i < 10; i++) {
            TransferenciasControlador transferencias 
                    = new TransferenciasControlador(banco, bancoControlador, i, 2000);
            new Thread(transferencias).start();
        }
    }
    
}
