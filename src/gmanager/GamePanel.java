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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;

public class GamePanel extends JPanel {
    
    private final Game game;
    
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
        center.setLayout(new BorderLayout());
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
        descriptionPanel.setPreferredSize(new Dimension(400, 100));
        info.add(descriptionPanel);
        
        JTextPane text = new JTextPane();
        text.setText(game.getDescription());
        text.setEditable(false);
        descriptionPanel.setViewportView(text);
        
        JPanel ratingPanel = new JPanel();
        ratingPanel.setBorder(BorderFactory.createTitledBorder("User Ratings"));
        ratingPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints d = new GridBagConstraints();
        d.anchor = GridBagConstraints.NORTHWEST;
        d.fill = GridBagConstraints.NONE;
        d.weightx = 0;
        d.weighty = 1;
        d.gridx = 0;
        d.gridy = -1;
        d.insets = new Insets(0, 0, 2, 10);
        
        float fun = game.getRating(Game.RATING_FUN);
        float inc = game.getRating(Game.RATING_INCENTIVE);
        float gra = game.getRating(Game.RATING_GRAPHIC);
        float pri = game.getRating(Game.RATING_PRICE_PERFORMANCE);
        float[] ratings = {fun, inc, gra, pri};
        boolean paintRating = false;
        
        for(int i = 0; i < ratings.length; i++) {
            if(ratings[i] >= 0) {
                paintRating = true;
                d.gridy++;
                d.weightx = 0;
                d.gridx = 0;
                String ratingText = "";
                switch (i) {
                    case 0: ratingText = "Fun:"; break;
                    case 1: ratingText = "Incentive:"; break;
                    case 2: ratingText = "Graphic:"; break;
                    case 3: ratingText = "Price/Performance:"; break;
                }
                ratingPanel.add(new JLabel(ratingText), d);

                d.gridx++;
                ratingPanel.add(new Ratingbar(ratings[i]), d);
                d.gridx++;
                d.weightx = 1;
                float labelText = Math.round(ratings[i] * 10) / 10.0f;
                ratingPanel.add(new JLabel("" + labelText), d);
            }
        }

        
        JScrollPane scrollRating = new JScrollPane();
        if(paintRating) {
            scrollRating.setViewportView(ratingPanel);
        }
        c.gridy++;
        center.add(scrollRating, BorderLayout.CENTER);
        
        JLabel ratingHeading = new JLabel("Ratings");
        f = ratingHeading.getFont();
        ratingHeading.setFont(f.deriveFont(f.getSize2D() + 5.0f));
        scrollRating.setColumnHeaderView(ratingHeading);

        this.game = game;
    }
}
