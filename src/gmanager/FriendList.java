package gmanager;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FriendList extends JPanel {
    
    private JPanel content;
    private CollapseableList requestsPanel, friendsPanel, requestedPanel;
    
    public FriendList(User owner, User[] requests, User[] friends, User[] requested, JTabbedPane tab, MainGUI mainGUI) {
        super();
        setLayout(new BorderLayout());
        
        content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 2));
        content.setLayout(new GridBagLayout());
        add(content, BorderLayout.NORTH);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 0;
        c.insets = new Insets(2, 0, 15, 0);
        
        requestsPanel = new CollapseableList(owner, CollapseableList.TYPE_REQUEST, requests, this, tab, mainGUI);
        content.add(requestsPanel, c);
        
        friendsPanel = new CollapseableList(owner, CollapseableList.TYPE_FRIEND, friends, this, tab, mainGUI);
        c.gridy++;
        content.add(friendsPanel, c);
      
        requestedPanel = new CollapseableList(owner, CollapseableList.TYPE_REQUESTED, requested, this, tab, mainGUI);
        c.gridy++;
        content.add(requestedPanel, c);
    }
    
    public void update(User[] requests, User[] friends, User[] requested) {
        requestsPanel.updateUserList(requests);
        friendsPanel.updateUserList(friends);
        requestedPanel.updateUserList(requested);
    }
}
