/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package ec.edu.monster.main;
import ec.edu.monster.controlador.Controlador;
import ec.edu.monster.modelo.PelotaHilos;
import ec.edu.monster.vista.Vista;

/**
 *
 * @author USER
 */
public class HilosJava_Video168_grupo2 {

    public static void main(String[] args) {
        Vista vista=new Vista();
                PelotaHilos modelo=new PelotaHilos();
		Controlador controlador=new Controlador(vista,modelo);
    }
} 

