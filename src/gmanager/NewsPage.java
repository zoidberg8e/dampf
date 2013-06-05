package gmanager;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class NewsPage extends JPanel {
    
    public NewsPage() {
        super();
        setLayout(new BorderLayout());
        
        JLabel heading = new JLabel("News");
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        Font f = heading.getFont();
        heading.setFont(f.deriveFont(f.getSize2D() + 15.0f));
        add(heading, BorderLayout.NORTH);
        
        
    }
}
