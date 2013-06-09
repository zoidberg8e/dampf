package gmanager;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "LookAndFeel Error", JOptionPane.ERROR_MESSAGE);
        } catch (InstantiationException ex) {
            JOptionPane.showMessageDialog(null, ex, "LookAndFeel Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, ex, "LookAndFeel Error", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, ex, "LookAndFeel Error", JOptionPane.ERROR_MESSAGE);
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //new LoginScreen();
                User u = new User("pdold@gmail.com");
                u.setUserImage("/home/patrickd/Desktop/cartman.png");
                new MainGUI(new GManager(u));
            }
        });
    }
}
