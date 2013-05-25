package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 *
 * @author patrickd
 */
public class CollapseableList extends JPanel implements ActionListener {
    
    private User[] users;
    private String headerText;
    private CollapseButton button;
    private JPanel userList;
    private JLabel header;
    private boolean visible;
    private GridBagConstraints global;
    
    public CollapseableList(String headerText, User[] users) {   
        super(new GridBagLayout());
        
        this.users = users;
        this.headerText = headerText;

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

            JLabel thumbnail = new JLabel(users[i].getUserImage());
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
