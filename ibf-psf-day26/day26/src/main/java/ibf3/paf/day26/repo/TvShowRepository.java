package ibf3.paf.day26.repo;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import ibf3.paf.day26.Constants;

@Repository
public class TvShowRepository {

    @Autowired
    private MongoTemplate template;

    /**
     * During assessment you need to write the Mongo version of the query as well
     * db.tvshows.find({
     *     name: {$regex: 'name', $options: 'i'},
     *     genres: { $all: ['Drama', 'Thriller']}
     * })
     * .projection({ name: 1, genres: 1})
     * .sort({ name: -1 })
     * */
    public List<Document> findShowsByName(String name) {
        // criteria is your predicate

        // Create the filter
        Criteria criteria = Criteria
            .where(Constants.F_NAME)
            .regex(name, "i")
            .and(Constants.F_GENRES)
            .all("Drama", "Thriller");
            // .is(name);

        // Create thre query with thefoletr
        Query query = Query
            .query(criteria)
            .with(Sort.by(Direction.DESC, Constants.F_NAME))
            .limit(5);
        query
            .fields()
            .include(Constants.F_NAME, Constants.F_GENRES);

        return template.find(query, Document.class, Constants.C_TVSHOWS);
    }

    /*
     * db.tvshows.find({
     *    language: { $regex: 'language', $options: 'i'}
     * }).count()
     */
    public long countShowsByLanguage(String lang) {
        // anything inside the db.tvshows.find is under criteria, otherwise, it is part of the query
        Criteria criteria = Criteria
            .where(Constants.F_LANGUAGE)
            .regex(lang, "i");

        Query query = Query.query(criteria);
        
        return template.count(query, Constants.C_TVSHOWS);
    }

    /*
     * db.tvshows.distinct("type", { "rating.average": { $gte: 7.0 }})
     */
    public List<String> getTypesByRating(float rating) {
        Criteria criteria = Criteria
            .where(Constants.F_AVERAGE_RATING)
            .gte(rating);

        Query query = Query.query(criteria);
        
        return template.findDistinct(query, Constants.F_TYPE, Constants.C_TVSHOWS, String.class);
    }
}
