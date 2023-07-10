package ec.edu.monster.controlador;

import ec.edu.monster.vista.HilosVista;

public class HilosVariosControlador extends Thread {
    public void run() {
        HilosVista vista = new HilosVista();
        
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
