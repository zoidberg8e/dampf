package gmanager;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class NewsPage extends JPanel {
    
    private JLabel news;
    
    public NewsPage(String newsText) {
        super();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel heading = new JLabel("News");
        heading.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        Font f = heading.getFont();
        heading.setFont(f.deriveFont(f.getSize2D() + 15.0f));
        add(heading, BorderLayout.NORTH);

        news = new JLabel(newsText);
        news.setVerticalAlignment(SwingConstants.TOP);
        news.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(news);
        add(scroll, BorderLayout.CENTER);
    }
    
    public void setNews(String text) {
        news.setText(text);
    }
}
