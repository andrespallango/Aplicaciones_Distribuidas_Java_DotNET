/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.vista;

import ec.edu.monster.modelo.Banco;

/**
 *
 * @author Adrian Mosquera
 */
public class BancoVista {
    
    public void mostrarDetallesTransferencia(
            int cuentaOrigen,
            int cuentaDestino,
            double cantidad
    ) {
        System.out.println(Thread.currentThread() + "\n");
        System.out.printf("%10.2f de %d para %d\n", cantidad, cuentaOrigen, cuentaDestino);
    }
    
    public void mostrarSaldoTotal(
            double saldoTotal
    ) {
        System.out.printf("Saldo Total: %10.2f\n", saldoTotal);
    }
}
