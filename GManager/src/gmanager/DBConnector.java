package gmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public boolean checkLogin() {
        return true;
    }
    
    public boolean userExists(String name) {
        return false;
    }
    
    public static DBConnector getInstance() {
        return INSTANCE;
    }
    
    public void createUser(String name, String password) {
        
    }
}
