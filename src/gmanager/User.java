
package gmanager;

import javax.swing.ImageIcon;

public class User {
    
    private final String email;
    private final String username;
    private int age;
    private int icq;
    private ImageIcon userImage;
    private User[] friends;
    private User[] friendRequests;
    private User[] unansweredRequests;
    
    public User(String email) {
        this.email = email;
        this.username = DBConnector.getInstance().getUsername(email);
        this.age = DBConnector.getInstance().getUserAge(email);
        this.userImage = DBConnector.getInstance().getUserImage(email);
        this.icq = DBConnector.getInstance().getUserICQ(email);
    }
    
    public void update() {
        friends = DBConnector.getInstance().getFriends(email);
        friendRequests = DBConnector.getInstance().getFriendRequests(email);
        unansweredRequests = DBConnector.getInstance().getUnansweredRequests(email);
        this.age = DBConnector.getInstance().getUserAge(email);
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
    
    public int getICQ() {
        return icq;
    }
    
    public void setUserImage(String path) {
        boolean successful = DBConnector.getInstance().setUserImage(email, path);
        if(successful) {
            this.userImage = DBConnector.getInstance().getUserImage(email);
        }
    }
    
    public ImageIcon getUserImage() {
        return userImage;
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
