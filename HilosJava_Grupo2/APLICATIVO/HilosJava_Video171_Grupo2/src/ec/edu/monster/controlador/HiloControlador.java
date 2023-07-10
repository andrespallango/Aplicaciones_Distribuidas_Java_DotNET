package ec.edu.monster.controlador;

import ec.edu.monster.modelo.HiloModelo;
import ec.edu.monster.vista.HiloVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloControlador implements ActionListener  {
    HiloVista hiloVista;
    HiloModelo hiloModelo;
    public HiloControlador(HiloVista vista,HiloModelo modelo){
        this.hiloVista=vista;
        this.hiloModelo=modelo;
        this.hiloVista.jbtnIniciar.addActionListener(this);
        iniciarComponentes();
    }
    
    public void iniciarComponentes(){
        hiloVista.jlabel.setText("Hola");
        hiloVista.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          if (e.getSource() == hiloVista.jbtnIniciar) {
              try {
                  jbtnIniciarHilosActionPerformed();
              } catch (InterruptedException ex) {
                  Logger.getLogger(HiloControlador.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
    }
    private void jbtnIniciarHilosActionPerformed() throws InterruptedException{
       hiloModelo.iniciarHilos();
    }
}
