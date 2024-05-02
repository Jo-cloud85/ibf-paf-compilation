package ibf.paf3.day27.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

import ibf.paf3.day27.model.Person;

@Service
public class PersonService {

    @Autowired
    MongoTemplate mongoTemplate;

    public UpdateResult updatePerson(String conditionKey, String conditionValue, Person personRecord, String collectionName) {
        Query query = new Query(Criteria.where(conditionKey).is(conditionValue));

        Update updateOps = new Update()
			.set("name", personRecord.getName())
            .set("age", personRecord.getAge())
			.push("hobbies")
			.each(personRecord.getHobbies());
		
		UpdateResult updateResult = mongoTemplate.upsert(query, updateOps, collectionName);

		System.out.println("Slide 13 results: " + updateResult.getModifiedCount());

        return updateResult;
    }
}
