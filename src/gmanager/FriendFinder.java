package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class FriendFinder extends JFrame implements ActionListener {
    
    private JTextField searchField;
    private JButton check, add, cancel;
    private Border standardBorder;
    private JTable found;
    private JScrollPane scroll;
    private DefaultTableModel model;
    private JLabel errorLabel;
    private MainGUI mainGUI;
    
    public FriendFinder(MainGUI mainGUI) {
        
        super("Friend Finder");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        add(content);
        
        JPanel north = new JPanel();
        north.setBorder(BorderFactory.createTitledBorder("Enter E-Mail address or username"));
        north.setLayout(new BorderLayout(5, 5));
        content.add(north, BorderLayout.NORTH);
        
        searchField = new JTextField(40);
        standardBorder = searchField.getBorder();
        north.add(searchField, BorderLayout.CENTER);
        
        check = new JButton("Check");
        check.addActionListener(this);
        north.add(check, BorderLayout.EAST);
        
        String[] columnNames = {"Username", "E-Mail"};
        
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        
        found = new JTable(model);
        found.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = found.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    ListSelectionModel sm = (ListSelectionModel) e.getSource();
                    if(sm.getMinSelectionIndex() == -1) {
                        add.setEnabled(false);
                    }
                    else {
                        add.setEnabled(true);
                    }
                }
            }
        });
        
        errorLabel = new JLabel("No matching users found.");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
  
        scroll = new JScrollPane();
        scroll.setPreferredSize(new Dimension(scroll.getPreferredSize().width, 150));
        content.add(scroll, BorderLayout.CENTER);
        
        JPanel south = new JPanel();
        south.setBorder(BorderFactory.createEmptyBorder(6, 5, 6, 10));
        south.setLayout(new BorderLayout());
        content.add(south, BorderLayout.SOUTH);
        
        JPanel southEast = new JPanel();
        southEast.setLayout(new GridLayout(1, 0, 5, 5));
        south.add(southEast, BorderLayout.EAST);
        
        add = new JButton("Add");
        add.setEnabled(false);
        add.addActionListener(this);
        southEast.add(add);
        
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        southEast.add(cancel);
        
        pack();

        this.mainGUI = mainGUI;
    }
    
    private void addFriend(User requested) {
        User requestor = mainGUI.getGManager().getUser();
        DBConnector.getInstance().requestFriend(requestor, requested);
        mainGUI.updateFriendPanel();
        dispose();
    }
    
    private void checkUsers() {
        model.setRowCount(0);
        String text = searchField.getText();
        if(text.equals("")) {
            searchField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
        else {
            searchField.setBorder(standardBorder);
            User[] result = DBConnector.getInstance().searchUsers(text);
            
            for (User user : result) {
                String[] rowData = {user.getUsername(), user.getEmail()};
                model.addRow(rowData);
            }
        }
        if(model.getRowCount() == 0) {
            scroll.setViewportView(errorLabel);
        }
        else {
            scroll.setViewportView(found);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(check)) {
            checkUsers();
        }
        else if(e.getSource().equals(add)) {
            int index = found.getSelectedRow();
            String email = (String) found.getValueAt(index, 1);
            int ID = DBConnector.getInstance().getUserID(email);
            if (ID != -1) {
                User requested = new User(ID);
                addFriend(requested);
            }
        }
        else if(e.getSource().equals(cancel)) {
            dispose();
        }
    }
}
