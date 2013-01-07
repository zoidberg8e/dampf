package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class MainGUI extends JFrame implements ActionListener {
    
    private JMenuItem exit;
    private JScrollPane myGames, profile, explorer, news;
    private JPanel mainWindow;
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
        
        profile = new JScrollPane();
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
        
        JPanel alignNorth = new JPanel();
        alignNorth.setLayout(new BorderLayout());
        
        JPanel friends = new JPanel();
        friends.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 0;
        c.insets = new Insets(2, 3, 0, 3);
        JPanel[] friend = createFriendPanels();
        for (JPanel panel : friend) {
            friends.add(panel, c);
            c.gridy++;
        }
        alignNorth.add(friends, BorderLayout.NORTH);
        
        JScrollPane friendsScrollPane = new JScrollPane(alignNorth);
        friendsScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        east.add(friendsScrollPane, BorderLayout.CENTER);
        
        searchFriends = new JTextField(20);
        searchFriends.setToolTipText("Search for a friend.");
        east.add(searchFriends, BorderLayout.SOUTH);
        
        pack();
        setVisible(true);  
    }
    
    public JPanel[] createFriendPanels() {
        User[] friends = gameManager.getUser().getFriends();
        JPanel[] panels = new JPanel[friends.length];
        for (int i = 0; i < panels.length; i++) {
            JPanel friend = new JPanel();
            friend.setLayout(new BorderLayout());
            
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
            
            friend.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
            
            panels[i] = friend;
        }
        return panels;
    }
    
    public void showProfile() {
        mainWindow.removeAll();
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
        
        JTextField search = new JTextField(20);
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
}
