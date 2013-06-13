package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ProfilePanel extends JPanel implements ActionListener, MouseListener {
    
    private User user;
    private boolean editable;
    private JButton editProfile = null;
    private JTextPane age, icq, jabber;
    private DefaultTableModel model;
    private JTable table;
    
    public ProfilePanel(User user, boolean editable) {
        
        super();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        JPanel topLine = new JPanel();
        topLine.setLayout(new BorderLayout(8, 10));
        add(topLine, BorderLayout.NORTH);
        
        ImageIcon userIcon = user.getImage();
        Image original = userIcon.getImage();
        
        int size = 96;
        JLabel userImage = new JLabel(new ImageIcon(original.getScaledInstance(-1, size, Image.SCALE_SMOOTH)));
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
            JPanel editButtonPanel = new JPanel();
            editButtonPanel.setLayout(new BorderLayout());
            topLine.add(editButtonPanel, BorderLayout.EAST);
            
            editProfile = new JButton("edit");
            editProfile.addActionListener(this);
            editButtonPanel.add(editProfile, BorderLayout.SOUTH);
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
        if(userAge > 0) {          
            age = new JTextPane();
            age.setText("" + userAge);
            age.setBorder(null);
            age.setBackground(null);
            age.setEditable(false);
            age.setOpaque(false);
            d.gridx++;
            d.weightx = 1;
            userInfo.add(age, d);
        }
        
        JLabel icqLabel = new JLabel("ICQ:");
        d.weightx = 0;
        d.gridx = 0;
        d.gridy++;
        d.weighty = 0;
        userInfo.add(icqLabel, d);
        
        int userICQ = user.getICQ();
        if(userICQ > 0) {
            icq = new JTextPane();
            icq.setText("" + userICQ);
            icq.setBackground(null);
            icq.setBorder(null);
            icq.setEditable(false);
            icq.setOpaque(false);
            d.gridx++;
            userInfo.add(icq, d);
        }
        
        JLabel jabberLabel = new JLabel("Jabber:");
        d.gridx = 0;
        d.gridy++;
        userInfo.add(jabberLabel, d);
        
        String userJabber = user.getJabber();
        if(userJabber != null) {
            jabber = new JTextPane();
            jabber.setText("" + user.getJabber());
            jabber.setBackground(null);
            jabber.setBorder(null);
            jabber.setEditable(false);
            jabber.setOpaque(false);
            d.gridx++;
            userInfo.add(jabber, d);
        }
        
        c.gridy++;
        center.add(new JSeparator(), c);
        
        JPanel myGames = new JPanel();
        myGames.setLayout(new BorderLayout());
        myGames.setBorder(BorderFactory.createTitledBorder("My Games"));
        c.gridy++;
        c.weighty = 1;
        center.add(myGames, c);
        
        String[] columnNames = {"Game", "Playtime", "Playing"};
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
        
        this.user = user;
        this.editable = editable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    public void updateGames(ArrayList<String[]> gameList) {
        model.setRowCount(0);
        for(int i = 0; i < gameList.size(); i ++) {
            model.addRow(gameList.get(i));
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
            JPopupMenu popup = new JPopupMenu();
            popup.add(new JMenuItem("Toggle Playing"));
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
