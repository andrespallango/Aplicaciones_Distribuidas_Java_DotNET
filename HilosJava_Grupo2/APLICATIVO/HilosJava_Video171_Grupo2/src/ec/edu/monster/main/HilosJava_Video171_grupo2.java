package ec.edu.monster.main;

import ec.edu.monster.controlador.HiloControlador;
import ec.edu.monster.modelo.HiloModelo;
import ec.edu.monster.vista.HiloVista;

public class HilosJava_Video171_grupo2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HiloVista vista=new HiloVista();
        HiloModelo modelo= new HiloModelo();
        HiloControlador controlador= new HiloControlador(vista,modelo);
        
   }
    
}
