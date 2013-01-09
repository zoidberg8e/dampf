package gmanager;

import javax.swing.SwingUtilities;

/**
 *
 * @author doldpa
 */
public class GManager {
    
    private User user;
    private long startTime = -1;
    private boolean recordingTime = false;
    
    public GManager(User user) {
        this.user = user;
    }
    
    public void startTime() {
        startTime = System.currentTimeMillis();
        recordingTime = true;
    }
    
    public long stopTime() {
        if (!recordingTime) {
            return 0;
        }
        long stopTime = System.currentTimeMillis();
        recordingTime = false;
        startTime = -1;
        return (stopTime - startTime);
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
