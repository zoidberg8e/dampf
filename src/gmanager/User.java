
package gmanager;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class User {
    
    private int id;
    private final String email;
    private final String username;
    private int age;
    private int icq;
    private String jabber;
    private ImageIcon userImage;
    private User[] friends;
    private User[] friendRequests;
    private User[] unansweredRequests;
    private ArrayList<String[]> games;
    
    public User(int ID) {
        this.id = ID;
        this.email = DBConnector.getInstance().getUserEmail(id);
        this.username = DBConnector.getInstance().getUserName(id);
        
        this.userImage = DBConnector.getInstance().getUserImage(id);
        this.age = DBConnector.getInstance().getUserAge(id);
        this.icq = DBConnector.getInstance().getUserICQ(id);
        this.jabber = DBConnector.getInstance().getUserJabber(id);
    }
    
    public void update() {
        this.friends = DBConnector.getInstance().getFriends(id);
        this.friendRequests = DBConnector.getInstance().getFriendRequests(id);
        this.unansweredRequests = DBConnector.getInstance().getUnansweredRequests(id);
        this.userImage = DBConnector.getInstance().getUserImage(id);
        this.age = DBConnector.getInstance().getUserAge(id);
        this.icq = DBConnector.getInstance().getUserICQ(id);
        this.jabber = DBConnector.getInstance().getUserJabber(id);
    }
    
    public int getID() {
        return this.id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        DBConnector.getInstance().setUserAge(id, age);
        this.age = age;
    }
    
    public int getICQ() {
        return icq;
    }
    
    public void setICQ(int icq) {
        DBConnector.getInstance().setUserICQ(id, icq);
        this.icq = icq;
    }
    
    public String getJabber() {
        return jabber;
    }
    
    public void setJabber(String jabber) {
        DBConnector.getInstance().setUserJabber(id, jabber);
        this.jabber = jabber;
    }
    
    public void setImage(String path) {
        boolean successful = DBConnector.getInstance().setUserImage(id, path);
        if(successful) {
            this.userImage = DBConnector.getInstance().getUserImage(id);
        }
    }
    
    public ImageIcon getImage() {
        return userImage;
    }
    
    public User[] getFriends() {
        if (friends == null) {
            friends = DBConnector.getInstance().getFriends(id);
        }
        return friends;
    }
    
    public User[] getFriendRequests() {
        if (friendRequests == null) {
            friendRequests = DBConnector.getInstance().getFriendRequests(id);
        }
        return friendRequests;
    }
    
    public User[] getUnansweredRequests() {
        if (unansweredRequests == null) {
            unansweredRequests = DBConnector.getInstance().getUnansweredRequests(id);
        }
        return unansweredRequests;
    }
    
    public ArrayList<String[]> getGames() {
        if (games == null) {
            games = DBConnector.getInstance().getUserGames(id);
        }
        return games;
    }
}
