/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.modelo;

import java.awt.Color;

/**
 *
 * @author USER
 */
public class Pelota {
    public static final int TAMX = 15, TAMY = 15;
    public double x, y, dx, dy;
    public Color color;

    public Pelota() {
        this.x = 0;
        this.y = 0;
        this.dx = 1;
        this.dy = 1;
        this.color = Color.BLUE;
    }
}
