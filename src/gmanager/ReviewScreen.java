package gmanager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class ReviewScreen extends JFrame implements ActionListener, WindowListener{
    
    private JButton save, cancel, close;
    private Review review;
    private MainGUI mainGUI;
    private Ratingbar fun, incentive, graphic, price;
    private JTextPane yourText;
    private Game game;
    private User user;
    
    public ReviewScreen(Review review, boolean editable, MainGUI mainGUI, Game game, User user) {
        super("User Review");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        JPanel content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.setLayout(new BorderLayout());
        cp.add(content, BorderLayout.CENTER);
        
        JPanel yourRating = new JPanel();
        yourRating.setBorder(BorderFactory.createEmptyBorder(2, 2, 5, 2));
        yourRating.setLayout(new GridBagLayout());
        content.add(yourRating, BorderLayout.NORTH);
        
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.NONE;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 1, 10);
        
        c.weightx = 0;
        yourRating.add(new JLabel("Fun:"), c);
        
        float value = 0;
        if(review != null) {
            value = review.getRating(Review.RATING_FUN);
        }
        fun = new Ratingbar(value);
        fun.setEditable(editable);
        c.gridx++;
        c.weightx = 1;
        yourRating.add(fun, c);
        
        c.weightx = 0;
        c.gridx = 0;
        c.gridy++;
        yourRating.add(new JLabel("Incentive:"), c);
        
        value = 0;
        if(review != null) {
            value = review.getRating(Review.RATING_INCENTIVE);
        }
        incentive = new Ratingbar(value);
        incentive.setEditable(editable);
        c.gridx++;
        c.weightx = 1;
        yourRating.add(incentive, c);
        
        c.weightx = 0;
        c.gridx = 0;
        c.gridy++;
        yourRating.add(new JLabel("Graphic:"), c);

        value = 0;
        if(review != null) {
            value = review.getRating(Review.RATING_GRAPHIC);
        }
        graphic = new Ratingbar(value);
        graphic.setEditable(editable);
        c.gridx++;
        c.weightx = 1;
        yourRating.add(graphic, c);
        
        c.weightx = 0;
        c.gridx = 0;
        c.gridy++;
        yourRating.add(new JLabel("Price/Performance:"), c);
        
        value = 0;
        if(review != null) {
            value = review.getRating(Review.RATING_PRICE_PERFORMANCE);
        }
        price = new Ratingbar(value);
        price.setEditable(editable);
        c.gridx++;
        c.weightx = 1;
        yourRating.add(price, c);
        
        JScrollPane scrollYourText = new JScrollPane();
        scrollYourText.setPreferredSize(new Dimension(500, 120));
        content.add(scrollYourText, BorderLayout.WEST);
        
        String text = "";
        if(review != null) {
            text = review.getText();
        }
        JTextPane yourText = new JTextPane();
        yourText.setEditable(editable);
        yourText.setText(text);
        scrollYourText.setViewportView(yourText);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        c.gridx++;
        content.add(buttonPanel, BorderLayout.SOUTH);
        
        save = new JButton("Save");
        save.addActionListener(this);
        if(!editable) {
            save.setVisible(false);
        }
        buttonPanel.add(save);
        
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        if(!editable) {
            cancel.setVisible(false);
        }
        buttonPanel.add(cancel);
        
        close = new JButton("Close");
        close.addActionListener(this);
        if(editable) {
            close.setVisible(false);
        }
        buttonPanel.add(close);

        this.review = review;
        this.mainGUI = mainGUI;
        this.user = user;
        this.game = game;
        
        pack();
        setVisible(true);
        setAlwaysOnTop(true);
    }
    
    public void close() {
        dispose();
        mainGUI.setEnabled(true);
        mainGUI.toFront();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource(); 
        if(source.equals(close) || source.equals(cancel)) {
            close();
        }
        else if(source.equals(save)) {
            if(fun.getValue() == 0 ||
               incentive.getValue() == 0 ||
               graphic.getValue() == 0 ||
               price.getValue() == 0) {
                JOptionPane.showMessageDialog(null, "Your have to rate all categories to save your rating.", "Invalid Rating", JOptionPane.ERROR_MESSAGE);
            }
            else {
                int funValue = (int) fun.getValue();
                int incValue = (int) incentive.getValue();
                int graValue = (int) graphic.getValue();
                int priValue = (int) price.getValue();
                String text = yourText.getText();
                if(review == null) {
                    DBConnector.getInstance().createReview(user.getID(), game.getID(), funValue, incValue, graValue, priValue, text);
                }
                else {
                    review.setRating(Review.RATING_FUN, funValue);
                    review.setRating(Review.RATING_INCENTIVE, incValue);
                    review.setRating(Review.RATING_GRAPHIC, graValue);
                    review.setRating(Review.RATING_PRICE_PERFORMANCE, priValue);
                    review.setText(text);
                }
                close();
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        close();
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
