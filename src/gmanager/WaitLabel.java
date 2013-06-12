
package gmanager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

public class WaitLabel extends JLabel implements ActionListener {
    
    private int alpha = 0;
    private Timer timer;
    
    public WaitLabel(int delay) {

        setPreferredSize(new Dimension(60, 60));
        //setBorder(BorderFactory.createLineBorder(Color.black));
        timer = new Timer(delay, this);
        timer.start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke(1));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //g2.drawOval(5, 5, 50, 50);
        //g2.drawLine(0, 0, 60, 60);
        //g2.drawLine(60, 0, 0, 60);
        //g2.drawLine(30, 0, 30, 60);
        //g2.drawLine(0, 30, 60, 30);

        g2.setColor(new Color(0, 0, 0, alpha));
        g2.fillOval(39, 5, 7, 7);   // 1 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 22) % 243));
        g2.fillOval(48, 14, 7, 7);  // 2 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 44) % 243));
        g2.fillOval(52, 27, 7, 7);  // 3 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 66) % 243));
        g2.fillOval(48, 40, 7, 7);  // 4 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 88) % 243));
        g2.fillOval(39, 49, 7, 7);  // 5 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 110) % 243));
        g2.fillOval(27, 52, 7, 7);  // 6 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 132) % 243));
        g2.fillOval(15, 49, 7, 7);  // 7 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 154) % 243));
        g2.fillOval(6, 40, 7, 7);   // 8 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 176) % 243));
        g2.fillOval(2, 27, 7, 7);   // 9 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 198) % 243));
        g2.fillOval(6, 14, 7, 7);   // 10 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 220) % 243));
        g2.fillOval(15, 5, 7, 7);   // 11 Uhr
        
        g2.setColor(new Color(0, 0, 0, (alpha + 242) % 243));
        g2.fillOval(27, 2, 7, 7);   // 12 Uhr
        
        Toolkit.getDefaultToolkit().sync();
        
        g2.dispose();
    }

    
    public void stop() {
        timer.stop();
    }
    
    private void shiftColors() {
        alpha = (alpha + 22) % 243;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("tut");
        shiftColors();
        repaint();
    }
}
