package gmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.ImageIcon;

public final class DBConnector {
    
    private static final DBConnector INSTANCE = new DBConnector();
    private Connection con;
    
    private DBConnector() {
        String url = "jdbc:postgresql://localhost/dbprojekt";
        Properties props = new Properties();
        props.setProperty("user", "projekt");
        props.setProperty("password", "geheim");
        //props.setProperty("ssl","true");
        
        try {
            con = DriverManager.getConnection(url, props);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean checkLogin(String email, int hashPW) {
        if (!DBConnector.getInstance().emailExists(email)) {
            return false;
        }
        if (getPasswordHash(email) == hashPW) {
            return true;
        }
        else {
            return false;
        }
    }
    
    private int getPasswordHash(String email) {
        try {
            String statement = "SELECT passwort FROM benutzer WHERE email = '" + email + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                String result = rs.getString(1);
                rs.close();
                st.close();
                return Integer.parseInt(result);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
    
    public ImageIcon getUserImage(int userID) {
        return new ImageIcon();
    }
    
    public boolean emailExists(String email) {
        try {
            String statement = "SELECT * FROM benutzer WHERE email = '" + email + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                rs.close();
                st.close();
                return true;
            }
            rs.close();
            st.close();
            return false;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public boolean usernameExists(String name) {
        try {
            String statement = "SELECT * FROM benutzer WHERE name = '" + name + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                rs.close();
                st.close();
                return true;
            }
            rs.close();
            st.close();
            return false;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public static DBConnector getInstance() {
        return INSTANCE;
    }
    
    public boolean createUser(String email, String name, String password) {
        if (!usernameExists(name) && !emailExists(email)) {
            try {
                String statement = "INSERT INTO benutzer(email, name, passwort) " + 
                        "VALUES('" + email + "', '" + name + "', '" + password.hashCode() + "')";
                Statement st = con.createStatement();
                st.executeQuery(statement);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }
    
    public String getUsername(String email) {
        if (!emailExists(email)) {
            return null;
        }
        try {
            String statement = "SELECT name FROM benutzer WHERE email = '" + email + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                return rs.getString(1);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public User[] getFriendRequests(String email) {
        List<User> friends = new ArrayList();
        try {
            String statement = "SELECT benutzer1 FROM freundschaft WHERE benutzer2 = '" + email + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                String user2 = rs.getString(1);
                String statement2 = "SELECT * FROM freundschaft WHERE " + 
                                    "benutzer1 = '" + email + "' AND " +
                                    "benutzer2 = '" + user2 + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(statement2);
                if(!rs2.next()) {
                    friends.add(new User(user2));
                }
                rs2.close();
                st2.close();
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return friends.toArray(new User[friends.size()]);
    }
    
    public User[] getFriends(String email) {
        List<User> friends = new ArrayList();
        try {
            String statement = "SELECT benutzer2 FROM freundschaft WHERE benutzer1 = '" + email + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                String user2 = rs.getString(1);
                String statement2 = "SELECT * FROM freundschaft WHERE " + 
                                    "benutzer1 = '" + user2 + "' AND " +
                                    "benutzer2 = '" + email + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(statement2);
                if(rs2.next()) {
                    friends.add(new User(user2));
                }
                rs2.close();
                st2.close();
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return friends.toArray(new User[friends.size()]);
    }
    
    public User[] getUnansweredRequests(String email) {
        List<User> friends = new ArrayList();
        try {
            String statement = "SELECT benutzer2 FROM freundschaft WHERE benutzer1 = '" + email + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                String user2 = rs.getString(1);
                String statement2 = "SELECT * FROM freundschaft WHERE " + 
                                    "benutzer1 = '" + user2 + "' AND " +
                                    "benutzer2 = '" + email + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(statement2);
                if(!rs2.next()) {
                    friends.add(new User(user2));
                }
                rs2.close();
                st2.close();
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return friends.toArray(new User[friends.size()]);
    }
}
