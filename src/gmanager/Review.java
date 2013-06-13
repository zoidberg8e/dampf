package gmanager;

public class Review {
    
    private final int ID;
    private String text = null;
    private User user = null;
    private int fun = -1;
    private int incentive = -1;
    private int graphic = -1;
    private int pricePerformance = -1;
    
    public static final int RATING_FUN = 0;
    public static final int RATING_INCENTIVE = 1;
    public static final int RATING_GRAPHIC = 2;
    public static final int RATING_PRICE_PERFORMANCE = 3;
    
    public Review(int ID, User user, int funRating, int incentiveRating, int graphicRating, int priceRating) {
        this.ID = ID;
        this.user = user;
        
        this.fun = funRating;
        this.incentive = incentiveRating;
        this.graphic = graphicRating;
        this.pricePerformance = priceRating;
    }
    
    public Review(int ID) {
        this.ID = ID;
    }
    
    public User getUser() {
        if(user == null) {
            user = DBConnector.getInstance().getReviewUser(ID);
        }
        return user;
    }
    
    public int getRating(int type) {
        switch(type) {
            case RATING_FUN: 
                if(fun == -1) {
                    updateRatings();
                }
                return fun;
            case RATING_INCENTIVE: 
                if(incentive == -1) {
                    updateRatings();
                }
                return incentive;
            case RATING_GRAPHIC: 
                if(graphic == -1) {
                    updateRatings();
                }
                return graphic;
            default: 
                if(pricePerformance == -1) {
                    updateRatings();
                }
                return pricePerformance;
        }
    }
        
    public float getOverallRating() {
        if(fun == -1 ||
           incentive == -1 ||
           graphic == -1 ||
           pricePerformance == -1) {
            updateRatings();
        }
        float total = fun + incentive + graphic + pricePerformance;
        float average = total / 4;
        return average;
    }
    
    public void updateRatings() {
        int[] ratings = DBConnector.getInstance().getReviewRatings(ID);
        
        fun = ratings[0];
        incentive = ratings[1];
        graphic = ratings[2];
        pricePerformance = ratings[3];
    }
    
    public int getID() {
        return ID;
    }
}
