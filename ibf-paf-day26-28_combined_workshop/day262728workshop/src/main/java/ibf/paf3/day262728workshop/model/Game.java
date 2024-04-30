package ibf.paf3.day262728workshop.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

@Document(collection = "games")
public class Game extends GameSummary {
    
    // order does not matter BUT name must match MongoDB collection column names exactly;

    private int gid;
    private int year;
    private int ranking;
    private int users_rated;
    private String url;
    private String image;
    private LocalDateTime timestamp; //this is the one we added

    public Game() {
        this.timestamp = LocalDateTime.now();
    }

    public Game(int gid, ObjectId _id, String name, int year, int ranking, int users_rated, 
        String url, String image, LocalDateTime timestamp) {
        super(_id, name);
        this.gid = gid;
        this.year = year;
        this.ranking = ranking;
        this.users_rated = users_rated;
        this.url = url;
        this.image = image;
        this.timestamp = timestamp;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getUsersRated() {
        return users_rated;
    }

    public void setUsersRated(int users_rated) {
        this.users_rated = users_rated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String toString() {
        return "Game [game_id=" + gid +
            ", Id=" + get_id() + 
            ", name=" + getName() + 
            ", year=" + year + 
            ", ranking=" + ranking + 
            ", users_rated=" + users_rated + 
            ", url=" + url + 
            ", image=" + image + 
            ", timestamp=" + timestamp + "]";
    }
}

