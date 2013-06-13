package gmanager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

public class Ratingbar extends JLabel implements MouseListener {
    
    private float value;
    private boolean editable = false;
    
    public Ratingbar(float value) {
        this.value = value;
        addMouseListener(this);
        Dimension size = new Dimension(191, 21);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }
    
    public void setEditable(boolean editable) {
        if(editable) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        this.editable = editable;
    }
    
    public float getValue() {
        return value;
    }
    
    private void setValue(float value) {
        this.value = value;
        repaint();
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
        
        float drawnValue = Math.round(value * 10.0f) / 10.0f;
        g2.drawString("" + drawnValue, 160, 16);

    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if(editable) {
            int newValue = 1;
            int x = e.getX();
            if(x > 150) {
                return;
            }
            if(x > 30) {
                newValue = 2;
            }
            if(x > 60) {
                newValue = 3;
            }
            if(x > 90) {
                newValue = 4;
            }
            if(x > 120) {
                newValue = 5;
            }
            setValue(newValue);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
