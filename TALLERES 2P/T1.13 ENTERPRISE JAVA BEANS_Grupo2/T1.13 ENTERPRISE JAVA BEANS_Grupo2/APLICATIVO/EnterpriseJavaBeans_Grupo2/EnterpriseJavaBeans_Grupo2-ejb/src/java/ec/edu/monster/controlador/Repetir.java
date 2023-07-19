/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ec.edu.monster.controlador;

import javax.ejb.Stateless;

/**
 *
 * @author Andrés
 */
@Stateless
public class Repetir implements RepetirRemote {

    @Override
    public String repetir(String repitiendo) {
        return "repitiendo: " +repitiendo;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
