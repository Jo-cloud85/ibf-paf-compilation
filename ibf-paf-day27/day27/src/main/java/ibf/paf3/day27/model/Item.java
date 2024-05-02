package ibf.paf3.day27.model;

import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    
    @Id
    private ObjectId _id;
    private String item;
    private List<Instock> instock = new LinkedList<>();
    
}
