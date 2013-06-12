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
    
    public GamePanel(Game game) {
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
        center.setLayout(new GridBagLayout());
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
        center.add(info, c);
        
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        info.add(imageLabel);
        
        JScrollPane descriptionPanel = new JScrollPane();
        descriptionPanel.setPreferredSize(new Dimension(400, 100));
        info.add(descriptionPanel);
        
        JTextPane text = new JTextPane();
        text.setText("Das ist die Beschreibung von Diablo III\nDiablo III ist super und so, bla!");
        text.setEditable(false);
        descriptionPanel.setViewportView(text);
        
        JPanel ratingPanel = new JPanel();
        
        this.game = game;
    }
}
