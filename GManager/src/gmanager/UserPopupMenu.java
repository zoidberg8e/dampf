
package gmanager;

import javax.swing.JPopupMenu;

/**
 *
 * @author patrick
 */
public class UserPopupMenu extends JPopupMenu {
    
    private final User user;
    
    public UserPopupMenu(User u) {
        super();
        user = u;
    }
    
    public User getUser() {
        return user;
    }
}
