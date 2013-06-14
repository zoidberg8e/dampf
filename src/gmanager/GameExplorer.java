package gmanager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class GameExplorer extends JPanel implements ActionListener, MouseListener {
    
    private JTextField searchField;
    private JButton searchButton;
    private DefaultTableModel model;
    private JTable found;
    private JScrollPane scroll;
    private JTabbedPane tab;
    private ArrayList<Game> games;
    private User user;
    private MainGUI mainGUI;
    private ProfilePanel userProfile;
    
    private final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                                       "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                                       "U", "V", "W", "X", "Y", "Z"};

    public GameExplorer(JTabbedPane tab, User user, MainGUI mainGUI, ProfilePanel userProfile) {
        super();
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Game Explorer");
        heading.setOpaque(false);
        heading.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        Font f = heading.getFont();
        heading.setFont(f.deriveFont(f.getSize2D() + 15.0f));
        add(heading, BorderLayout.NORTH);
        
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        add(content, BorderLayout.CENTER);

        JPanel searchbar = new JPanel();
        searchbar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchbar.setLayout(new BorderLayout(10, 0));
        content.add(searchbar, BorderLayout.NORTH);
        
        JPanel alphabetic = new JPanel();
        alphabetic.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
        searchbar.add(alphabetic, BorderLayout.CENTER);
        
        for( String lable : alphabet) {
            JButton button = new JButton(lable);
            button.addActionListener(this);
            alphabetic.add(button);
        }
        
        JPanel east = new JPanel();
        east.setLayout(new FlowLayout(FlowLayout.RIGHT));
        searchbar.add(east, BorderLayout.EAST);
        
        searchField = new InfoTextField(20, "Search for games");
        east.add(searchField);
        
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        east.add(searchButton);
        
        scroll = new JScrollPane();
        content.add(scroll, BorderLayout.CENTER);
        
        String[] columnNames = {"Name", "Developer"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        found = new JTable(model);
        found.addMouseListener(this);
        found.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JTableHeader header = found.getTableHeader();
        header.setReorderingAllowed(false);
        
        this.tab = tab;
        this.userProfile = userProfile;
        this.user = user;
        this.mainGUI = mainGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String searchText = null;
        if(source.equals(searchButton)) {
            searchText = searchField.getText();
            searchText = "%" + searchText + "%";
        }
        else {
            searchText = source.getText();
            searchText += "%"; 
        }
        System.out.println(searchText);
        games = DBConnector.getInstance().getGamesLike(searchText);
        setTable(games);
    }
    
    public void setTable(ArrayList<Game> games) {
        model.setRowCount(0);
        for (Game game : games) {
            String[] line = {game.getName(), game.getDeveloper()};
            model.addRow(line);
        }
        scroll.setViewportView(found);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        int r = found.rowAtPoint(e.getPoint());
        if (r >= 0 && r < found.getRowCount()) {
            found.setRowSelectionInterval(r, r);
        }
        else {
            found.clearSelection();
        }
        
        int rowindex = found.getSelectedRow();
        if(rowindex < 0) {
            return;
        }
        if(e.getClickCount() >= 2 && e.getButton() == 1 && e.getComponent() instanceof JTable) {
            Game game = games.get(rowindex);
            String gameName = game.getName();
            int tabIndex = tab.indexOfTab(gameName);
            if(tabIndex == -1) {
                tab.addTab(gameName, new GamePanel(game, user, mainGUI, userProfile));
            }
            tab.setSelectedIndex(tab.indexOfTab(gameName));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
