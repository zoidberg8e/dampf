package gmanager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class MainGUI extends JFrame implements ActionListener {
    
    JMenuItem exit;
    JScrollPane myGames, profile, explorer, news;
    JPanel mainWindow;
    JTextField searchFriends;
    JButton addFriend, logout;

    public MainGUI() {
        super("GManager");
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
        
        JTabbedPane tabbedPane = new JTabbedPane();
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
        
        JPanel friends = new JPanel();
        JScrollPane friendsScrollPane = new JScrollPane(friends);
        east.add(friendsScrollPane, BorderLayout.CENTER);
        
        searchFriends = new JTextField(20);
        searchFriends.setToolTipText("Search for a friend.");
        east.add(searchFriends, BorderLayout.SOUTH);
        
        pack();
        setVisible(true);
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
