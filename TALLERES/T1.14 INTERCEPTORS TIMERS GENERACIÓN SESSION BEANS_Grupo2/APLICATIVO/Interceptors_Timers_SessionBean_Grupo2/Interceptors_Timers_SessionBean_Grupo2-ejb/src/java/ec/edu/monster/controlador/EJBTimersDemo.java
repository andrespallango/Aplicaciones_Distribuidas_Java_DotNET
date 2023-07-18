/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ec.edu.monster.controlador;

import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author GRUPO1
 */
@Stateless
public class EJBTimersDemo{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Schedule(hour = "*", minute = "*", second = "*/30")
    public void logMessage(){
        System.out.println("Mensaje Log() método invocado a las: "
            + new Date(System.currentTimeMillis()));
    }
}
