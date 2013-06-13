
package gmanager;

import javax.swing.JPopupMenu;

public class UserPopupMenu extends JPopupMenu {
    
    private final User user;
    private final User owner;
    
    public UserPopupMenu(User owner, User user) {
        super();
        this.user = user;
        this.owner = owner;
    }
    
    public User getUser() {
        return user;
    }
    
    public User getOwner() {
        return owner;
    }
}
