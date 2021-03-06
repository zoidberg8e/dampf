package gmanager;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

public class InfoTextField extends JTextField implements FocusListener {    
    private String infoText;
    private boolean isEmpty;
    
    public InfoTextField(int size, String infoText) {
        super(size);
        addFocusListener(this);
        isEmpty = true;
        this.infoText = infoText;
        showInfo();
    }
    
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(isEmpty) {
            hideInfo();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(getText().equals("")) {
            isEmpty = true;
            showInfo();
        }
        else {
            isEmpty = false;
        }
    }
    
    private void showInfo() {
        setText(infoText);
        setForeground(Color.GRAY);
    }
    
    private void hideInfo() {
        setText("");
        setForeground(Color.BLACK);
    }
}
