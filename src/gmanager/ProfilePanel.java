package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ProfilePanel extends JPanel implements ActionListener, MouseListener {
    
    private User user;
    private JButton editProfile, save, cancel, uploadImage;
    private JTextField age, icq, jabber;
    private DefaultTableModel model;
    private JTable table;
    private JMenuItem togglePlaying, showGame, remove;
    private JPopupMenu popup;
    private ArrayList<String[]> gamelist;
    private String[] selectedItem;
    private JTabbedPane tab;
    private MainGUI mainGUI;
    private Border standardBorder;
    private JLabel userImage;
    
    public ProfilePanel(User user, boolean editable, JTabbedPane tab, MainGUI mainGUI) {
        
        super();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        JPanel topLine = new JPanel();
        topLine.setLayout(new BorderLayout(8, 10));
        add(topLine, BorderLayout.NORTH);
        
        ImageIcon userIcon = user.getImage();
        Image original = userIcon.getImage();
        
        int size = 96;
        userImage = new JLabel(new ImageIcon(original.getScaledInstance(-1, size, Image.SCALE_SMOOTH)));
        userImage.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        topLine.add(userImage, BorderLayout.WEST);
        
        JLabel userName = new JLabel(user.getUsername());
        Font f = userName.getFont();
        Float s = f.getSize2D();
        s += 20.0f;
        userName.setFont(f.deriveFont(s));
        userName.setVerticalAlignment(SwingConstants.BOTTOM);
        topLine.add(userName, BorderLayout.CENTER);
        
        if(editable) {
            JPanel alignSouth = new JPanel();
            alignSouth.setLayout(new BorderLayout());
            topLine.add(alignSouth, BorderLayout.EAST);
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            alignSouth.add(buttonPanel, BorderLayout.SOUTH);
            
            uploadImage = new JButton("Upload Image");
            uploadImage.addActionListener(this);
            buttonPanel.add(uploadImage);
            
            editProfile = new JButton("Edit");
            editProfile.addActionListener(this);
            buttonPanel.add(editProfile);

            save = new JButton("Save");
            save.setVisible(false);
            save.addActionListener(this);
            buttonPanel.add(save);
            
            cancel = new JButton("Cancel");
            cancel.setVisible(false);
            cancel.addActionListener(this);
            buttonPanel.add(cancel);
        }
        
        JSeparator sep = new JSeparator();
        topLine.add(sep, BorderLayout.SOUTH);
        
        JPanel center = new JPanel();
        center.setLayout(new GridBagLayout());
        add(center, BorderLayout.CENTER);
        
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 0, 10, 0);
        
        JPanel userInfo = new JPanel();
        userInfo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        userInfo.setLayout(new GridBagLayout());
        center.add(userInfo, c);

        GridBagConstraints d = new GridBagConstraints();
        d.anchor = GridBagConstraints.WEST;
        d.fill = GridBagConstraints.NONE;
        d.gridx = 0;
        d.gridy = 0;
        d.insets = new Insets(0, 0, 10, 30);
        
        JLabel ageLabel = new JLabel("Age:");
        userInfo.add(ageLabel, d);
        
        int userAge = user.getAge(); 
        
        age = new JTextField(9);
        standardBorder = age.getBorder();
        if(userAge > 0) {
            age.setText("" + userAge);
        }
        age.setEditable(false);
        d.gridx++;
        d.weightx = 1;
        userInfo.add(age, d);
        
        JLabel icqLabel = new JLabel("ICQ:");
        d.weightx = 0;
        d.gridx = 0;
        d.gridy++;
        d.weighty = 0;
        userInfo.add(icqLabel, d);
        
        int userICQ = user.getICQ();

        icq = new JTextField(9);
        if(userICQ > 0) {
            icq.setText("" + userICQ);
        }
        icq.setEditable(false);
        d.gridx++;
        userInfo.add(icq, d);
        
        JLabel jabberLabel = new JLabel("Jabber:");
        d.gridx = 0;
        d.gridy++;
        userInfo.add(jabberLabel, d);
        
        String userJabber = user.getJabber();

        jabber = new JTextField(9);
        if(userJabber != null) {
            jabber.setText("" + user.getJabber());
        }
        jabber.setEditable(false);
        d.gridx++;
        userInfo.add(jabber, d);
        
        c.gridy++;
        center.add(new JSeparator(), c);
        
        JPanel myGames = new JPanel();
        myGames.setLayout(new BorderLayout());
        myGames.setBorder(BorderFactory.createTitledBorder("My Games"));
        c.gridy++;
        c.weighty = 1;
        center.add(myGames, c);
        
        String[] columnNames = {"Game", "Playing"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.addMouseListener(this);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myGames.add(table, BorderLayout.CENTER);
        
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        myGames.add(header, BorderLayout.NORTH);
        
        updateGames(user.getGames());
        initalizeMenu();
        
        this.user = user;
        this.tab = tab;
        this.mainGUI = mainGUI;
    }
    
    private void initalizeMenu() {
        popup = new JPopupMenu();
        
        showGame = new JMenuItem("Show Game");
        showGame.addActionListener(this);
        popup.add(showGame);
        
        togglePlaying = new JMenuItem("Toggle Playing");
        togglePlaying.addActionListener(this);
        popup.add(togglePlaying);
        
        popup.addSeparator();
        
        remove = new JMenuItem("Remove");
        remove.addActionListener(this);
        popup.add(remove);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(editProfile)) {
            age.setBorder(standardBorder);
            icq.setBorder(standardBorder);
            jabber.setBorder(standardBorder);
            
            age.setEditable(true);
            icq.setEditable(true);
            jabber.setEditable(true);
            
            age.requestFocus();
            
            editProfile.setVisible(false);
            save.setVisible(true);
            cancel.setVisible(true);
        }
        else if(e.getSource().equals(uploadImage)) {

            JFileChooser fc = new JFileChooser();
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(new gmanager.ImageFilter());
            int returnVal = fc.showOpenDialog(null);
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String file = fc.getSelectedFile().getAbsolutePath();
                user.setImage(file);
                
                ImageIcon userIcon = user.getImage();
                Image original = userIcon.getImage();
        
                int size = 96;
                userImage.setIcon(new ImageIcon(original.getScaledInstance(-1, size, Image.SCALE_SMOOTH)));
            }
        }
        else if(e.getSource().equals(save)) {
            age.setBorder(standardBorder);
            icq.setBorder(standardBorder);
            jabber.setBorder(standardBorder);
            
            boolean valid = true;
            
            String ageText = age.getText();
            String icqText = icq.getText();
            String jabberText = jabber.getText();
            
            if(ageText.equals("") || ageText.matches("[0-9]{1,3}")) {}
            else {
                valid = false;
                age.setBorder(BorderFactory.createLineBorder(Color.red, 2));
            }
            if(icqText.equals("") || icqText.matches("[0-9]{9}")) {}
            else {
                valid = false;
                icq.setBorder(BorderFactory.createLineBorder(Color.red, 2));
            }
            if(jabberText.length() > 100) {
                valid = false;
                jabber.setBorder(BorderFactory.createLineBorder(Color.red, 2));
            }
            if(valid == true) {
                user.setAge(Integer.parseInt(ageText));
                user.setICQ(Integer.parseInt(icqText));
                user.setJabber(jabberText);
                
                age.setEditable(false);
                icq.setEditable(false);
                jabber.setEditable(false);
                
                save.setVisible(false);
                cancel.setVisible(false);
                editProfile.setVisible(true);
            }
        }
        else if(e.getSource().equals(cancel)) {
            int userAge = user.getAge();
            if(userAge > 0) {
                age.setText("" + userAge);
            }
            else {
                age.setText("");
            }
            
            int userICQ = user.getICQ();
            if(userICQ > 0) {
                icq.setText("" + userICQ);
            }
            else {
                icq.setText("");
            }
            
            String userJabber = user.getJabber();
            if(userJabber != null) {
                jabber.setText("" + userJabber);
            }
            else {
                jabber.setText("");
            }
            
            age.setEditable(false);
            icq.setEditable(false);
            jabber.setEditable(false);
            
            save.setVisible(false);
            cancel.setVisible(false);
            editProfile.setVisible(true);
        }
        else if(e.getSource().equals(showGame)) {
            int tabIndex = tab.indexOfTab(selectedItem[1]);
            if(tabIndex == -1) {
                tab.addTab(selectedItem[1], new GamePanel(new Game(Integer.parseInt(selectedItem[0]), selectedItem[1]), user, mainGUI, this));
            }
            tab.setSelectedIndex(tab.indexOfTab(selectedItem[1]));
        }
        else if(e.getSource().equals(togglePlaying)) {
            if(selectedItem[2].equals("No")) {
                DBConnector.getInstance().setUserGamePlaying(user.getID(), Integer.parseInt(selectedItem[0]), true);
            }
            else {
                DBConnector.getInstance().setUserGamePlaying(user.getID(), Integer.parseInt(selectedItem[0]), false);
            }
            updateGames(DBConnector.getInstance().getUserGames(user.getID()));
        }
        else if(e.getSource().equals(remove)) {
            DBConnector.getInstance().removeUserGame(user.getID(), Integer.parseInt(selectedItem[0]));
            updateGames(DBConnector.getInstance().getUserGames(user.getID()));
        }
    }
    
    public void updateGames(ArrayList<String[]> gameList) {
        this.gamelist = gameList;
        model.setRowCount(0);
        for(int i = 0; i < gameList.size(); i ++) {
            String[] row = gameList.get(i);
            model.addRow(new String[]{row[1], row[2]});
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        int r = table.rowAtPoint(e.getPoint());
        if (r >= 0 && r < table.getRowCount()) {
            table.setRowSelectionInterval(r, r);
        }
        else {
            table.clearSelection();
        }
        
        int rowindex = table.getSelectedRow();
        if(rowindex < 0) {
            return;
        }
        if(e.getButton() == 3 && e.getComponent() instanceof JTable) {
            selectedItem = gamelist.get(rowindex);
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
