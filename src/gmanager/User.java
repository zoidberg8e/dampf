
package gmanager;

import javax.swing.ImageIcon;

public class User {
    
    private final String email;
    private final String username;
    private User[] friends;
    private User[] friendRequests;
    private User[] unansweredRequests;
    
    public User(String email) {
        this.email = email;
        this.username = DBConnector.getInstance().getUsername(email);
    }
    
    public void update() {
        
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public ImageIcon getUserImage() {
        return new ImageIcon("sil.jpeg");
    }
    
    public User[] getFriends() {
        if (friends == null) {
            friends = DBConnector.getInstance().getFriends(email);
        }
        return friends;
    }
    
    public User[] getFriendRequests() {
        if (friendRequests == null) {
            friendRequests = DBConnector.getInstance().getFriendRequests(email);
        }
        return friendRequests;
    }
    
    public User[] getUnansweredRequests() {
        if (unansweredRequests == null) {
            unansweredRequests = DBConnector.getInstance().getUnansweredRequests(email);
        }
        return unansweredRequests;
    }
}
