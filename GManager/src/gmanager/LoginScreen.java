package gmanager;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author doldpa
 */
public class LoginScreen extends JFrame implements ActionListener, KeyListener {
    
    private JButton login, register;
    private JTextField mail;
    private JPasswordField password;
    private Border standardBorder;
    
    public LoginScreen() {
        super("Login");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container cp = getContentPane();
        cp.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(4, 5, 0, 4);
        
        JLabel nameLabel = new JLabel("E-mail:");
        cp.add(nameLabel, c);
        
        mail = new JTextField(15);
        mail.addKeyListener(this);
        standardBorder = mail.getBorder();
        c.gridx = 1;
        c.gridwidth = 2;
        cp.add(mail, c);
        
        JLabel passwordLabel = new JLabel("Passwort:");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        cp.add(passwordLabel, c);
        
        password = new JPasswordField(15);
        password.addKeyListener(this);
        c.gridx = 1;
        c.gridwidth = 2;
        cp.add(password, c);
        
        login = new JButton("Login");
        login.addActionListener(this);
        c.insets = new Insets(5, 5, 5, 0);
        c.gridwidth = 1;
        c.gridy = 2;
        cp.add(login, c);
        
        register = new JButton("Register");
        register.addActionListener(this);
        c.gridx = 2;
        c.insets = new Insets(5, 1, 5, 4);
        cp.add(register, c);
        
        pack();
        setVisible(true);
    }
    
    /**
     * 
     */
    public boolean authenticate(String user, String password) {
        return DBConnector.getInstance().checkLogin(user, password);
    }
    
    /**
     * 
     */
    public void login() {
        mail.setBorder(standardBorder);
        password.setBorder(standardBorder);

        boolean valid = true;
        String name = mail.getText();
        String pw = new String(password.getPassword());
        if (name.equals("")) {
            valid = false;
            mail.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
        if (pw.equals("")) {
            valid = false;
            password.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
        if (valid) {
            if (authenticate(name, pw)) {
                dispose();
                new MainGUI(new GManager(new User(DBConnector.getInstance().getUserID(name))));
            }
            else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.", "Access denied", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(login)) {
            login();
        }
        else {
            dispose();
            new CreateAccount();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 10) {
            login();
        }
    }
}
