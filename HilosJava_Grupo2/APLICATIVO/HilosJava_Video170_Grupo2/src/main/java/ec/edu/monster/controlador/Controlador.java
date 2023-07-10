/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  ec.edu.monster.controlador;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import ec.edu.monster.modelo.Pelota;

/**
 *
 * @author USER
 */
public class Controlador {
    public void moverPelota(Rectangle2D limites, Pelota pelota) {
        pelota.x += pelota.dx;
        pelota.y += pelota.dy;

        if (pelota.x < limites.getMinX()) {
            pelota.x = limites.getMinX();
            pelota.dx = -pelota.dx;
        }

        if (pelota.x + Pelota.TAMX >= limites.getMaxX()) {
            pelota.x = limites.getMaxX() - Pelota.TAMX;
            pelota.dx = -pelota.dx;
        }

        if (pelota.y < limites.getMinY()) {
            pelota.y = limites.getMinY();
            pelota.dy = -pelota.dy;
        }

        if (pelota.y + Pelota.TAMY >= limites.getMaxY()) {
            pelota.y = limites.getMaxY() - Pelota.TAMY;
            pelota.dy = -pelota.dy;
        }
    }

    public Ellipse2D obtenerForma(Pelota pelota) {
        return new Ellipse2D.Double(
                pelota.x,
                pelota.y,
                Pelota.TAMX,
                Pelota.TAMY);
    }
}
