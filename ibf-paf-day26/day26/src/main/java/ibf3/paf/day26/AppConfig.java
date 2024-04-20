package ibf3.paf.day26;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/* You don't need this AppConfig if in your application.properties, you set the mongo uri directly to
the database name i.e. spring.data.mongodb.uri=mongodb://localhost:27017/shows */

@Configuration
public class AppConfig {
    
    @Value("${spring.data.mongodb.uri}")
    private String mongoUrl;

    @Bean 
    public MongoTemplate createMongoTemplate() {
        MongoClient client = MongoClients.create(mongoUrl);
        return new MongoTemplate(client, Constants.SHOWS_DB);
    }
}
