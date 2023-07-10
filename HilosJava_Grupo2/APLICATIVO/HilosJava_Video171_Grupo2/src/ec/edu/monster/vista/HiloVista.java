/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.monster.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HiloVista extends JFrame{
    public HiloVista(){
        this.setSize(300,300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
     
        setTitle("Sicronizacion Hilos");
        jpanel=new JPanel();
        jpanel.setLayout(null);
        this.getContentPane().add(jpanel);
        jlabel=new JLabel();
        jbtnIniciar=new JButton("Iniciar Hilos");
        jbtnIniciar.setBounds(100,100,100,30);        
        jpanel.add(jbtnIniciar);
          
    }
    public JPanel jpanel;
    public JLabel jlabel;
    public JButton jbtnIniciar; 
    
}
