package gmanager;

import javax.swing.ImageIcon;

public class Game {
    
    private final int ID;
    private String name = null;
    private String developer = null;
    private float ratingFun = -1;
    private float ratingIncentive = -1;
    private float ratingGraphic = -1;
    private float ratingPricePerformance = -1;
    private String description = null;
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
    
    public String getDescription() {
        if(description == null) {
           description = DBConnector.getInstance().getGameDescription(ID);
        }
        return description;
    }
    
    public String getDeveloper() {
        if(developer == null) {
            developer = DBConnector.getInstance().getGameDeveloper(ID);
        }
        return developer;
    }
    
    public float getRating(int type) {
        switch(type) {
            case RATING_FUN: 
                if(ratingFun == -1) {
                    updateRatings();
                }
                return ratingFun;
            case RATING_INCENTIVE: 
                if(ratingIncentive == -1) {
                    updateRatings();
                }
                return ratingIncentive;
            case RATING_GRAPHIC: 
                if(ratingFun == -1) {
                    updateRatings();
                }
                return ratingGraphic;
            case RATING_PRICE_PERFORMANCE: 
                if(ratingFun == -1) {
                    updateRatings();
                }
                return ratingPricePerformance;
            default:
                return overallRating();
        }
    }
    
    private float overallRating() {
        if(ratingFun == -1 ||
           ratingIncentive == -1 ||
           ratingGraphic == -1 ||
           ratingPricePerformance == -1) {
            updateRatings();
        }
        float total = ratingFun + ratingIncentive + ratingGraphic + ratingPricePerformance;
        float average = total / 4;
        return average;
    }
    
    private void updateRatings() {
        float[] ratings = DBConnector.getInstance().getGameRatings(ID);
        
        ratingFun = ratings[0];
        ratingIncentive = ratings[1];
        ratingGraphic = ratings[2];
        ratingPricePerformance = ratings[3];
    }
    
    public ImageIcon getImage() {
        if(image == null) {
            image = DBConnector.getInstance().getGameImage(ID);
        }
        return image;
    }
}
