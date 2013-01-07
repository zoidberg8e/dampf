
package gmanager;

import javax.swing.ImageIcon;

/**
 *
 * @author patrick
 */
public class User {
    
    private final int ID;
    private final String username;
    
    public User(int ID) {
        this.ID = ID;
        this.username = DBConnector.getInstance().getUsername(ID);
    }
    
    public int getUserID() {
        return ID;
    }
    
    public String getUsername() {
        return username;
    }
    
    public ImageIcon getUserImage() {
        return new ImageIcon("/home/patrick/Downloads/Sil.jpeg");
    }
    
    public User[] getFriends() {
        User[] friends = new User[20];
        for (int i = 0; i < friends.length; i++) {
            friends[i] = new User(i);
        }
        return friends;
    }
}
