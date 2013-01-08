
package gmanager;

import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author patrick
 */
public class User {
    
    private final int ID;
    private final String username;
    private User[] friends = null;
    
    public User(int ID) {
        this.ID = ID;
        char[] text = new char[16];
        Random rnd = new Random();
        String characters = new String("AaBbCcDdEeFf");
        for (int i = 0; i < 16; i++)
        {
            text[i] = characters.charAt(rnd.nextInt(characters.length()));
        }
        
        this.username = new String(text);
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
        if (friends == null) {
            friends = new User[20];
            for (int i = 0; i < friends.length; i++) {
                friends[i] = new User(i);
            }
        }
        return friends;
    }
}
