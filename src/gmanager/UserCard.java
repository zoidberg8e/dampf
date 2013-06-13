package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserCard extends JPanel {
    
    private User user;

    public UserCard(User user) {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ImageIcon userImage = user.getImage();
        int originalHeight = userImage.getIconHeight();
        int size = 48;
        if(originalHeight != size) {
            Image originalImage = userImage.getImage();
            userImage = new ImageIcon(originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH));
        }

        JLabel thumbnail = new JLabel(userImage);
        thumbnail.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(thumbnail, BorderLayout.WEST);

        JLabel friendName = new JLabel(user.getUsername());
        friendName.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        add(friendName, BorderLayout.CENTER);
        
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
}
