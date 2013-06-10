package gmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicTextUI;

/**
 *
 * @author patrickd
 */
public class ProfilePanel extends JPanel implements ActionListener {
    
    private User user;
    private boolean editable;
    private JButton editProfile = null;
    JTextField age;
    JTextField icq;
    
    public ProfilePanel(User user, boolean editable) {
        
        super();
        setLayout(new BorderLayout());
        
        JPanel topLine = new JPanel();
        topLine.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        topLine.setLayout(new BorderLayout(8, 10));
        add(topLine, BorderLayout.NORTH);
        
        ImageIcon userIcon = user.getUserImage();
        Image original = userIcon.getImage();
        
        int size = 96;
        JLabel userImage = new JLabel(new ImageIcon(original.getScaledInstance(-1, size, Image.SCALE_SMOOTH)));
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
            JPanel editButtonPanel = new JPanel();
            editButtonPanel.setLayout(new BorderLayout());
            topLine.add(editButtonPanel, BorderLayout.EAST);
            
            editProfile = new JButton("edit");
            editProfile.addActionListener(this);
            editButtonPanel.add(editProfile, BorderLayout.SOUTH);
        }
        
        JSeparator sep = new JSeparator();
        topLine.add(sep, BorderLayout.SOUTH);
            
        JPanel westAlign = new JPanel();
        westAlign.setLayout(new BorderLayout());
        add(westAlign, BorderLayout.WEST);
        
        JPanel userInfo = new JPanel();
        userInfo.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        userInfo.setLayout(new GridBagLayout());
        westAlign.add(userInfo, BorderLayout.NORTH);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 10, 15);
        
        JLabel ageLabel = new JLabel("Age:");
        userInfo.add(ageLabel, c);
        
        int userAge = user.getAge();
        if(userAge > 0) {
            age = new JTextField("" + userAge, 9);
            age.setEditable(false);
            c.gridx++;
            userInfo.add(age, c);
        }
        
        JLabel icqLabel = new JLabel("ICQ:");
        c.gridx = 0;
        c.gridy++;
        c.weighty = 0;
        userInfo.add(icqLabel, c);
        
        int userICQ = user.getICQ();
        if(userICQ > 0) {
            icq = new JTextField("" + userICQ, 9);
            icq.setEditable(false);
            c.gridx++;
            userInfo.add(icq, c);
        }
        
        this.user = user;
        this.editable = editable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
