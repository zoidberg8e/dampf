package gmanager;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class NewsPage extends JScrollPane {
    
    private JTextPane news;
    
    public NewsPage(String newsText) {
        super();
        setOpaque(false);
        
        JLabel heading = new JLabel("News");
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        Font f = heading.getFont();
        heading.setFont(f.deriveFont(f.getSize2D() + 15.0f));
        setColumnHeaderView(heading);

        news = new JTextPane();
        news.setContentType("text/html");
        news.setText(newsText);
        news.setEditable(false);
        setViewportView(news);
        //news.setVerticalAlignment(SwingConstants.TOP);
        //news.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }
    
    public void setNews(String text) {
        news.setText(text);
    }
}
