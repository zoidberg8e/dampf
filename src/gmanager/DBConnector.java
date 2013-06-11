package gmanager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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
            System.out.println(ex);
        }
    }
    
    public boolean checkLogin(String email, int hashPW) {
        if (!DBConnector.getInstance().emailExists(email)) {
            return false;
        }
        if (getPasswordHash(getUserID(email)) == hashPW) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public int getUserID(String email) {
        int result = -1;
        try {
            String statement = "SELECT benutzerid FROM benutzer WHERE email = '" + email + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                result = rs.getInt("benutzerid");
                rs.close();
                st.close();
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    private int getPasswordHash(int id) {
        try {
            String statement = "SELECT passwort FROM benutzer WHERE benutzerid = '" + id + "'";
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
            System.out.println(ex);
        }
        return -1;
    }
    
    public ImageIcon getUserImage(int id) {
        ImageIcon img = null;
        try {
            String statement = "SELECT bild FROM benutzer WHERE benutzerid = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            

            if (rs.next()) {
                byte[] imgData = rs.getBytes("bild");
                if(imgData != null) {
                    img = new ImageIcon(imgData);
                }
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        if(img != null) {
            return img;
        }
        else {
            return new ImageIcon("sil.jpeg");
        }
    }
    
    public boolean setUserImage(int id, String path) {
        try {
            File imgFile = new File(path);
            FileInputStream fis = new FileInputStream(imgFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);     
            }
            byte[] bytes = bos.toByteArray();
            fis.close();
            bos.close();

            PreparedStatement ps = con.prepareStatement("UPDATE benutzer SET bild=? WHERE benutzerid=?");
            ps.setBytes(1, bytes);
            ps.setInt(2, id);
            ps.executeUpdate();
            
            ps.close();
            } catch (Exception ex) {
                System.out.println(ex);
                return false;
            }
        return true;
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
            System.out.println(ex);
        }
        return false;
    }
    
    private boolean idExists(int id) {
        try {
            String statement = "SELECT * FROM benutzer WHERE benutzerid = '" + id + "'";
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
            System.out.println(ex);
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
            System.out.println(ex);
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
                st.executeUpdate(statement);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return false;
    }
    
    public String getUserName(int id) {
        if(!idExists(id)) {
            return null;
        }
        try {
            String statement = "SELECT benutzername FROM benutzer WHERE benutzerid = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                return rs.getString("benutzername");
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public User[] getFriendRequests(int id) {
        List<User> friends = new ArrayList();
        try {
            String statement = "SELECT benutzer1 FROM freundschaft WHERE benutzer2 = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                int user2 = rs.getInt("benutzer1");
                String statement2 = "SELECT * FROM freundschaft WHERE " + 
                                    "benutzer1 = '" + id + "' AND " +
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
            System.out.println(ex);
        }
        return friends.toArray(new User[friends.size()]);
    }
    
    public User[] getFriends(int id) {
        List<User> friends = new ArrayList();
        try {
            String statement = "SELECT benutzer2 FROM freundschaft WHERE benutzer1 = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                int user2 = rs.getInt("benutzer2");
                String statement2 = "SELECT * FROM freundschaft WHERE " + 
                                    "benutzer1 = '" + user2 + "' AND " +
                                    "benutzer2 = '" + id + "'";
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
            System.out.println(ex);
        }
        return friends.toArray(new User[friends.size()]);
    }
    
    public User[] getUnansweredRequests(int id) {
        List<User> friends = new ArrayList();
        try {
            String statement = "SELECT benutzer2 FROM freundschaft WHERE benutzer1 = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                int user2 = rs.getInt("benutzer2");
                String statement2 = "SELECT * FROM freundschaft WHERE " + 
                                    "benutzer1 = '" + user2 + "' AND " +
                                    "benutzer2 = '" + id + "'";
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
            System.out.println(ex);
        }
        return friends.toArray(new User[friends.size()]);
    }
    
    public User[] searchUsers(String searchText) {
        List<User> result = new ArrayList();
        try {
            String statement = "SELECT benutzerid FROM benutzer WHERE name LIKE '%" + searchText + "%' OR email LIKE '%" + searchText + "%'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                int id = rs.getInt("benutzerid");
                result.add(new User(id));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result.toArray(new User[result.size()]);
    }
    
    public void requestFriend(User requestor, User requested) {
        
        String mail1 = requestor.getEmail();
        String mail2 = requested.getEmail();
        
        try {
            String statement = "SELECT * FROM freundschaft WHERE benutzer1 = '" +
                    mail1 + "' AND benutzer2 = '" + mail2 + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if(rs.next()) {
                return;
            }
        } catch (SQLException ex) {
                System.out.println(ex);
        }
        
        try {
            String statement = "INSERT INTO freundschaft(benutzer1, benutzer2) " + 
                    "VALUES('" + mail1 + "', '" + mail2 + "')";
            Statement st = con.createStatement();
            st.executeUpdate(statement);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public String getUserEmail(int id) {
        try {
            String statement = "SELECT email FROM benutzer WHERE benutzerid = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                return rs.getString("email");
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public int getUserAge(int id) {
        try {
            String statement = "SELECT age FROM benutzer WHERE benutzerid = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                return rs.getInt("age");
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return 0;
    }
    
    public int getUserICQ(int id) {
        try {
            String statement = "SELECT icq FROM benutzer WHERE benutzerid = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                return rs.getInt("icq");
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return 0;
    }
    
    public String getUserJabber(int id) {
        try {
            String statement = "SELECT jabber FROM benutzer WHERE benutzerid = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                return rs.getString("jabber");
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public ArrayList<String[]> getUserGames(int id) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        try {
            String statement = "SELECT \"spielname\", \"spielzeit\", \"spielt\" FROM \"besitz\" LEFT JOIN"
                             + "\"spiel\" ON \"spiel\".\"spielid\"=\"besitz\".\"spielid\""
                             + "WHERE \"besitz\".\"benutzerid\" = '" + id + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                String[] game = new String[3];
                game[0] = rs.getString("spielname");
                int playTime = rs.getInt("spielzeit");
                int hour = playTime / 3600;
                int minute = (playTime - (hour * 3600) )/ 60;
                DecimalFormat df = new DecimalFormat("00");
                game[1] = hour + ":" + df.format(minute);
                game[2] = "No";
                list.add(game);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
}
