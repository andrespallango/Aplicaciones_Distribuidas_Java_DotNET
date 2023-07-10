/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.vista;


import ec.edu.monster.modelo.Pelota;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author USER
 */
class LaminaPelotaVista extends JPanel{
	private ArrayList<Pelota> pelotas=new ArrayList<Pelota>();
	//Añadimos pelota a la lámina
	
	public LaminaPelotaVista(){
            
        }
        public void add(Pelota b){
		
		pelotas.add(b);
	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		for(Pelota b: pelotas){
			
			g2.fill(b.getShape());
		}		
	}
		
}