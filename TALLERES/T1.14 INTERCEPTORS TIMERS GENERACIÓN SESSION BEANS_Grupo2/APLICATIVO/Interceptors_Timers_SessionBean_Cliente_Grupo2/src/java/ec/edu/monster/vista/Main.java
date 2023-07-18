
package ec.edu.monster.vista;

import javax.ejb.EJB;
import javax.swing.JOptionPane;

/**
 *
 * @author GRUPO1
 */
public class Main {

    @EJB
    private static RepetirRemote repetir;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JOptionPane.showMessageDialog(null, repetir.repetir("Si sale este mensaje todo salio bien"));
    }
}
