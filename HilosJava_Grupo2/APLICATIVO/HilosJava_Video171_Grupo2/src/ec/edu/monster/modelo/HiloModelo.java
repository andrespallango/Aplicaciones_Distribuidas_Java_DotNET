
package ec.edu.monster.modelo;

public class HiloModelo{
    
    HilosVarios hilo1;
    HilosVarios hilo2;
    
    public HiloModelo(){
        hilo1=new HilosVarios();
        hilo2=new HilosVarios();
    }
    
    public void iniciarHilos() throws InterruptedException{
        hilo1.start();
        hilo1.join();
        hilo2.start();
        hilo2.join();
        System.out.println("Terminadas las tareas");
    }
}
