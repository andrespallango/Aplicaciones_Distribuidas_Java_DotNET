/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.controlador;

import ec.edu.monster.modelo.Banco;
import ec.edu.monster.vista.BancoVista;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Adrian Mosquera
 */
public class BancoControlador {
    private Condition saldoSuficiente;
    private Lock cierreBanco = new ReentrantLock();
    private BancoVista bancoView = new BancoVista();

    public void acreditarSaldoCuentas(Banco banco, double saldo) {
        double[] cuentasAcreditadas = new double[100];
        for (int i = 0; i < cuentasAcreditadas.length; i++) {
            cuentasAcreditadas[i] = saldo;
        }

        banco.cuentas = cuentasAcreditadas;
        saldoSuficiente = cierreBanco.newCondition();
    }

    public void transferirEntreCuentas(Banco banco, int cuentaOrigen, int cuentaDestino, double cantidad)
        throws InterruptedException {

        cierreBanco.lock();
        try {
            while (banco.cuentas[cuentaOrigen] < cantidad) {
                saldoSuficiente.await();
            }

            bancoView.mostrarTransferenciaExitosa(cuentaOrigen);
            banco.cuentas[cuentaOrigen] -= cantidad;
            banco.cuentas[cuentaDestino] += cantidad;

            bancoView.mostrarDetallesTransferencia(banco, cuentaOrigen, cuentaDestino, cantidad,
                    obtenerSaldoTotal(banco));
            
            saldoSuficiente.signalAll();
        } finally {
            cierreBanco.unlock();
        }
    }

    public double obtenerSaldoTotal(Banco banco) {
        double saldoTotal = 0;

        for (double saldo : banco.cuentas) {
            saldoTotal += saldo;
        }

        return saldoTotal;
    }
}
