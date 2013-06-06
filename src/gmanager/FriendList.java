package gmanager;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author doldpa
 */
public class FriendList extends JPanel implements PopupMenuListener {
    
    private JPanel content;
    private CollapseableList requestsPanel, friendsPanel, requestedPanel;
    
    public FriendList(User[] requests, User[] friends, User[] requested) {
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
        
        requestsPanel = new CollapseableList(CollapseableList.TYPE_REQUEST, requests);
        content.add(requestsPanel, c);
        
        friendsPanel = new CollapseableList(CollapseableList.TYPE_FRIEND, friends);
        c.gridy++;
        content.add(friendsPanel, c);
      
        requestedPanel = new CollapseableList(CollapseableList.TYPE_REQUESTED, requested);
        c.gridy++;
        content.add(requestedPanel, c);
    }
    
    public void update(User[] requests, User[] friends, User[] requested) {
        requestsPanel.updateUserList(requests);
        friendsPanel.updateUserList(friends);
        requestedPanel.updateUserList(requested);
    }
    
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
//        UserPopupMenu u = (UserPopupMenu) e.getSource();
//        contextMenuUser = u.getUser();
    }
    
    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
    
    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {}
}
