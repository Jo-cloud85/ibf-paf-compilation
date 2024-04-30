package ibf.paf3.day262728workshop.repo;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import ibf.paf3.day262728workshop.model.Game;
import ibf.paf3.day262728workshop.model.GameSummary;
import ibf.paf3.day262728workshop.model.GameWithReview;
import ibf.paf3.day262728workshop.model.Review;

@Repository
public class ReviewRepository {
 
    @Autowired
    private MongoTemplate template;

    // ======================================================================================================
    // Day 26
    // ====================================================================================================== 
    /*
    db.games.find(
        {
            name: { $regex: 'button', $options: 'i' }   
        }
    ).count();
    */
    public long getTotalGamesCount(String substrName) {
        //make sure you import the correct Query
        Query query = new Query(
            Criteria.where("name")
                    .regex(substrName, "i")
        ); 
        return template.count(query, "games");
    }

    /*
    db.games.find(
        {
            _id: ObjectId('65b32e122da1824ea35a3b7b')   
        }
    )
     */
    public Game findGameById(ObjectId objId) {
        Query query = new Query(
            Criteria.where("_id").is(objId)
        );
        return template.findOne(query, Game.class, "games");
    }

    // Day 26 (a) -----------------------------
    /*
    db.games.find(
        {
            name: { $regex: 'button', $options: 'i' }   
        }
    ).skip(0).limit(25)
     */
    public List<GameSummary> findGamesByNameWithPagination(String substrName, int limit, int offset) {
        //make sure you import the correct Query
        Query query = new Query(
            Criteria.where("name")
                    .regex(substrName, "i")
        ); 
        query
            .skip(limit*offset)
            .limit(limit);
        return template.find(query, GameSummary.class, "games");
    }

    // Day 26 (b) -----------------------------
    /*
    db.games.find(
        {
            name: { $regex: 'button', $options: 'i' }   
        }
    )
    .skip(0).limit(25)
    .sort({ rank: 1 })
     */
    public List<GameSummary> findGamesByNameWithPaginationAndRank(String substrName, int limit, int offset) {
        Query query = new Query(
            Criteria.where("name")
                    .regex(substrName, "i")
        ); 
        query
            .skip(limit*offset)
            .limit(limit)
            .with(Sort.by(Sort.Direction.ASC, "ranking"));
        return template.find(query, GameSummary.class, "games");
    }


    // ======================================================================================================
    // Day 27 
    // ====================================================================================================== 

    /*
        db.games.find({
            gid: 11
        })
        .count() //If no count, this query will return the record, then just change the method accordingly
    */
    // Checking if ID from games collection exist
    public boolean doesGameIdExist(int gameId) {
        Query query = new Query(Criteria.where("gid").is(gameId));
        return template.exists(query, Game.class);
    }


    /*
        db.reviews.find({
            _id: ObjectId("662fb9d1bd58d730ceb418b7")
        })
        .count() //If no count, this query will return the record, and then you just change the method accordingly
    */
    public boolean doesReviewObjIdExist(ObjectId reviewObjId) {
        Query query = new Query(Criteria.where("_id").is(reviewObjId));
        return template.exists(query, Review.class);
    }


    // Day 27 (a) -----------------------------
    /*
        db.reviews.insert({
            user: "rimuru",
            rating: 9,
            comment: "",
            ID: 11,
            posted: date,
            name: "Bohnanza"
        })
    */
    public Review createReview(Review review) {
        return template.insert(review);
    }
    

    // Day 27 (b) -----------------------------
    /*
        db.reviews.updateOne(
            { _id: ObjectId("662fc81cbd58d730ceb418bf") },
            { $push: { edited: { rating: 7, c_text: "gonna recommend this to my friends", posted: new Date() } } }
        )
    */
    public long updateReview(ObjectId reviewObjId, Integer rating, String comment) {
        Query query = Query.query(Criteria.where("_id").is(reviewObjId));
        Update updateOps = new Update()
                .addToSet("edited", 
                    new Document("rating", rating)
                        .append("c_text", comment)
                        .append("posted", new Date()));
        UpdateResult updateResult = template.updateFirst(query, updateOps, Review.class, "reviews");
        return updateResult.getModifiedCount();
    }


