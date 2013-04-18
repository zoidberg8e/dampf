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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author doldpa
 */
public class FriendList extends JPanel implements PopupMenuListener, ActionListener {
    
    private boolean requestsVisible = true;
    private boolean friendsVisible = true;
    private boolean requestedVisible = true;
    private User[] requests, friends, requested;
    private CollapseButton requestsCollapse, friendsCollapse, requestedCollapse;
    private JPanel content;
    private GridBagConstraints global;
    private JPanel requestsPanel, friendsPanel, requestedPanel;
    
    public FriendList(User[] requests, User[] friends, User[] requested) {
        super();
        setLayout(new BorderLayout());
        
        content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 2));
        content.setLayout(new GridBagLayout());
        add(content, BorderLayout.NORTH);
        
        this.requests = requests;
        this.friends = friends;
        this.requested = requested;
        
        global = new GridBagConstraints();
        global.fill = GridBagConstraints.HORIZONTAL;
        global.weightx = 1;
        global.gridy = 0;
        global.insets = new Insets(2, 0, 15, 0);
        
        requestsPanel = requestsPanel();
        if(requestsPanel != null) {
            content.add(requestsPanel, global);
            global.gridy++;
        }
        
        friendsPanel = friendsPanel();
        if(friendsPanel != null) {
            content.add(friendsPanel, global);
            global.gridy++;
        }
        
        requestedPanel = requestedPanel();
        if(requestedPanel != null) {
            content.add(requestedPanel, global);
        }
    }
    
    private JPanel requestsPanel() {
        if(requests.length == 0) {
            return null;
        }
        JPanel base = new JPanel();
        base.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 3, 0, 0);

        requestsCollapse = new CollapseButton();
        requestsCollapse.addActionListener(this);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        base.add(requestsCollapse, c);
        
        JLabel headerRequests = new JLabel("Friend Requests (" + requests.length + ")");
        c.weightx = 1;
        c.insets = new Insets(2, 0, 0, 3);
        c.gridx++;
        base.add(headerRequests, c);
        
        c.gridwidth = 2;
        c.insets = new Insets(2, 3, 0, 3);
        c.gridx = 0;
        c.gridy++;
        base.add(new JSeparator(), c);
        
        if(requestsVisible) {
            c.insets = new Insets(2, 25, 0, 3);
            for (int i = 0; i < requests.length; i++) {
                Color background;
                JPanel request = new JPanel();
                if (i % 2 == 0) {
                    background = new Color(220, 230, 255);
                }
                else {
                    background = new Color(190, 210, 255);
                }
                request.setBackground(background);
                request.setLayout(new BorderLayout());
                request.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JLabel thumbnail = new JLabel(requests[i].getUserImage());
                thumbnail.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                request.add(thumbnail, BorderLayout.WEST);

                JLabel friendName = new JLabel(requests[i].getUsername());
                friendName.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
                request.add(friendName, BorderLayout.CENTER);
                c.gridy++;
                base.add(request, c);
            }
        }
        return base;
    }
    
    private JPanel friendsPanel() {
        if(friends.length == 0) {
            return null;
        }
        JPanel base = new JPanel();
        base.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 3, 0, 0);
        
        friendsCollapse = new CollapseButton();
        friendsCollapse.addActionListener(this);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        base.add(friendsCollapse, c);
        
        JLabel headerFriends = new JLabel("Friends (" + friends.length + ")");
        c.weightx = 1;
        c.insets = new Insets(2, 0, 0, 3);
        c.gridx++;
        base.add(headerFriends, c);

        c.gridwidth = 2;
        c.insets = new Insets(2, 3, 0, 3);
        c.gridx = 0;
        c.gridy++;
        base.add(new JSeparator(), c);

        if(friendsVisible) {
            c.insets = new Insets(2, 25, 0, 3);
            for (int i = 0; i < friends.length; i++) {
                Color background;
                JPanel friend = new JPanel();
                if (i % 2 == 0) {
                    background = new Color(220, 230, 255);
                }
                else {
                    background = new Color(190, 210, 255);
                }
                friend.setBackground(background);
                friend.setLayout(new BorderLayout());
                friend.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                UserPopupMenu friendMenu = new UserPopupMenu(friends[i]);
                friendMenu.addPopupMenuListener(this);
                JMenuItem showProfile =  new JMenuItem("Show Profile");
                showProfile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
    //                        for (int i = 3; i < tabbedPane.getTabCount(); i++) {
    //                            if (tabbedPane.getTitleAt(i).equals(contextMenuUser.getUsername())) {
    //                                tabbedPane.setSelectedIndex(i);
    //                                return;
    //                            }
    //                        }
    //                        tabbedPane.addTab(contextMenuUser.getUsername(), new JScrollPane(createProfilePanel(contextMenuUser)));
    //                        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() -1);
    //                        setClosable(tabbedPane.getTabCount() - 1);
                    }
                });
                friendMenu.add(showProfile);
                friendMenu.add(new JMenuItem("Chat"));
                friend.setComponentPopupMenu(friendMenu);

                JLabel friendThumbnail = new JLabel(friends[i].getUserImage());
                friendThumbnail.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                friend.add(friendThumbnail, BorderLayout.WEST);

                JLabel friendName = new JLabel(friends[i].getUsername());
                friendName.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
                friend.add(friendName, BorderLayout.CENTER);
                c.gridy++;
                base.add(friend, c);

            }
        }
        return base;
    }
        
    private JPanel requestedPanel() {
        if(requested.length == 0) {
            return null;
        }
        JPanel base = new JPanel();
        base.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 3, 0, 0);
        c.gridy = 0;
        
        requestedCollapse = new CollapseButton();
        requestedCollapse.addActionListener(this);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        base.add(requestedCollapse, c);
        
        JLabel headerRequested = new JLabel("Requested Friends (" + requested.length + ")");
        c.weightx = 1;
        c.insets = new Insets(2, 0, 0, 3);
        c.gridx++;
        base.add(headerRequested, c);

        c.gridwidth = 2;
        c.insets = new Insets(2, 3, 0, 3);
        c.gridx = 0;
        c.gridy++;
        base.add(new JSeparator(), c);

        if(requestedVisible) {
            c.insets = new Insets(2, 25, 0, 3);
            for (int i = 0; i < requested.length; i++) {
                Color background;
                JPanel requestedFriends = new JPanel();
                if (i % 2 == 0) {
                    background = new Color(220, 230, 255);
                }
                else {
                    background = new Color(190, 210, 255);
                }
                requestedFriends.setBackground(background);
                requestedFriends.setLayout(new BorderLayout());
                requestedFriends.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                JLabel thumbnail = new JLabel(requested[i].getUserImage());
                thumbnail.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                requestedFriends.add(thumbnail, BorderLayout.WEST);

                JLabel friendName = new JLabel(requested[i].getUsername());
                friendName.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
                requestedFriends.add(friendName, BorderLayout.CENTER);
                c.gridy++;
                base.add(requestedFriends, c);
            }
        }
        return base;
    }
    
    public void setRequests(User[] requests) {
        this.requests = requests;
    }
    
    public void setFriends(User[] friends) {
        this.friends = friends;
    }
    
    public void setRequested(User[] requested) {
        this.requested = requested;
    }

    private void updateRequests() {
        if(requestsPanel != null) {
            System.out.println("geht rein");
            content.remove(requestsPanel);
            content.revalidate();
            content.repaint();
        }
        requestsPanel = requestsPanel();
        if(requestsPanel != null) {
            global.gridy = 0;
            content.add(requestsPanel, global);
        }

    }
    
    private void updateFriends() {
        if(friendsPanel != null) {
            content.remove(friendsPanel);
            content.revalidate();
            content.repaint();
        }
        friendsPanel = friendsPanel();
        if(friendsPanel != null) {
            global.gridy = 1;
            content.add(friendsPanel, global);
        }

    }
    private void updateRequested() {
        if(requestedPanel != null) {
            content.remove(requestedPanel);
            content.revalidate();
            content.repaint();
        }
        requestedPanel = requestedPanel();
        if(requestedPanel != null) {
            global.gridy = 2;
            content.add(requestedPanel, global);
        }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source.equals(requestsCollapse)) {
            requestsVisible = !requestsVisible;
            requestsCollapse.setCollapsed(!requestsCollapse.isCollapsed());
            updateRequests();
        }
        else if (source.equals(friendsCollapse)) {
            friendsVisible = !friendsVisible;
            friendsCollapse.setCollapsed(!friendsCollapse.isCollapsed());
            updateFriends();
        }
        else if (source.equals(requestedCollapse)) {
            requestedVisible = !requestedVisible;
            requestedCollapse.setCollapsed(!requestedCollapse.isCollapsed());
            updateRequested();
        }
    }
}
