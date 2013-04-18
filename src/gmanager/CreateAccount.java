package gmanager;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CreateAccount extends JFrame implements ActionListener {
    
    private JButton register, cancel;
    private JTextField mail, name;
    private JPasswordField password, confirmPw;
    private Border standardBorder;
    
    public CreateAccount() {
        super("Create Account");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        Container cp = getContentPane();
        cp.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(4, 5, 0, 4);
        
        JPanel reg = new JPanel();
        reg.setLayout(new GridBagLayout());
        reg.setBorder(BorderFactory.createTitledBorder("Create Account"));
        cp.add(reg, c);  
        
        JLabel mailLabel = new JLabel("E-mail:");
        reg.add(mailLabel, c);
        
        mail = new JTextField(15);
        standardBorder = mail.getBorder();
        c.gridx = 1;
        c.gridwidth = 2;
        reg.add(mail, c);
        
        JLabel nameLabel = new JLabel("Username:");
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        reg.add(nameLabel, c);
        
        name = new JTextField(15);
        c.gridx = 1;
        c.gridwidth = 2;
        reg.add(name, c);
        
        JLabel passwordLabel = new JLabel("Passwort:");
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        reg.add(passwordLabel, c);
        
        password = new JPasswordField(15);
        c.gridx = 1;
        c.gridwidth = 2;
        reg.add(password, c);
        
        JLabel confirmLabel = new JLabel("Confirm Password:");
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 1;
        reg.add(confirmLabel, c);
        
        confirmPw = new JPasswordField(15);
        c.gridx = 1;
        c.gridwidth = 2;
        reg.add(confirmPw, c);
        
        register = new JButton("Register");
        register.addActionListener(this);
        c.insets = new Insets(5, 5, 5, 0);
        c.gridwidth = 1;
        c.gridy++;
        reg.add(register, c);
        
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        c.gridx = 2;
        c.insets = new Insets(5, 1, 5, 4);
        reg.add(cancel, c);
        
        pack();
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(register)) {
            String email = mail.getText();
            String username = name.getText();
            String pw = new String(password.getPassword());
            String pw2 = new String(confirmPw.getPassword());
            mail.setBorder(standardBorder);
            name.setBorder(standardBorder);
            password.setBorder(standardBorder);
            confirmPw.setBorder(standardBorder);

            boolean valid = true;

            if (email.equals("")) {
                valid = false;
                mail.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (username.equals("")) {
                valid = false;
                name.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (pw.equals("")) {
                valid = false;
                password.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (pw2.equals("")) {
                confirmPw.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
            if (valid) {
                if (DBConnector.getInstance().emailExists(email)) {
                    valid = false;
                    JOptionPane.showMessageDialog(null, "E-mail adress already in use.", "Invalid E-mail adress", JOptionPane.ERROR_MESSAGE);
                }
                if (DBConnector.getInstance().usernameExists(username)) {
                    valid = false;
                    JOptionPane.showMessageDialog(null, "Username already in use.", "Invalid Username", JOptionPane.ERROR_MESSAGE);
                }
                if (!pw.equals(pw2)) {
                    valid = false;
                    JOptionPane.showMessageDialog(null, "Password does not match.", "Invalid Password", JOptionPane.ERROR_MESSAGE);
                }
                if (valid) {
                    DBConnector.getInstance().createUser(email, username, pw);
                    JOptionPane.showMessageDialog(null, "Account successfully created.", "Account created", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new LoginScreen();
                }
            }
        }
        
        if (e.getSource().equals(cancel)) {
            dispose();
            new LoginScreen();
        }
    }
}
