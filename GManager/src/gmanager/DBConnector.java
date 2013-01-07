package gmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author doldpa
 */
public final class DBConnector {
    
    private static final DBConnector INSTANCE = new DBConnector();
    private Connection con;
    
    private DBConnector() {
        /*try {
            con = DriverManager.getConnection("jdbc:");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    public boolean checkLogin(String username, String pw) {
        if (!DBConnector.getInstance().userExists(username)) {
            return false;
        }
        int hashPW = pw.hashCode();
        int hashCorrect = getPassword(username);
        hashCorrect = hashPW;
        if (hashCorrect == hashPW) {
            return true;
        }
        else {
            return false;
        }
    }
    
    private int getPassword(String username) {
        return 1;
    }
    
    public ImageIcon getUserImage(int userID) {
        return new ImageIcon();
    }
    
    public int getUserID(String username) {
        return 12345;
    }
    
    public boolean userExists(String name) {
        return true;
    }
    
    public static DBConnector getInstance() {
        return INSTANCE;
    }
    
    public void createUser(String name, String password) {
        
    }
    
    public String getUsername(int ID) {
        return "Peter Müller";
    }
}
