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
    public Controlador(Vista v,PelotaHilos mod){
        vista=v;
        IniciarComponentes();
        vista.setVisible(true);
        modelo=mod;
        
    }
    public void IniciarComponentes(){
        ponerBoton(vista.laminaBotones,vista.btnIniciar, "Lanzar pelota", new ActionListener(){		
			public void actionPerformed(ActionEvent evento){
				LanzarPelota();
			}			
	});
        ponerBoton(vista.laminaBotones,vista.btnSalir, "Terminar", new ActionListener(){
			
			public void actionPerformed(ActionEvent evento){				
				System.exit(0);				
			}		
        });
 
    }
    
    public void ponerBoton(Container c,JButton btn, String titulo, ActionListener accion){
		
	btn.setText(titulo);
        c.add(btn);		
		btn.addActionListener(accion);
		
    }
    public void LanzarPelota(){
			
                        Pelota pelota=new Pelota();
                        vista.AñadirPelota(pelota);
			Runnable r = new PelotaHilos(pelota, vista.laminaPelota);
                        Thread t = new Thread(r);
                        t.start();
        }
}	
	