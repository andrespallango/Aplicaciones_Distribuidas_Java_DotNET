package ec.edu.monster.modelo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HilosVarios extends Thread{
    
    public HilosVarios(){
        
    }
    @Override
    public void run(){
        for(int i=0; i<15;i++){
            System.out.println("Ejecutando Hilo "+getName());
            try {
                Thread.sleep(700);
            } catch (InterruptedException ex) {
                Logger.getLogger(HilosVarios.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        } 
        
    }
  
}
