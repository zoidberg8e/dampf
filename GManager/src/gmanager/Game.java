package gmanager;

import javax.swing.ImageIcon;

/**
 *
 * @author patrick
 */
public class Game {
    
    private final int ID;
    private final String name;
    private int[] rating = {0, 0, 0, 0, 0};
    private String description;
    private ImageIcon image;
    
    public Game(int ID) {
        this.ID = ID;
        this.name = "blubb";
    }
    
    public float getAverageRating() {
        int quantity = 0;
        int total = 0;
        for (int i = 0; i < rating.length; i++) {
            quantity += rating[i];
            total += rating[i] * (i + 1);
        }
        if (quantity == 0) {
            return 0.0f;
        }
        float avg = (float) total / (float) quantity;
        avg = Math.round(avg * 100) / 100.0f;
        return avg;
    }
    
    public void addRating(int r) {
        rating[r - 1] = rating[r - 1] + 1;
    }
}
