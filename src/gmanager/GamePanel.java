package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class GamePanel extends JPanel implements MouseListener {
    
    private final Game game;
    private JTable table;
    private ArrayList<Review> reviews;
    
    public GamePanel(Game game, User u) {
        super();
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setLayout(new BorderLayout());
        
        JPanel header = new JPanel();
        header.setLayout(new GridBagLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(header, BorderLayout.PAGE_START);
        
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 10, 0);
        
        JLabel heading = new JLabel(game.getName());
        Font f = heading.getFont();
        heading.setFont(f.deriveFont(f.getSize2D() + 20.0f));
        header.add(heading, c);
        
        c.gridy++;
        c.insets = new Insets(0, 0, 0, 0);
        header.add(new JSeparator(), c);

        JPanel center = new JPanel();
        center.setLayout(new BorderLayout(0, 20));
        add(center, BorderLayout.CENTER);
        
        ImageIcon imageIcon = game.getImage();
        if(imageIcon.getIconWidth() != 192 || imageIcon.getIconHeight() != 192) {
            Image image = imageIcon.getImage();
            image = image.getScaledInstance(192, 192, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
        }
        
        JPanel info = new JPanel();
        info.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        center.add(info, BorderLayout.NORTH);
        
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        info.add(imageLabel);
        
        JScrollPane descriptionPanel = new JScrollPane();
        descriptionPanel.setPreferredSize(new Dimension(500, 120));
        info.add(descriptionPanel);
        
        JTextPane text = new JTextPane();
        text.setText(game.getDescription());
        text.setEditable(false);
        descriptionPanel.setViewportView(text);
        
        JPanel reviewPanel = new JPanel();
        reviewPanel.setBorder(BorderFactory.createTitledBorder("User Ratings"));
        reviewPanel.setLayout(new BorderLayout());
        reviewPanel.setBorder(BorderFactory.createTitledBorder("Reviews"));
        center.add(reviewPanel, BorderLayout.CENTER);
        
        JPanel averageRating = new JPanel();
        averageRating.setBorder(BorderFactory.createTitledBorder("User Ratings"));
        averageRating.setLayout(new GridBagLayout());
        reviewPanel.add(averageRating, BorderLayout.NORTH);

        GridBagConstraints d = new GridBagConstraints();
        d.anchor = GridBagConstraints.NORTHWEST;
        d.fill = GridBagConstraints.NONE;
        d.weightx = 0;
        d.weighty = 0;
        d.gridx = 0;
        d.gridy = -1;
        d.insets = new Insets(0, 0, 0, 10);
        
        float fun = game.getRating(Game.RATING_FUN);
        float inc = game.getRating(Game.RATING_INCENTIVE);
        float gra = game.getRating(Game.RATING_GRAPHIC);
        float pri = game.getRating(Game.RATING_PRICE_PERFORMANCE);
        float all = game.getRating(Game.RATING_OVERALL);
        float[] ratings = {fun, inc, gra, pri, all};
        
        for(int i = 0; i < ratings.length; i++) {
            if(ratings[i] < 0) {
                ratings[i] = 0;
            }
            d.gridx = 0;
            d.gridy++;
            d.weightx = 0;
            if(i == 4) {
                averageRating.add(new JSeparator(SwingConstants.HORIZONTAL), d);
                d.gridwidth = 3;
                d.gridy++;
            }
            d.gridwidth = 1;

            String ratingText = "";
            switch (i) {
                case 0: ratingText = "Fun:"; break;
                case 1: ratingText = "Incentive:"; break;
                case 2: ratingText = "Graphic:"; break;
                case 3: ratingText = "Price/Performance:"; break;
                case 4: ratingText = "Summary"; break;
            }
            averageRating.add(new JLabel(ratingText), d);

            d.gridx++;
            averageRating.add(new Ratingbar(ratings[i]), d);
            d.gridx++;
            d.weightx = 1;
            float labelText = Math.round(ratings[i] * 10) / 10.0f;
            averageRating.add(new JLabel("" + labelText), d);
        }

        JScrollPane scrollReviews = new JScrollPane();
        reviewPanel.add(scrollReviews, BorderLayout.CENTER);
        
        String[] columnNames = {"User", "Rating"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.addMouseListener(this);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollReviews.setViewportView(table);
        
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        
        reviews = DBConnector.getInstance().getReviews(game.getID());
        for (Review review : reviews) {
            String[] row = {review.getUser().getUsername(), "" + review.getOverallRating()};
            model.addRow(row);
        }
        
        JPanel yourReview = new JPanel();
        yourReview.setBorder(BorderFactory.createTitledBorder("Your Review"));
        yourReview.setLayout(new BorderLayout());
        reviewPanel.add(yourReview, BorderLayout.SOUTH);
        
        JPanel yourRating = new JPanel();
        yourRating.setLayout(new GridBagLayout());
        yourReview.add(yourRating, BorderLayout.NORTH);
        
        GridBagConstraints e = new GridBagConstraints();
        e.anchor = GridBagConstraints.NORTHWEST;
        e.fill = GridBagConstraints.NONE;
        e.weighty = 1;
        e.gridx = 0;
        e.gridy = -1;
        e.insets = new Insets(0, 0, 0, 10);
        
        for(int i = 0; i < 4; i++) {

            e.gridy++;
            e.weightx = 0;
            e.gridx = 0;
            String ratingText = "";
            switch (i) {
                case 0: ratingText = "Fun:"; break;
                case 1: ratingText = "Incentive:"; break;
                case 2: ratingText = "Graphic:"; break;
                case 3: ratingText = "Price/Performance:"; break;
                case 4: ratingText = "Summary"; break;
            }
            yourRating.add(new JLabel(ratingText), e);

            e.gridx++;
            yourRating.add(new Ratingbar(0), e);
            e.gridx++;
            e.weightx = 1;
            int labelText = 0;
            yourRating.add(new JLabel("" + labelText), e);
        }
        
        JScrollPane scrollYourText = new JScrollPane();
        scrollYourText.setPreferredSize(new Dimension(500, 120));
        yourReview.add(scrollYourText, BorderLayout.WEST);
        
        JTextPane yourText = new JTextPane();
        yourText.setText("");
        scrollYourText.setViewportView(yourText);
        
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
