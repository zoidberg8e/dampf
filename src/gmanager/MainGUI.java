package gmanager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class MainGUI extends JFrame implements ActionListener, KeyListener, WindowListener {
    
    private JMenuItem exit, logout, deleteAccount;
    private JScrollPane profile;
    private GameExplorer explorer;
    private JTextField searchFriends;
    private JButton addFriend;
    private JTabbedPane tabbedPane;
    private GManager gameManager;
    private FriendList friendList;

    public MainGUI(GManager gm) {
        super("GManager");
        
        gameManager = gm;
        
        addWindowListener(this);
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
        
        JMenu account = new JMenu("Account");
        menubar.add(account);
        
        deleteAccount = new JMenuItem("Delete Account");
        deleteAccount.addActionListener(this);
        account.add(deleteAccount);
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        cp.add(tabbedPane, BorderLayout.CENTER);
        
        tabbedPane.addTab("News", new NewsPage("<html>Welcome to the brand new GManager</html>"));
        
        ProfilePanel userProfile = new ProfilePanel(gameManager.getUser(), true, tabbedPane, this);
        profile = new JScrollPane(userProfile);
        tabbedPane.addTab("My Profile", profile);
        
        explorer = new GameExplorer(tabbedPane, gm.getUser(), this, userProfile);
        tabbedPane.addTab("Game Explorer", explorer);
        
        JPanel east = new JPanel();
        east.setLayout(new BorderLayout());
        cp.add(east, BorderLayout.EAST);
        
        JPanel friendListHeaderPanel = new JPanel();
        friendListHeaderPanel.setBorder(BorderFactory.createEmptyBorder(5, 2, 5, 5));
        friendListHeaderPanel.setLayout(new BorderLayout());
        east.add(friendListHeaderPanel, BorderLayout.NORTH);
        
        JLabel friendListHeader = new JLabel("Friendlist");
        Font f = friendListHeader.getFont();
        Float s = f.getSize2D();
        s += 6.0f;
        friendListHeader.setFont(f.deriveFont(s));
        friendListHeaderPanel.add(friendListHeader, BorderLayout.CENTER);
        
        addFriend = new JButton("add");
        addFriend.setToolTipText("Add a friend to your friendlist.");
        addFriend.addActionListener(this);
        friendListHeaderPanel.add(addFriend, BorderLayout.EAST);
        
        JPanel alignFriendsNorth = new JPanel();
        alignFriendsNorth.setLayout(new BorderLayout());
        
        User u = gameManager.getUser();
        friendList = new FriendList(u, u.getFriendRequests(), u.getFriends(), u.getUnansweredRequests(), tabbedPane, this);
        alignFriendsNorth.add(friendList, BorderLayout.NORTH);
        
        Timer t = new Timer(60000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.getUser().update();
                friendList.update(gameManager.getUser().getFriendRequests(),
                                  gameManager.getUser().getFriends(),
                                  gameManager.getUser().getUnansweredRequests());
            }
        });
        t.start();
        
        JScrollPane friendsScrollPane = new JScrollPane(alignFriendsNorth);
        friendsScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        east.add(friendsScrollPane, BorderLayout.CENTER);
        
        JPanel searchBorder = new JPanel();
        searchBorder.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        east.add(searchBorder, BorderLayout.SOUTH);
        
        searchFriends = new InfoTextField(22, "Search your friendlist");
        searchFriends.addKeyListener(this);
        searchBorder.add(searchFriends);
        
        setPreferredSize(new Dimension(640, 480));
        pack();
        setVisible(true);
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
        ArrayList<User> filtered = new ArrayList();
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
        friendList.update(u.getFriendRequests(), u.getFriends(), u.getUnansweredRequests());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(exit)) {
            endAllPlaying();
            System.exit(0);
        }
        else if(e.getSource().equals(logout)) {
            dispose();
            endAllPlaying();
            new LoginScreen();
        }
        else if(e.getSource().equals(deleteAccount)) {
            int result = JOptionPane.showConfirmDialog(null, "Do your realy want to delete your account?", "Delete Account", JOptionPane.YES_NO_OPTION);
            if(result == 0) {
                DBConnector.getInstance().deleteUser(gameManager.getUser().getID());
                dispose();
                new LoginScreen();
            }
        }
        else if(e.getSource().equals(addFriend)) {
            //if(friendFinder == null) {
                FriendFinder friendFinder = new FriendFinder(this);
                friendFinder.setVisible(true);
            //}
            //if(!friendFinder.isVisible()) {
            //    friendFinder.setVisible(true);
            //}
            //else {
            //    friendFinder.toFront();
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

                User[] requests = search(keyword, gameManager.getUser().getFriendRequests());
                User[] friends = search(keyword, gameManager.getUser().getFriends());
                User[] requested = search(keyword, gameManager.getUser().getUnansweredRequests());
                
                friendList.update(requests, friends, requested);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        endAllPlaying();
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    
    private void endAllPlaying() {
        ArrayList<String[]> games = DBConnector.getInstance().getUserGames(gameManager.getUser().getID());
        for (String[] game : games) {
            if(game[2].equals("Yes")) {
                DBConnector.getInstance().setUserGamePlaying(gameManager.getUser().getID(), Integer.parseInt(game[0]), false);
            }
        }
    }
}
