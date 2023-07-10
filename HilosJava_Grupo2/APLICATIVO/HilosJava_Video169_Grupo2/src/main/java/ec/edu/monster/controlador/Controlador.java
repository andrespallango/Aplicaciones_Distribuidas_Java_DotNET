
package ec.edu.monster.controlador;

import ec.edu.monster.modelo.PelotaHilos;
import ec.edu.monster.modelo.Pelota;
import ec.edu.monster.vista.Vista;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;


public class Controlador {
   Vista vista;
    PelotaHilos modelo;
    Thread t;
   
    public Controlador(Vista v,PelotaHilos mod){
        vista=v;
        IniciarComponentes();
        vista.setVisible(true);
        modelo=mod;
        
    }
    public void IniciarComponentes(){
        ponerBoton(vista.laminaBotones,vista.btnIniciar, "Lanzar Pelota", new ActionListener(){		
			public void actionPerformed(ActionEvent evento){
				LanzarPelota();
			}			
	});
        ponerBoton(vista.laminaBotones,vista.btnSalir, "Salir", new ActionListener(){
			
			public void actionPerformed(ActionEvent evento){				
				System.exit(0);				
			}		
        });
        ponerBoton(vista.laminaBotones,vista.btnDetener, "Parar pelota", new ActionListener(){		
			public void actionPerformed(ActionEvent evento){
				detener();
			}			
	});
 
    }
    
    public void ponerBoton(Container c,JButton btn, String titulo, ActionListener oyente){
		
	btn.setText(titulo);
        c.add(btn);		
		btn.addActionListener(oyente);
		
    }
    
    public void LanzarPelota (){
			
                        Pelota pelota=new Pelota();
                        vista.AñadirPelota(pelota);
			Runnable r = new PelotaHilos(pelota, vista.laminaPelota);
                         t = new Thread(r);
                        t.start();
        }
    
    public void detener(){
            //t.stop();
            t.interrupt();
        }
}
