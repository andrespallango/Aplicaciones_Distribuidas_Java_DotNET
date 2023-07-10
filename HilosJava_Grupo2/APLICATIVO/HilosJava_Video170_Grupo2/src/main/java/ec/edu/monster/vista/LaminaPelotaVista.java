package ec.edu.monster.vista;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import ec.edu.monster.controlador.Controlador;
import ec.edu.monster.modelo.Pelota;

class LaminaPelotaVista extends JPanel {
    private final ArrayList<Pelota> arrPelotas = new ArrayList<>();
    private final Controlador pelotaController = new Controlador();
    
    public void agregarPelota(Pelota pelota) {
        arrPelotas.add(pelota);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        arrPelotas.forEach(p -> g2.fill(pelotaController.obtenerForma(p)));
    }
}