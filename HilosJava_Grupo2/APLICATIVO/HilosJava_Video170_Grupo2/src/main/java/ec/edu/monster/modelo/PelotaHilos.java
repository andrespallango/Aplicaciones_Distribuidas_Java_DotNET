package ec.edu.monster.modelo;

import ec.edu.monster.controlador.*;
import java.awt.Component;



public class PelotaHilos implements Runnable {
    public Pelota pelota;
    public Controlador pelotaController;
    public Component componente;

    public PelotaHilos(Pelota pelota, Controlador pelotaController, Component componente) {
        this.pelota = pelota;
        this.pelotaController = pelotaController;
        this.componente = componente;
    }

    @Override
    public void run() {
        System.out.println("El hilo está corriendo");
        while (!Thread.currentThread().isInterrupted()) {
            pelotaController.moverPelota(componente.getBounds(), pelota);
            componente.paint(componente.getGraphics());

            try {
                Thread.sleep(4);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("El hilo dejo de correr");
    }
}
