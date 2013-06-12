/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gmanager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author zoidberg
 */
public class GameList extends JPanel implements ActionListener {
    private Game game;
    private JScrollPane gameList;
    private JLabel header;
    private JTextField SearchField;
    private JButton Search;
    
    public GameList (){
        super();
        setLayout(new BorderLayout(4,5));
        JPanel Searchbar = new JPanel();
        Searchbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(Searchbar,BorderLayout.PAGE_START);
        SearchField = new JTextField(100);
        Search = new JButton("Search");
        Searchbar.add(SearchField);
        Search.addActionListener(this);
        gameList = new JScrollPane();
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}