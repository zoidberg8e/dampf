package gmanager;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


/**
 *
 * @author zoidberg
 */

public class GameExplorer extends JPanel implements PopupMenuListener {
    
    private JPanel content;

    public GameExplorer() {
        super();
        setLayout(new BorderLayout());
        
        content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 2));
        content.setLayout(new GridBagLayout());
        add(content, BorderLayout.NORTH);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridy = 0;
        c.insets = new Insets(2, 0, 15, 0);
        
    }
    
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
//        UserPopupMenu u = (UserPopupMenu) e.getSource();
//        contextMenuUser = u.getUser();
    }
    
    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
    
    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {}
} 
