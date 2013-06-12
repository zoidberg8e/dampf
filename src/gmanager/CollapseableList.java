package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class CollapseableList extends JPanel implements ActionListener {
    
    private User[] users;
    private CollapseButton button;
    private JPanel userList;
    private JLabel header;
    private String headerText;
    private boolean visible = true;
    private GridBagConstraints global;
    
    public static final int TYPE_REQUEST = 0;
    public static final int TYPE_FRIEND = 1;
    public static final int TYPE_REQUESTED = 2;
    
    public CollapseableList(int type, User[] users) {   
        super(new GridBagLayout());
        
        this.users = users;

        global = new GridBagConstraints();
        global.fill = GridBagConstraints.HORIZONTAL;
        global.insets = new Insets(2, 3, 0, 0);
        global.gridy = 0;
        
        button = new CollapseButton();
        button.addActionListener(this);
        global.gridwidth = 1;
        global.gridx = 0;
        global.gridy = 0;
        add(button, global);
        
        switch (type) {
            case TYPE_REQUEST: headerText = "Friend Requests"; break;
            case TYPE_FRIEND: headerText = "Friends"; break;
            case TYPE_REQUESTED: headerText = "Requested Friends"; break;
            default: headerText = "List";
        }
        
        header= new JLabel(headerText + " (" + users.length + ")");
        global.weightx = 1;
        global.insets = new Insets(2, 0, 0, 3);
        global.gridx++;
        add(header, global);

        global.gridwidth = 2;
        global.insets = new Insets(2, 3, 0, 3);
        global.gridx = 0;
        global.gridy++;
        add(new JSeparator(), global);
        
        userList = createUserList();
        global.gridy++;
        add(userList, global);
        
        if(users.length == 0) {
            setVisible(false);
        }
    }
    
    private JPanel createUserList() {
        JPanel base = new JPanel(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 22, 0, 3);
        c.weightx = 1;
        c.gridy = 0;
        
        for (int i = 0; i < users.length; i++) {
            Color background;
            JPanel userCard = new JPanel();
            if (i % 2 == 0) {
                background = new Color(220, 230, 255);
            }
            else {
                background = new Color(190, 210, 255);
            }
            userCard.setBackground(background);
            userCard.setLayout(new BorderLayout());
            userCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            ImageIcon userImage = users[i].getImage();
            int originalHeight = userImage.getIconHeight();
            int size = 48;
            if(originalHeight != size) {
                Image originalImage = userImage.getImage();
                userImage = new ImageIcon(originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH));
            }
            
            JLabel thumbnail = new JLabel(userImage);
            thumbnail.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            userCard.add(thumbnail, BorderLayout.WEST);

            JLabel friendName = new JLabel(users[i].getUsername());
            friendName.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
            userCard.add(friendName, BorderLayout.CENTER);
            c.gridy++;
            base.add(userCard, c);
        }
        return base;
    }
    
    private void setUserMenu() {
        
    }
    
    public void updateUserList(User[] users) {
        if(compareUserArrays(this.users, users)) {
            return;
        }
        remove(userList);
        repaint();
        
        this.users = users;
        userList = createUserList();
        userList.setVisible(visible);
        add(userList, global);
        header.setText(headerText + "(" + users.length + ")");
        
        if(users.length == 0) {
            setVisible(false);
        }
        else {
            setVisible(true);
        }
    }
    
    private boolean compareUserArrays(User[] a1, User[]a2) {
        if(a1.length != a2.length) {
            return false;
        }
        // TODO: finish comparison...
        return true;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(button)) {
            button.setCollapsed(!button.isCollapsed());
            userList.setVisible(!visible);
            visible = !visible;
        }
    }
}
