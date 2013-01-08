package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainGUI extends JFrame implements ActionListener, KeyListener {
    
    private JMenuItem exit;
    private JScrollPane myGames, profile, explorer, news;
    private JPanel mainWindow, alignFriendsNorth;
    private JTextField searchFriends;
    private JButton addFriend, logout;
    private JTabbedPane tabbedPane;
    private GManager gameManager;

    public MainGUI(GManager gm) {
        super("GManager");
        
        gameManager = gm;
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        
        JMenu file = new JMenu("File");
        menubar.add(file);
        
        exit = new JMenuItem("Exit");
        exit.setMnemonic('x');
        exit.addActionListener(this);
        file.addSeparator();
        file.add(exit);
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        tabbedPane = new JTabbedPane();
        cp.add(tabbedPane, BorderLayout.CENTER);
        
        news = new JScrollPane();
        tabbedPane.addTab("News", news);
        
        profile = new JScrollPane(createProfile(gameManager.getUser()));
        tabbedPane.addTab("My Profile", profile);
        
        myGames = new JScrollPane();
        tabbedPane.addTab("My Games", myGames);
        
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
        
        logout = new JButton("logout");
        logout.setToolTipText("Logout");
        logout.addActionListener(this);
        userControl.add(logout);
        
        alignFriendsNorth = new JPanel();
        alignFriendsNorth.setLayout(new BorderLayout());
        
        JPanel friends = createFriendPanel(gameManager.getUser().getFriends());
        alignFriendsNorth.add(friends, BorderLayout.NORTH);
        
        JScrollPane friendsScrollPane = new JScrollPane(alignFriendsNorth);
        friendsScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        east.add(friendsScrollPane, BorderLayout.CENTER);
        
        JPanel searchBorder = new JPanel();
        searchBorder.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        east.add(searchBorder, BorderLayout.SOUTH);
        
        searchFriends = new JTextField(20);
        searchFriends.addKeyListener(this);
        searchFriends.setToolTipText("Search for a friend.");
        searchBorder.add(searchFriends);
        
        setPreferredSize(new Dimension(640, 480));
        pack();
        setVisible(true);  
    }
    
    public static JPanel createFriendPanel(User[] friends) {
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 0;
        c.insets = new Insets(2, 3, 0, 3);
        
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
            
            JPopupMenu friendMenu = new JPopupMenu();
            friendMenu.add(new JMenuItem("Show Profile"));
            friendMenu.add(new JMenuItem("Chat"));
            friend.setComponentPopupMenu(friendMenu);
            
            JLabel friendThumbnail = new JLabel(friends[i].getUserImage());
            friendThumbnail.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            friend.add(friendThumbnail, BorderLayout.WEST);
                  
            JLabel friendName = new JLabel(friends[i].getUsername());
            friendName.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
            friend.add(friendName, BorderLayout.CENTER);
            
            container.add(friend, c);
            c.gridy++;
        }
        return container;
    }
    
    public static JPanel createProfile(User user) {
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
    
    public void showMyGames() {
        mainWindow.removeAll();
    }
    
    public void showGameExplorer() {
        mainWindow.removeAll();
        
        JPanel topLine = new JPanel();
        topLine.setLayout(new BorderLayout());
        mainWindow.add(topLine, BorderLayout.NORTH);
        
        JLabel gameExplorer = new JLabel("Game Explorer");
        gameExplorer.setFont(gameExplorer.getFont().deriveFont(48));
        topLine.add(gameExplorer, BorderLayout.WEST);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));
        topLine.add(searchPanel, BorderLayout.EAST);
        
        JTextField search = new JTextField(22);
        search.setToolTipText("Search for a game");
        searchPanel.add(search);
        
        JPanel mid = new JPanel();
        mid.setLayout(new GridLayout(0, 2));
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(exit)) {
            System.exit(0);
        }
        if(e.getSource().equals(logout)) {
            dispose();
            new LoginScreen();
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
                User[] allFriends = gameManager.getUser().getFriends();
                List<User> filtered = new ArrayList();
                for (User u : allFriends) {
                    String username = u.getUsername();
                    if(username.toLowerCase().contains(keyword.toLowerCase())) {
                        filtered.add(u);
                    }
                }
                User[] filteredArray = filtered.toArray(new User[filtered.size()]);
                JPanel friends = createFriendPanel(filteredArray);
                alignFriendsNorth.add(friends, BorderLayout.NORTH);
                alignFriendsNorth.revalidate();
                alignFriendsNorth.repaint();
            }
        }
    }
}
