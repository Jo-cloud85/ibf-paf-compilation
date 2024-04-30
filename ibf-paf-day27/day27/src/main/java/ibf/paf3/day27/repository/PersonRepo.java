package ibf.paf3.day27.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import ibf.paf3.day27.exception.DuplicatePersonException;
import ibf.paf3.day27.model.Person;

@Repository
public class PersonRepo {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    // day27 - slide 3
    // db.movies.insert({
    //     _id: 1,
    //     name: "Pang Ching Kuang",
    //     age: 18,
    //     gender: "M",
    //     hobbies: ["gaming", "coding"],
    // })

    // insert method will throw an error if the id already exists
    public Person insertPerson(Person person) {
        Person insertedPerson = mongoTemplate.insert(person);
        return insertedPerson;
    }

    // save method will overwrite if the id already exists
    public Person savePerson(Person person) {
        // Check if the person already exists
        Query query = new Query(Criteria.where("name").is(person.getName()));
        Person existingPerson = mongoTemplate.findOne(query, Person.class);

        if (existingPerson != null) {
            // If the person already exists, throw a custom exception
            throw new DuplicatePersonException("Person with name " + person.getName() + " already exists.");
        }

        // If the person doesn't exist, save them
        Person savedPerson = mongoTemplate.save(person);
        return savedPerson;
    }

    public List<Person> findAllPersons(Person person) {
        return mongoTemplate.findAll(Person.class); //mongodb will auto-map
    }
    
    // db.persons.deleteOne({
    //     _id: 1
    // })
    public void deletePerson(Person person) {
        mongoTemplate.remove(person);
    }

    public Person updatePerson(Person person) {
        Person updatedPerson = mongoTemplate.save(person);
        return updatedPerson;
    }

    // day27 - slide 7 & 11
    // db.persons.updateOne(
    //     { _id: ObjectId("xxxxxxxxxxxx")},
    //     { 
    //         $set: {name: "Emily"},
    //         $inc: {age: 2}
    //     }
    // )
    public void findAndUpdatePerson(ObjectId objId, Person person) {
        Query query = Query.query(Criteria.where("_id").is(objId));

        Update updateOperation = new Update()   
            .set("name", person.getName())
            .inc("age", 1);
        
        UpdateResult result = mongoTemplate.updateMulti(query, updateOperation, "persons");

        System.out.printf("Document updated: %d\n", result.getModifiedCount());
    } 
}
