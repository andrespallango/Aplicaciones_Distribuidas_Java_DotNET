package ec.edu.monster.main;

import ec.edu.monster.controlador.HilosVarios2Controlador;
import ec.edu.monster.controlador.HilosVariosControlador;
import ec.edu.monster.modelo.HilosModelo;
import ec.edu.monster.vista.HilosVista;

/**
 *
 * @author Mosquera - Pallango - Sánchez
 */
public class HilosJava_Video172_grupo2 {

    public static void main(String[] args) {
        HilosVista vista = new HilosVista();
        HilosModelo modelo = new HilosModelo();
        
        HilosVariosControlador hilo1 = new HilosVariosControlador();
        
        modelo.hilo1 = hilo1;
        
        HilosVarios2Controlador hilo2 = new HilosVarios2Controlador(modelo);

        hilo2.start();
        hilo1.start();

        vista.imprimirFinalizado();
    }
}
