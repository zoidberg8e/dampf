/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
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
/**
 *
 * @author zoidberg
 */
public class GameExplorer extends JFrame {
    private JTextField searchField;
    private JButton check, add, cancel;
    private Border standartBorder;
    private JTable found;
    private JScrollPane scroll;
    private DefaultTableModel model;
    private JLabel errorLabel;
    private MainGUI mainGUI;
    private Border standardBorder;
    
    public GameExporer(MainGUI mainGUI){
        
        super("Game Explorer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        add(content);
        
        JPanel north = new JPanel();
        north.setBorder(BorderFactory.createTitledBorder("Name of the Game"));
        north.setLayout(new BorderLayout(5, 5));
        content.add(north, BorderLayout.NORTH);
        
        searchField = new JTextField(40);
        standardBorder = searchField.getBorder();
        north.add(searchField, BorderLayout.CENTER);
        
        check = new JButton("CheckGame");
        check.addActionListener(this);
        north.add(check, BorderLayout.EAST);
        
        String[] columnNames = {"Game"};
        
        model = new DefaultTableModel(columnNames, 0);
        
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
        //add.setEnabled(false);
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
            User requested = new User((String) found.getValueAt(index, 1));
            addFriend(requested);
        }
        else if(e.getSource().equals(cancel)) {
            dispose();
        }
    }
        
} 
