package ibf.paf3.day27.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instock {
    
    private String warehouse;
    private Integer qty;
}