    // Day 27 (c & d) --------------------------
    /*
        db.reviews.aggregate([
            { $match: { "_id": ObjectId("662fd46fbd58d730ceb418d1")} },
            {
                $project: {
                    "user": 1,
                    "ID": 1,
                    "name": 1,
                    "rating": 1,
                    "posted": 1,
                    "c_text": {
                        $ifNull: [
                            { $arrayElemAt: ["$edited.c_text", -1] }, // Get latest "c_text" fr "edited" array using $arrayElemAt
                            "$c_text" // If "edited" array is empty or null, use the outer "c_text"
                        ]
                    },
                    "edited": 1
                }
            }
        ])
    */
    public Review getReview(ObjectId reviewObjId) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("_id").is(reviewObjId));

        // Project fields and replace outer c_text with the latest c_text from edited array
        ProjectionOperation projectOperation = Aggregation
            .project("user", "ID", "name", "rating", "posted", "c_text", "edited")
            .andExclude("_id")
            //.andExpression("ifElse(isEmpty('$edited'), false, true)").as("edited") //if you incl this, it can't map to Review.class so you do it in ReviewService instead
            .and("edited.c_text").arrayElementAt(-1).as("c_text"); // Get the latest element from edited array

        // Execute the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, projectOperation);
        AggregationResults<Review> aggregationResults = template.aggregate(aggregation, "reviews", Review.class);
        return aggregationResults.getUniqueMappedResult(); // Return the result
    }


    // ======================================================================================================
    // Day 28
    // ====================================================================================================== 

    // Day 28 (a) --------------------------
    /*
        db.games.aggregate([
            { $match: { gid: 14 }},
            { 
                $lookup: {
                    from: "reviews",
                    localField: "gid",
                    foreignField: "ID",
                    as: "reviews"
                }
            },
            {
                $project: {
                    "gid": 1,
                    "name": 1,
                    "year": 1,
                    "ranking": 1,
                    "users_rated": 1,
                    "url": 1,
                    "image": 1,
                    "reviews": 1
                }
            }
        ])
    */
    public GameWithReview getGameWithReviews(int gameId) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("gid").is(gameId));
        LookupOperation lookupOperation = Aggregation.lookup("reviews", "gid", "ID", "reviews");

        ProjectionOperation projectOperation = Aggregation
            .project(
                "gid", 
                "name", 
                "year", 
                "ranking", 
                "users_rated", 
                "url", 
                "image", 
                "reviews")
            .andExclude("_id");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, lookupOperation, projectOperation);
        // If you don't want to create another object, then use Document, and outputType will be Document.class
        AggregationResults<GameWithReview> aggregationResults = template.aggregate(aggregation, "games", GameWithReview.class);
        return aggregationResults.getUniqueMappedResult();
    }

    // Day 28 (b) --------------------------
    /*
        db.games.aggregate([
            { 
                $lookup: {
                    from: "comments",
                    foreignField: "gid",
                    localField: "gid",
                    pipeline: [
                        {$sort: {rating: -1}},
                        {$limit:1}
                    ],
                    as: "reviews"
                }
            },
            {$unwind:"$reviews"},
            {$project: {
                _id: 0,
                gid: 1, 
                name: 1,
                rating: "$reviews.rating",
                user: "$reviews.user",
                comment: "$reviews.c_text",
                review_id: "$reviews.c_id"
            }},
            {$limit:10}
        ])
    */
    public List<Document> getListofGamesWithRating (String highestOrlowest) {
        Direction direction = Direction.DESC;
        if ("lowest".equalsIgnoreCase(highestOrlowest))
            direction = Direction.ASC;
            
        LookupOperation lookupOperation = Aggregation.lookup()
            .from("comments")
            .localField("gid")
            .foreignField("gid")
            .pipeline(
                Aggregation.sort(Sort.by(direction, "rating")), 
                Aggregation.limit(1))
            .as("reviews");

        UnwindOperation unwindOperation = Aggregation.unwind("reviews");

        LimitOperation limitOperation = Aggregation.limit(10);

        ProjectionOperation projectOperation = Aggregation
            .project()
            .andExclude("_id") //you have to exclude this first since you want gid as _id
            .and("gid").as("_id")
            .and("name").as("name")
            .and("reviews.rating").as("rating")
            .and("reviews.user").as("user")
            .and("reviews.c_text").as("comment")
            .and("reviews.c_id").as("review_id");
            
        
        Aggregation aggregation = Aggregation.newAggregation(lookupOperation, unwindOperation, limitOperation,projectOperation);
        AggregationResults<Document> aggregationResults = template.aggregate(aggregation, "games", Document.class);

        System.out.println(aggregationResults.getMappedResults());
        return aggregationResults.getMappedResults();
    }
}
