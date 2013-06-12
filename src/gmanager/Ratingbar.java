package gmanager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

public class Ratingbar extends JLabel {
    
    private float value;
    
    public Ratingbar(float value) {
        this.value = value;
        setPreferredSize(new Dimension(150, 30));
    }
    
    
        @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke(1));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int size = Math.round(value * 30.0f);
        g2.setColor(new Color(255, 255, 255));
        g2.fillRoundRect(0, 0, 150, 20, 10, 10);
        
        g2.setColor(new Color(0, 255, 255));
        g2.fillRoundRect(0, 0, size, 20, 10, 10);
        
        g2.setColor(Color.black);
        g2.drawRoundRect(0, 0, 150, 20, 10, 10);
        
        g2.drawLine(30, 0, 30, 20);
        g2.drawLine(60, 0, 60, 20);
        g2.drawLine(90, 0, 90, 20);
        g2.drawLine(120, 0, 120, 20);
    }
}
