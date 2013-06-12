package gmanager;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Game {
    
    private final int ID;
    private String name = null;
    private String developer = null;
    private int[] ratingFun;
    private int[] ratingIncentive;
    private int[] ratingGraphic;
    private int[] ratingPricePerformance;
    private String description;
    private ImageIcon image;
    
    public static final int RATING_FUN = 0;
    public static final int RATING_INCENTIVE = 1;
    public static final int RATING_GRAPHIC = 2;
    public static final int RATING_PRICE_PERFORMANCE = 3;
    public static final int RATING_OVERALL = 4;
    
    public Game(int ID, String name, String developer) {
        this.ID = ID;
        this.name = name;
        this.developer = developer;
    }
    
    public Game(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }
    
    public Game(int ID) {
        this.ID = ID;
    }
    
    public int getID() {
        return ID;
    }
    
    public String getName() {
        if(name == null) {
            name = DBConnector.getInstance().getGameName(ID);
        }
        return name;
    }
    
    public String getDeveloper() {
        if(developer == null) {
            developer = DBConnector.getInstance().getGameDeveloper(ID);
        }
        return developer;
    }
    
    public float getAverageRating(int type) {
        return 4.1245f;
    }
    
    private float getAverage(ArrayList<Integer> list) {
        int size = list.size();
        if(size == 0) {
            return -1;
        }
        int total = 0;
        for (int rating : list) {
            total += rating;
        }
        float average = (float) total / (float) size;
        System.out.println(average);
        return average;
    }
    
//    public float getAverageRating() {
//        int quantity = 0;
//        int total = 0;
//        for (int i = 0; i < rating.length; i++) {
//            quantity += rating[i];
//            total += rating[i] * (i + 1);
//        }
//        if (quantity == 0) {
//            return 0.0f;
//        }
//        float avg = (float) total / (float) quantity;
//        avg = Math.round(avg * 100) / 100.0f;
//        return avg;
//    }
//    
//    public void addRating(int r) {
//        rating[r - 1] = rating[r - 1] + 1;
//    }
    
    public ImageIcon getImage() {
        if(image == null) {
            image = DBConnector.getInstance().getGameImage(ID);
        }
        return image;
    }
}
