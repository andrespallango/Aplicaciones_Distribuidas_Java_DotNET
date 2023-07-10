/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.controlador;

import ec.edu.monster.modelo.Banco;

/**
 *
 * @author Adrian Mosquera
 */
public class TransferenciasControlador implements Runnable {

    private Banco banco;
    private BancoControlador bancoControlador;
    private int cuentaOrigen;
    private double valorMaximo;

    public TransferenciasControlador(Banco banco, BancoControlador bancoControlador, int cuentaOrigen, double valorMaximo) {
        this.banco = banco;
        this.bancoControlador = bancoControlador;
        this.cuentaOrigen = cuentaOrigen;
        this.valorMaximo = valorMaximo;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int cuentaDestino = (int) (100 * Math.random());
                double cantidad = this.valorMaximo * Math.random();

                this.bancoControlador.transferirEntreCuentas(
                        this.banco,
                        this.cuentaOrigen,
                        cuentaDestino,
                        cantidad
                );
                Thread.sleep((int) (Math.random() * 10));
            }
        } catch (InterruptedException e1) {
        }
    }
}
