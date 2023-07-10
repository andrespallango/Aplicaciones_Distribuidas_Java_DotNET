/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.vista;

import ec.edu.monster.modelo.PelotaHilos;
import  ec.edu.monster.controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ec.edu.monster.modelo.Pelota;

/**
 *
 * @author USER
 */

public class Vista extends JFrame {
    public Pelota pelotaModel;
    private final LaminaPelotaVista panel;
    public Controlador pelotaController;
    private Thread hiloPelota1, hiloPelota2, hiloPelota3;
    private JButton btnDetenerPelota1, btnDetenerPelota2, btnDetenerPelota3;
    private JButton btnComenzarPelota1, btnComenzarPelota2, btnComenzarPelota3;

    public Vista() {
        JPanel panelBotones = new JPanel();
        panel               = new LaminaPelotaVista();
        pelotaController    = new Controlador();

        setBounds(800, 500, 1000, 550);
        setTitle("Pelotas Hilos");

        btnComenzarPelota1 = obtenerBoton("Pelota 1", event -> comenzarJuego(1));
        btnComenzarPelota2 = obtenerBoton("Pelota 2", event -> comenzarJuego(2));
        btnComenzarPelota3 = obtenerBoton("Pelota 3", event -> comenzarJuego(3));

        btnDetenerPelota1 = obtenerBoton("Detener 1", event -> detener(1));
        btnDetenerPelota2 = obtenerBoton("Detener 2", event -> detener(2));
        btnDetenerPelota3 = obtenerBoton("Detener 3", event -> detener(3));

        panelBotones.add(btnComenzarPelota1);
        panelBotones.add(btnComenzarPelota2);
        panelBotones.add(btnComenzarPelota3);
        panelBotones.add(btnDetenerPelota1);
        panelBotones.add(btnDetenerPelota2);
        panelBotones.add(btnDetenerPelota3);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JButton obtenerBoton(String titulo, ActionListener listener) {
        JButton boton = new JButton(titulo);
        boton.addActionListener(listener);
        return boton;
    }

    private void comenzarJuego(int numBoton) {
        pelotaModel = new Pelota();

        panel.agregarPelota(pelotaModel);

        Runnable runnablePelota = new PelotaHilos(pelotaModel, pelotaController, panel);

        switch (numBoton) {
            case 1:
                hiloPelota1 = new Thread(runnablePelota);
                hiloPelota1.start();
                break;
            case 2:
                hiloPelota2 = new Thread(runnablePelota);
                hiloPelota2.start();
                break;
            case 3:
                hiloPelota3 = new Thread(runnablePelota);
                hiloPelota3.start();
                break;
        }
    }

    private void detener(int numBoton) {
        switch (numBoton) {
            case 1:
                hiloPelota1.interrupt();
                break;
            case 2:
                hiloPelota2.interrupt();
                break;
            case 3:
                hiloPelota3.interrupt();
                break;
        }
    }
}
