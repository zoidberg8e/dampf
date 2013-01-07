package gmanager;

import javax.swing.SwingUtilities;

/**
 *
 * @author doldpa
 */
public class GManager {
    
    private User user;
    
    public GManager(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return user;
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
