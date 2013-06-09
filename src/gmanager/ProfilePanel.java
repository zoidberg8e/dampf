package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 *
 * @author patrickd
 */
public class ProfilePanel extends JPanel implements ActionListener {
    
    private User user;
    private boolean editable;
    private JButton editProfile = null;
    
    public ProfilePanel(User user, boolean editable) {
        
        super();
        setLayout(new BorderLayout());
        
        JPanel topLine = new JPanel();
        topLine.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        topLine.setLayout(new BorderLayout(8, 10));
        add(topLine, BorderLayout.NORTH);
        
        JLabel userImage = new JLabel(user.getUserImage());
        userImage.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        topLine.add(userImage, BorderLayout.WEST);
        
        JLabel userName = new JLabel(user.getUsername());
        Font f = userName.getFont();
        Float s = f.getSize2D();
        s += 20.0f;
        userName.setFont(f.deriveFont(s));
        userName.setVerticalAlignment(SwingConstants.BOTTOM);
        topLine.add(userName, BorderLayout.CENTER);
        
        if(editable) {
            editProfile = new JButton("edit");
            editProfile.addActionListener(this);
            editProfile.setPreferredSize(new Dimension(10,10));
            topLine.add(editProfile, BorderLayout.EAST);
        }
        
        JSeparator sep = new JSeparator();
        topLine.add(sep, BorderLayout.SOUTH);
        
        JPanel userInfo = new JPanel();
        userInfo.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        userInfo.setLayout(new GridBagLayout());
        add(userInfo, BorderLayout.WEST);
        
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.insets = new Insets(0, 0, 10, 15);
        
        JLabel ageLabel = new JLabel("Age:");
        userInfo.add(ageLabel, c);
        
        int userAge = user.getAge();
        if(userAge > 0) {
            JLabel age = new JLabel("" + user.getAge());
            c.gridx++;
            userInfo.add(age, c);
        }

        this.user = user;
        this.editable = editable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
