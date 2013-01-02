package gmanager;

import javax.swing.SwingUtilities;

/**
 *
 * @author doldpa
 */
public class GManager {
    
    public GManager(int userID) {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginScreen();
            }
        });
    }
}
