package ibf.paf3.day23workshop.model;

public class Game {
    private String gameName;
    private int gameReviewCount;
    private double gameAvgRating;

    public Game() {
    }

    public Game(String gameName, int gameReviewCount, double gameAvgRating) {
        this.gameName = gameName;
        this.gameReviewCount = gameReviewCount;
        this.gameAvgRating = gameAvgRating;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getGameReviewCount() {
        return gameReviewCount;
    }

    public void setGameReviewCount(int gameReviewCount) {
        this.gameReviewCount = gameReviewCount;
    }

    public double getGameAvgRating() {
        return gameAvgRating;
    }

    public void setGameAvgRating(double gameAvgRating) {
        this.gameAvgRating = gameAvgRating;
    }
}
