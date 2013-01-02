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
import javax.swing.JTextField;

public class MainGUI extends JFrame implements ActionListener {
    
    JMenuItem exit;
    JButton myGames, profile, explorer;
    JPanel mainWindow;

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
        
        JPanel navigation = new JPanel();
        navigation.setLayout(new GridLayout(0, 1, 2, 2));
        cp.add(navigation, BorderLayout.WEST);
        
        profile = new JButton("My Profile");
        profile.addActionListener(this);
        navigation.add(profile);
        
        myGames = new JButton("My Games");
        myGames.addActionListener(this);
        navigation.add(myGames);
        
        explorer = new JButton("Game Explorer");
        explorer.addActionListener(this);
        navigation.add(explorer);
        
        mainWindow = new JPanel();
        mainWindow.setLayout(new BorderLayout());
        
        JScrollPane scroll = new JScrollPane(mainWindow);
        cp.add(scroll, BorderLayout.CENTER);
        
        showGameExplorer();
        
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
    }
    
}
