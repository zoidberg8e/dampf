package gmanager;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

public class CollapseableList extends JPanel implements ActionListener, MouseListener {
    
    private User[] users;
    private User owner;
    private User contextUser;
    private CollapseButton button;
    private JPanel userList;
    private JLabel header;
    private String headerText;
    private boolean visible = true;
    private GridBagConstraints global;
    private FriendList friendlist;
    private MainGUI mainGUI;
    
    private JTabbedPane tab;
    
    private JPopupMenu menu;
    private JMenuItem accept, decline, cancelRequest, showProfile, cancelFriendship;
    
    public static final int TYPE_REQUEST = 0;
    public static final int TYPE_FRIEND = 1;
    public static final int TYPE_REQUESTED = 2;
    
    public CollapseableList(User owner, int type, User[] users, FriendList friendlist, JTabbedPane tab, MainGUI mainGUI) {
        super(new GridBagLayout());
        
        this.users = users;
        this.owner = owner;
        this.friendlist = friendlist;
        this.tab = tab;
        this.mainGUI = mainGUI;

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
        
        menu = createPopupMenu(type);
        
        if(users.length == 0) {
            setVisible(false);
        }
    }
    
    private JPopupMenu createPopupMenu(int type) {
        JPopupMenu menu = new JPopupMenu();
        switch (type) {
            case TYPE_REQUEST:
                accept = new JMenuItem("Accept");
                accept.addActionListener(this);
                menu.add(accept);
                
                decline = new JMenuItem("Decline");
                decline.addActionListener(this);
                menu.add(decline);
                break;
            case TYPE_FRIEND:
                showProfile = new JMenuItem("Show Profile");
                showProfile.addActionListener(this);
                menu.add(showProfile);
                
                cancelFriendship = new JMenuItem("Remove");
                cancelFriendship.addActionListener(this);
                menu.add(cancelFriendship);
                ;
                break;
            case TYPE_REQUESTED:
                cancelRequest = new JMenuItem("Cancel");
                cancelRequest.addActionListener(this);
                menu.add(cancelRequest);
                break;
        }
        return menu;
    }
    
    private JPanel createUserList() {
        JPanel base = new JPanel(new GridBagLayout());
        base.addMouseListener(this);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 22, 0, 3);
        c.weightx = 1;
        c.gridy = 0;
        
        for (int i = 0; i < users.length; i++) {
            Color background;
            
            UserCard userCard = new UserCard(users[i]);
            userCard.addMouseListener(this);
            if (i % 2 == 0) {
                background = new Color(220, 230, 255);
            }
            else {
                background = new Color(190, 210, 255);
            }
            userCard.setBackground(background);
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
        return true;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(button)) {
            button.setCollapsed(!button.isCollapsed());
            userList.setVisible(!visible);
            visible = !visible;
        }
        else if(e.getSource().equals(accept)) {
            DBConnector.getInstance().createFriendship(owner, contextUser);
            User[] requests = DBConnector.getInstance().getFriendRequests(owner.getID());
            User[] friends = DBConnector.getInstance().getFriends(owner.getID());
            User[] requested = DBConnector.getInstance().getUnansweredRequests(owner.getID());
            friendlist.update(requests, friends, requested);
        }
        else if(e.getSource().equals(decline)) {
            DBConnector.getInstance().deleteFriendship(contextUser.getID(), owner.getID());
            User[] requests = DBConnector.getInstance().getFriendRequests(owner.getID());
            User[] friends = DBConnector.getInstance().getFriends(owner.getID());
            User[] requested = DBConnector.getInstance().getUnansweredRequests(owner.getID());
            friendlist.update(requests, friends, requested);
        }
        else if(e.getSource().equals(cancelFriendship)) {
            DBConnector.getInstance().deleteFriendship(contextUser.getID(), owner.getID());
            DBConnector.getInstance().deleteFriendship(owner.getID(), contextUser.getID());
            User[] requests = DBConnector.getInstance().getFriendRequests(owner.getID());
            User[] friends = DBConnector.getInstance().getFriends(owner.getID());
            User[] requested = DBConnector.getInstance().getUnansweredRequests(owner.getID());
            friendlist.update(requests, friends, requested);
        }
        else if(e.getSource().equals(cancelRequest)) {
            DBConnector.getInstance().deleteFriendship(owner.getID(), contextUser.getID());
            User[] requests = DBConnector.getInstance().getFriendRequests(owner.getID());
            User[] friends = DBConnector.getInstance().getFriends(owner.getID());
            User[] requested = DBConnector.getInstance().getUnansweredRequests(owner.getID());
            friendlist.update(requests, friends, requested);
        }
        else if(e.getSource().equals(showProfile)) {
            String contextUsername = contextUser.getUsername();
            int index = tab.indexOfTab(contextUsername);
            if(index == -1) {
                tab.addTab(contextUsername, new ProfilePanel(contextUser, false, tab, mainGUI));
            }
            tab.setSelectedIndex(tab.indexOfTab(contextUsername));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 3) {
            UserCard source = (UserCard) e.getSource();
            contextUser = source.getUser();
            
            menu.show(source, e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
