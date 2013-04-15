package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class MainGUI extends JFrame implements ActionListener, KeyListener, PopupMenuListener {
    
    private JMenuItem exit, logout;
    private JScrollPane profile, explorer, news;
    private JPanel alignFriendsNorth;
    private JTextField searchFriends;
    private JButton addFriend;
    private JTabbedPane tabbedPane;
    private GManager gameManager;
    private User contextMenuUser;
    private FriendFinder friendFinder;

    public MainGUI(GManager gm) {
        super("GManager");
        
        gameManager = gm;
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        
        JMenu file = new JMenu("File");
        menubar.add(file);
        
        file.addSeparator();
        
        logout = new JMenuItem("Logout");
        logout.setMnemonic('l');
        logout.addActionListener(this);
        file.add(logout);
        
        exit = new JMenuItem("Exit");
        exit.setMnemonic('x');
        exit.addActionListener(this);
        file.add(exit);
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        cp.add(tabbedPane, BorderLayout.CENTER);
        
        news = new JScrollPane();
        tabbedPane.addTab("News", news);
        
        profile = new JScrollPane(createProfilePanel(gameManager.getUser()));
        tabbedPane.addTab("My Profile", profile);
        
        explorer = new JScrollPane();
        tabbedPane.addTab("Game Explorer", explorer);
        
        JPanel east = new JPanel();
        east.setLayout(new BorderLayout());
        cp.add(east, BorderLayout.EAST);
        
        JPanel userControl = new JPanel();
        userControl.setAlignmentX(0);
        east.add(userControl, BorderLayout.NORTH);
        
        addFriend = new JButton("add");
        addFriend.setToolTipText("Add a friend to your friendlist.");
        addFriend.addActionListener(this);
        userControl.add(addFriend);
        
        alignFriendsNorth = new JPanel();
        alignFriendsNorth.setLayout(new BorderLayout());
        
        User u = gameManager.getUser();
        JPanel friends = createFriendPanel(u.getFriendRequests(), u.getFriends(), u.getUnansweredRequests());
        alignFriendsNorth.add(friends, BorderLayout.NORTH);
        
        JScrollPane friendsScrollPane = new JScrollPane(alignFriendsNorth);
        friendsScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        east.add(friendsScrollPane, BorderLayout.CENTER);
        
        JPanel searchBorder = new JPanel();
        searchBorder.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        east.add(searchBorder, BorderLayout.SOUTH);
        
        searchFriends = new InfoTextField(20, "Search your friendlist");
        searchFriends.addKeyListener(this);
        searchBorder.add(searchFriends);
        
        setPreferredSize(new Dimension(640, 480));
        pack();
        setVisible(true);
    }
    
    private JPanel createFriendPanel(User[] requests, User[] friends, User[] requested) {
        JPanel friendPanel = new JPanel();
        friendPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 0;
        c.insets = new Insets(2, 3, 0, 3);
        
        JLabel headerRequests = new JLabel("Friend Requests:");
        Font f = headerRequests.getFont();
        Float s = f.getSize2D();
        s += 6.0f;
        headerRequests.setFont(f.deriveFont(s));
        friendPanel.add(headerRequests, c);
        c.gridy++;

        friendPanel.add(new JSeparator(), c);
        c.gridy++;
        
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
            
            friendPanel.add(request, c);
            c.gridy++;
            
        }
        
        JLabel headerFriends = new JLabel("Friends:");
        headerFriends.setFont(f.deriveFont(s));
        friendPanel.add(headerFriends, c);
        c.gridy++;

        friendPanel.add(new JSeparator(), c);
        c.gridy++;
        
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
                    for (int i = 3; i < tabbedPane.getTabCount(); i++) {
                        if (tabbedPane.getTitleAt(i).equals(contextMenuUser.getUsername())) {
                            tabbedPane.setSelectedIndex(i);
                            return;
                        }
                    }
                    tabbedPane.addTab(contextMenuUser.getUsername(), new JScrollPane(createProfilePanel(contextMenuUser)));
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() -1);
                    setClosable(tabbedPane.getTabCount() - 1);
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
            
            friendPanel.add(friend, c);
            c.gridy++;
        }
        
        JLabel headerRequested = new JLabel("Requested Friends:");
        headerRequested.setFont(f.deriveFont(s));
        friendPanel.add(headerRequested, c);
        c.gridy++;

        friendPanel.add(new JSeparator(), c);
        c.gridy++;
        
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
            
            friendPanel.add(requestedFriends, c);
            c.gridy++;
        }
        
        return friendPanel;
    }
    
    private JPanel createProfilePanel(User user) {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());
        
        JPanel topLine = new JPanel();
        topLine.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        topLine.setLayout(new BorderLayout(8, 10));
        profilePanel.add(topLine, BorderLayout.NORTH);
        
        JLabel userImage = new JLabel(user.getUserImage());
        userImage.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        topLine.add(userImage, BorderLayout.WEST);
        
        JLabel userName = new JLabel(user.getUsername());
        Font f = userName.getFont();
        Float s = f.getSize2D();
        s += 12.0f;
        userName.setFont(f.deriveFont(s));
        userName.setVerticalAlignment(SwingConstants.BOTTOM);
        topLine.add(userName, BorderLayout.CENTER);
        
        JSeparator sep = new JSeparator();
        topLine.add(sep, BorderLayout.SOUTH);
        
        return profilePanel;
    }
    
    private JPanel createExplorerPanel() {
        JPanel explorerPanel = new JPanel();
        return explorerPanel;
    }
    
    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel();
        return gamePanel;
    }
    
    private void setClosable(int i) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        p.setOpaque(false);
        JLabel text = new JLabel(tabbedPane.getTitleAt(i));
        text.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        p.add(text);
        TabCloseButton b = new TabCloseButton();
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TabCloseButton b = (TabCloseButton) e.getSource();
                int i = tabbedPane.indexOfTabComponent(b.getParent());
                tabbedPane.remove(i);
            }
        });
        p.add(b);
        tabbedPane.setTabComponentAt(i, p);
    }
    
    private User[] search(String keyword, User[] users) {
        if (keyword.equals("")) {
            return users;
        }
        List<User> filtered = new ArrayList();
        for (User u : users) {
            String username = u.getUsername();
            if(username.toLowerCase().contains(keyword.toLowerCase())) {
                filtered.add(u);
            }
        }
        return filtered.toArray(new User[filtered.size()]);
    }
    
    public GManager getGManager() {
        return gameManager;
    }
    
    public void updateFriendPanel() {
        User u = gameManager.getUser();
        u.update();
        
        alignFriendsNorth.removeAll();
        alignFriendsNorth.add(createFriendPanel(u.getFriendRequests(), u.getFriends(), u.getUnansweredRequests()), BorderLayout.NORTH);
        alignFriendsNorth.revalidate();
        alignFriendsNorth.repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(exit)) {
            System.exit(0);
        }
        else if(e.getSource().equals(logout)) {
            dispose();
            new LoginScreen();
        }
        else if(e.getSource().equals(addFriend)) {
            if(friendFinder == null) {
                friendFinder = new FriendFinder(this);
            }
            if(!friendFinder.isVisible()) {
                friendFinder.setVisible(true);
            }
            else {
                friendFinder.toFront();
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource().equals(searchFriends)) {
            if(e.getKeyCode() == 10) {
                String keyword = searchFriends.getText();
                alignFriendsNorth.removeAll();

                User[] requests = search(keyword, gameManager.getUser().getFriendRequests());
                User[] friends = search(keyword, gameManager.getUser().getFriends());
                User[] requested = search(keyword, gameManager.getUser().getUnansweredRequests());
                JPanel friendPanel = createFriendPanel(requests, friends, requested);
                alignFriendsNorth.add(friendPanel, BorderLayout.NORTH);
                alignFriendsNorth.revalidate();
                alignFriendsNorth.repaint();
            }
        }
    }
    
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        UserPopupMenu u = (UserPopupMenu) e.getSource();
        contextMenuUser = u.getUser();
    }
    
    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
    
    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {}
}
