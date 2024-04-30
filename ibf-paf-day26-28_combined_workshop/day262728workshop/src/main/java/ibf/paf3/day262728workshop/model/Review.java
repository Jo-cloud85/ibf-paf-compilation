package ibf.paf3.day262728workshop.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review extends EditedReview {
    
    private ObjectId _id;
    private String user; //name of user who posted the review
    private int ID; //references to the gid in games collection
    private String name; //references to the name of board game in games collection
    private List<EditedReview> edited = new LinkedList<>();

    public Review(String c_text, int rating, LocalDateTime posted, ObjectId _id, String user, int ID, 
        String name, List<EditedReview> edited) {
        super(c_text, rating, LocalDateTime.now());
        this._id = _id;
        this.user = user;
        this.ID = ID;
        this.name = name;
        this.edited = (edited != null) ? edited : new LinkedList<>();
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EditedReview> getEdited() {
        return edited;
    }

    public void setEdited(List<EditedReview> edited) {
        this.edited = edited;
    }

    @Override
    public String toString() {
        return "Review{" +
                " _id=" + _id +
                ", user=" + user + '\'' +
                ", rating=" + getRating() +
                ", c_text='" + getC_text() + '\'' +
                ", ID=" + ID +
                ", posted=" + getPosted() +
                ", name='" + name + '\'' +
                ", edited=" + edited +
                '}';
    }

}
