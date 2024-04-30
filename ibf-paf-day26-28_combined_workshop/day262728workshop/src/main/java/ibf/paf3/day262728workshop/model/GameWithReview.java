package ibf.paf3.day262728workshop.model;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
public class GameWithReview extends Game {
    private List<Review> reviews;
    
    public GameWithReview() {
    }

    public GameWithReview(int gid, ObjectId _id, String name, int year, int ranking, int users_rated,
            String url, String image, LocalDateTime timestamp, List<Review> reviews) {
        super(gid, _id, name, year, ranking, users_rated, url, image, timestamp);
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "GameWithReview [game_id=" + getGid() + 
                ", name=" + getName() + 
                ", year=" + getYear() +
                ", ranking=" + getRanking() + 
                ", users_rated=" + getUsersRated() + 
                ", url=" + getUrl() + 
                ", image=" + getImage() + 
                ", reviews=" + reviews + "]";
    }
}
