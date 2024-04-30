package ibf.paf3.day262728workshop.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
public class GameSummary {

    @Id
    private ObjectId _id;
    private String name;

    public GameSummary() {
    }

    public GameSummary(ObjectId _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GameSummary [gameId=" + _id + ", name=" + name + "]";
    }
}

