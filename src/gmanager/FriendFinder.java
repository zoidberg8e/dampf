package gmanager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author doldpa
 */
public class FriendFinder {
    
    private JTextField searchField;
    private JButton check, add, cancel;
    
    public FriendFinder() {
        
    }
    
    public void drawGUI() {
        
        JFrame friendFinder = new JFrame("Friend Finder");
        friendFinder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        friendFinder.setResizable(false);
        
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        friendFinder.add(content);
        
        JPanel north = new JPanel();
        north.setBorder(BorderFactory.createTitledBorder("Enter E-Mail address or username"));
        north.setLayout(new BorderLayout(5, 5));
        content.add(north, BorderLayout.NORTH);
        
        searchField = new JTextField(40);
        north.add(searchField, BorderLayout.CENTER);
        
        check = new JButton("Check");
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        north.add(check, BorderLayout.EAST);
        
        String[] columnNames = {"Username", "E-Mail"};
        
        JTable found = new JTable();
        found.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scroll = new JScrollPane(found);
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
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        southEast.add(add);
        
        cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        southEast.add(cancel);
        
        friendFinder.pack();
        friendFinder.setVisible(true);
    }
}
