package ec.edu.monster.controlador;

import ec.edu.monster.modelo.HilosModelo;
import ec.edu.monster.vista.HilosVista;

public class HilosVarios2Controlador extends Thread {
    private HilosModelo hiloModel;

    public HilosVarios2Controlador(HilosModelo hiloModel) {
        this.hiloModel = hiloModel;
    }

    public void run() {
        HilosVista vista = new HilosVista();
        try {
            hiloModel.hilo1.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 15; i++) {
            vista.imprimirInfoHilo(getName());
            try {
                Thread.sleep(700);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
