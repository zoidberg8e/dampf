package gmanager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

public class CollapseButton extends JButton {
    
    private boolean collapsed = false;
    
    public CollapseButton() {
        
        int size = 16;
        setPreferredSize(new Dimension(size, size));
        setToolTipText("Collapse");
        //Make the button looks the same for all Laf's
        setUI(new BasicButtonUI());
        //Make it transparent
        setContentAreaFilled(false);
        //No need to be focusable
        setFocusable(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        
        int delta = 5;
        if(collapsed) {
            g2.drawLine(getWidth()/2, delta, getWidth()/2, getHeight() - delta);
        }
        g2.drawLine(delta, getHeight()/2, getWidth()- delta, getHeight()/2);
        
        g2.dispose();
    }
    
    public void setCollapsed(boolean collapsed) {
        if(collapsed) {
            setToolTipText("Extend");
        }
        else {
            setToolTipText("Collapse");
        }
        this.collapsed = collapsed;
    }
    
    public boolean isCollapsed() {
        return collapsed;
    }
}
