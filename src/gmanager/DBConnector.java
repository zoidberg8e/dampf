package gmanager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
            return new ImageIcon(getClass().getResource("/gmanager/sil.jpeg"));
        }
    }
    
    public ImageIcon getGameImage(int id) {
        ImageIcon img = null;
        try {
            String statement = "SELECT bild FROM spiel WHERE spielid = '" + id + "'";
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
            return new ImageIcon(getClass().getResource("/gmanager/game_default.jpg"));
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
    
    public void deleteUser(int userID) {
        try {
                String statement = "DELETE FROM \"benutzer\"" + 
                                   "WHERE \"benutzerid\"=" + userID;
                Statement st = con.createStatement();
                st.executeUpdate(statement);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
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
            String statement = "SELECT benutzerid FROM benutzer WHERE LOWER(name) LIKE LOWER('%" + searchText + "%') OR LOWER(email) LIKE LOWER('%" + searchText + "%')";
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
        
        int id1 = requestor.getID();
        int id2 = requested.getID();
        
        try {
            String statement = "SELECT * FROM freundschaft WHERE benutzer1=" +
                    id1 + " AND benutzer2 =" + id2;
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
                    "VALUES(" + id1 + ", " + id2 + ")";
            Statement st = con.createStatement();
            st.executeUpdate(statement);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void createFriendship(User requestor, User requested) {
        int id1 = requestor.getID();
        int id2 = requested.getID();
        
        try {
            String statement = "INSERT INTO freundschaft(benutzer1, benutzer2) " + 
                    "VALUES(" + id1 + ", " + id2 + ")";
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
    
    public void setUserAge(int userID, int age) {
        try {
            String statement = "UPDATE \"benutzer\" SET \"age\"="+ age + " WHERE \"benutzerid\"=" + userID;
            Statement st = con.createStatement();
            st.executeUpdate(statement);
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
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
    
    public void setUserICQ(int userID, int icq) {
        try {
            String statement = "UPDATE \"benutzer\" SET \"icq\"="+ icq + " WHERE \"benutzerid\"=" + userID;
            Statement st = con.createStatement();
            st.executeUpdate(statement);
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
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
    
    public void setUserJabber(int userID, String jabber) {
        try {
            String statement = "UPDATE \"benutzer\" SET \"jabber\"='"+ jabber + "' WHERE \"benutzerid\"=" + userID;
            Statement st = con.createStatement();
            st.executeUpdate(statement);
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public ArrayList<String[]> getUserGames(int id) {
        ArrayList<String[]> list = new ArrayList();
        try {
            String statement = "SELECT \"spielname\", \"spielt\", \"spielid\""
                             + "FROM \"benutzerspiele\""
                             + "WHERE \"benutzerid\" =" + id
                             + "ORDER BY \"spielname\"";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                String[] game = new String[3];
                game[0] = "" + rs.getString("spielid");
                game[1] = rs.getString("spielname");
                if(rs.getBoolean("spielt")) {
                    game[2] = "Yes";
                }
                else {
                    game[2] = "No";
                }

                list.add(game);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
    
    public ArrayList<Game> getGamesLike(String compare) {
        ArrayList<Game> list = new ArrayList<Game>();
        try {
            String statement = "SELECT \"spielid\", \"spielname\", \"entwickler\""
                             + "FROM \"spiel\""
                             + "WHERE LOWER(\"spielname\") LIKE LOWER('" + compare + "')"
                             + "ORDER BY \"spielname\"";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                int gameID = rs.getInt("spielid");
                String name = rs.getString("spielname");
                String developer = rs.getString("entwickler");
                Game game = new Game(gameID, name, developer);
                list.add(game);
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
    
    public String getGameName(int ID) {
        String result = null;
        try {
            String statement = "SELECT spielname FROM spiel WHERE spielid =" + ID;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                result = rs.getString("spielname");
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    public String getGameDeveloper(int ID) {
        String result = null;
        try {
            String statement = "SELECT entwickler FROM spiel WHERE spielid =" + ID;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                result = rs.getString("entwickler");
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    public String getGameDescription(int ID) {
        String result = null;
        try {
            String statement = "SELECT kommentar FROM spiel WHERE spielid =" + ID;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                result = rs.getString("kommentar");
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }
    
    public float[] getGameRatings(int ID) {
        int funTotal = 0;
        int incentiveTotal = 0;
        int graphicTotal = 0;
        int pricePerformanceTotal = 0;
        int count = -1;
        try {
            String statement = "SELECT \"spass\", \"motivation\", \"grafik\", \"preisleistung\""
                             + "FROM \"bewertung\""
                             + "WHERE \"spielid\"="+ ID;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                funTotal += rs.getInt("spass");
                incentiveTotal += rs.getInt("motivation");
                graphicTotal += rs.getInt("grafik");
                pricePerformanceTotal += rs.getInt("preisleistung");
                count++;
            }
            rs.close();
            st.close();
            if(count == -1) {
                return new float[]{-1, -1, -1, -1};
            }
            count++;
            float funRating = (float) funTotal / (float) count;
            float incentiveRating = (float) incentiveTotal / (float) count;
            float graphicRating = (float) graphicTotal / (float) count;
            float pricePerformanceRating = (float) pricePerformanceTotal / (float) count;
            
            return new float[]{funRating, incentiveRating, graphicRating, pricePerformanceRating};
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public ArrayList<Review> getReviews(int gameID) {
        ArrayList<Review> list = new ArrayList<Review>();
        try {
            String statement = "SELECT \"bewertungid\", \"benutzerid\", \"spass\", \"motivation\", \"grafik\", \"preisleistung\""
                             + "FROM \"bewertung\""
                             + "WHERE \"spielid\"="+ gameID;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            while (rs.next()) {
                int reviewID = rs.getInt("bewertungid");
                int userID = rs.getInt("benutzerid");
                int fun = rs.getInt("spass");
                int incentive = rs.getInt("motivation");
                int graphic = rs.getInt("grafik");
                int price = rs.getInt("preisleistung");
                Review review = new Review(reviewID, new User(userID), fun, incentive, graphic, price);
                list.add(review);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
    
    public User getReviewUser(int reviewID) {
        User user = null;
        try {
            String statement = "SELECT \"benutzerid\""
                             + "FROM \"bewertung\""
                             + "WHERE \"bewertungid\"="+ reviewID;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                user = new User(rs.getInt("benutzerid"));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return user;
    }
    
    public int[] getReviewRatings(int reviewID) {
        try {
            String statement = "SELECT \"spass\", \"motivation\", \"grafik\", \"preisleistung\""
                             + "FROM \"bewertung\""
                             + "WHERE \"bewertungid\"="+ reviewID;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                int fun = rs.getInt("spass");
                int incentive = rs.getInt("motivation");
                int graphic = rs.getInt("grafik");
                int price = rs.getInt("preisleistung");
                return new int[]{fun, incentive, graphic, price};
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return new int[]{-1, -1, -1, -1};
    }
    
    public String getReviewText(int reviewID) {
        String result = null;
        try {
            String statement = "SELECT \"kritik\""
                             + "FROM \"bewertung\""
                             + "WHERE \"bewertungid\"="+ reviewID;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            
            if (rs.next()) {
                result = rs.getString("kritik");
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
        
    }
    
    public Review getUserGameReview(int userID, int gameID) {
        Review review = null;
        try {
            String statement = "SELECT \"bewertungid\", \"spass\", \"motivation\", \"grafik\", \"preisleistung\", \"kritik\""
                             + "FROM \"bewertung\""
                             + "WHERE \"spielid\"="+ gameID + " AND \"benutzerid\"=" + userID;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(statement);
            if(rs.next()) {
                int reviewID = rs.getInt("bewertungid");
                int fun = rs.getInt("spass");
                int inc = rs.getInt("motivation");
                int gra = rs.getInt("grafik");
                int pri = rs.getInt("preisleistung");
                String text = rs.getString("kritik");
                review = new Review(reviewID, new User(userID), fun, inc, gra, pri, text);
            }

            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return review;
    }
    
    public void setReviewRating(int reviewID, String type, int value) {
        try {
            String statement = "UPDATE \"bewertung\" SET \"" + type + "\"="+ value + " WHERE \"bewertungid\"=" + reviewID;
            Statement st = con.createStatement();
            st.executeUpdate(statement);
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void setReviewText(int reviewID, String text) {
        try {
            String statement = "UPDATE \"bewertung\" SET \"kritik\"='"+ text + "' WHERE \"bewertungid\"=" + reviewID;
            Statement st = con.createStatement();
            st.executeUpdate(statement);
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void createReview(int userID, int gameID, int ratingFun, int ratingIncentive, int ratingGraphic, int ratingPrice, String text) {
        try {
            String statement = "INSERT INTO bewertung(spielid, benutzerid, spass, motivation, grafik, preisleistung, kritik) " + 
                    "VALUES(" + gameID + ", " + userID + ", " + ratingFun + ", " + ratingIncentive + ", " + ratingGraphic + ", " + ratingPrice + ", '" + text + "')";
            Statement st = con.createStatement();
            st.executeUpdate(statement);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void setUserGamePlaying(int userID, int gameID, boolean playing) {
        try {
            String statement = "UPDATE \"besitz\" SET \"spielt\"='"+ playing + "'" 
                             + "WHERE \"spielid\"=" + gameID + "AND"
                             + "\"benutzerid\"=" + userID;
            Statement st = con.createStatement();
            st.executeUpdate(statement);
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void addUserGame(int userID, int gameID) {
        try {
            String statement = "INSERT INTO besitz(benutzerid, spielid)" 
                             + "VALUES(" + userID + "," + gameID + ")";
            Statement st = con.createStatement();
            st.executeUpdate(statement);
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void removeUserGame(int userID, int gameID) {
        try {
            String statement = "DELETE FROM \"besitz\"" 
                             + "WHERE \"spielid\"=" + gameID + "AND"
                             + "\"benutzerid\"=" + userID;
            Statement st = con.createStatement();
            st.executeUpdate(statement);
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void deleteFriendship(int userID1, int userID2) {
        try {
            String statement = "DELETE FROM \"freundschaft\"" 
                             + "WHERE \"benutzer1\"=" + userID1 + "AND"
                             + "\"benutzer2\"=" + userID2;
            Statement st = con.createStatement();
            st.executeUpdate(statement);
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
