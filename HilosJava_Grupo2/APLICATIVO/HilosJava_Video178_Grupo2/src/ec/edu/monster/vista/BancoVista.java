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
    public void mostrarTransferenciaError(Banco banco, int cuentaOrigen, double cantidad) {
        System.out.println("---------CANTIDAD INSUFICIENTE---------");
        System.out.println("\tCuenta: " + cuentaOrigen);
        System.out.println("\tSaldo: " + banco.cuentas[cuentaOrigen]);
        System.out.println("\tCantidad: " + cantidad);
    }

    public void mostrarTransferenciaExitosa(int cuentaOrigen) {
        System.out.println("---------CANTIDAD OK---------");
        System.out.println("\tCuenta: " + cuentaOrigen);
    }
    
    public void mostrarDetallesTransferencia(
            Banco banco,
            int cuentaOrigen,
            int cuentaDestino,
            double cantidad,
            double saldoTotal
    ) {
        System.out.println(Thread.currentThread() + "\n");
        System.out.printf("%10.2f de %d para %d\n", cantidad, cuentaOrigen, cuentaDestino);
        System.out.printf("Saldo Total: %10.2f\n", saldoTotal);
    }
}
