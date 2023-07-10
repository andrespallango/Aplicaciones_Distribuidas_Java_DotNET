/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.controlador;

import ec.edu.monster.modelo.Banco;
import ec.edu.monster.vista.BancoVista;

/**
 *
 * @author Adrian Mosquera
 */
public class BancoControlador {
    private BancoVista bancoView = new BancoVista();
    
    public void acreditarSaldoCuentas(Banco banco, double saldo) {
        double[] cuentasAcreditadas = new double[100];
        for (int i = 0; i < cuentasAcreditadas.length; i++) {
            cuentasAcreditadas[i] = saldo;
        }

        banco.cuentas = cuentasAcreditadas;
    }

    public void transferirEntreCuentas(Banco banco, int cuentaOrigen, int cuentaDestino, double cantidad) {
        if (banco.cuentas[cuentaOrigen] < cantidad) {
            return;
        }

        banco.cuentas[cuentaOrigen] -= cantidad;

        bancoView.mostrarDetallesTransferencia(cuentaOrigen, cuentaDestino, cantidad);

        banco.cuentas[cuentaDestino] += cantidad;
        
        bancoView.mostrarSaldoTotal(obtenerSaldoTotal(banco));
    }

    public double obtenerSaldoTotal(Banco banco) {
        double saldoTotal = 0;

        for (double saldo : banco.cuentas) {
            saldoTotal += saldo;
        }

        return saldoTotal;
    }
}
