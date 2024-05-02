package ibf.paf3.day27;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.BucketOperation;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;
import com.mongodb.internal.operation.AggregateOperation;

import ibf.paf3.day27.model.Person;
import ibf.paf3.day27.repository.PersonRepo;
import ibf.paf3.day27.service.PersonService;

@SpringBootApplication
public class Day27Application implements CommandLineRunner {

	@Autowired
	PersonRepo personRepo;

	@Autowired
	PersonService personSvc;

	@Autowired
	MongoTemplate mongoTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Day27Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Person newPerson01 = new Person("Alan", 25, "M", Arrays.asList("Coding", "Drinking", "Snorkeling"));
		// personRepo.savePerson(newPerson01);

		// Person newPerson02 = new Person("Felicia", 25, "F", Arrays.asList("Skateboarding", "Cycling", "Snorkeling"));
		// personRepo.savePerson(newPerson02);

		// Person newPerson03 = new Person("Charan", 25, "M", Arrays.asList("Coding", "Cycling", "Snorkeling"));
		// personRepo.savePerson(newPerson03);

		// ObjectId objKey = new Object("xxxxxxxxx");

		// See PersonService.java -------
		// Person updatedPerson = new Person();
		// updatedPerson.setName("Nicole Ng");
		// updatedPerson.setAge(30);
		// updatedPerson.setHobbies(Arrays.asList("Reading", "Swimming"));

		// UpdateResult result = personSvc.updatePerson("name", "Nicole", updatedPerson, "persons");

		// System.out.println("Slide 13 results: " + result);

		// ------------------------------------------------------------------------------------

		// Before you run this, you must make sure you have already created a text index in MongoDB
		/*
		 * db.persons.find({
				$text: {
					$search: "Felicia"
				}
			})
		 */

		TextCriteria textCriteria = TextCriteria
				.forDefaultLanguage()
				.matchingPhrase("Felicia");

		TextQuery textQuery = TextQuery.queryText(textCriteria);
		
		List<Document> results = mongoTemplate.find(textQuery, Document.class, "persons");

		System.out.printf("Slide 18: %s\n", results.toString());

		// ------------------------------------------------------------------------------------
		/*
		 * db.persons.find(
				{
					$text: { $search: "ng" }
				},
				{
					score: { $meta: "textScore" }
				}
			)
		*/
		TextCriteria textCriteria2 = TextCriteria
				.forDefaultLanguage()
				.matchingPhrase("ng") // must be a full word, cannot be substring
				.caseSensitive(false);

		TextQuery textQuery2 = TextQuery.queryText(textCriteria2)
			.sortByScore()
			.includeScore("score");
		
		List<Document> results2 = mongoTemplate.find(textQuery2, Document.class, "persons");

		// System.out.printf("Slide 18: %s\n", results2.toString());

		// ------------------------------------------------------------------------------------

		// Day 28 - Slide 9
		MatchOperation matchOperation = Aggregation.match(Criteria.where("Rated").is("PG"));

		Aggregation pipeline = Aggregation.newAggregation(matchOperation);

		AggregationResults<Document> aggregationResults = mongoTemplate.aggregate(
			pipeline, "movies", Document.class);

		List<Document> documentResults = aggregationResults.getMappedResults();

		// for (Document dr : documentResults) {
		// 	System.out.println("Day 28 Slide 9: " + dr);
		// }

		// ------------------------------------------------------------------------------------

		// Day 28 - Slide 11
		ProjectionOperation projectOperations = Aggregation
			.project("Title", "Year", "Rated", "Released")
			.andExclude("_id");

		Aggregation pipelineSlide11 = Aggregation.newAggregation(
			matchOperation, 
			projectOperations);

		AggregationResults<Document> aggregationResultsSlide11 = mongoTemplate.aggregate(
			pipelineSlide11, "movies", Document.class);

		List<Document> documentResultsSlide11 = aggregationResultsSlide11.getMappedResults();

		// for (Document dr : documentResultsSlide11) {
		// 	System.out.println("Day 28 Slide 11: " + dr);
		// }

		// ------------------------------------------------------------------------------------

		// Day 28 - Slide 15
		GroupOperation groupOperation = Aggregation.group("Rated")
												.count().as("countD") // "countD is the alias"
												.push("Title").as("titles"); // "titles is the alias"

		// SortOperation sortOperationSlide15 = Aggregation.sort(Sort.by(Direction.ASC, "countD"));								

		SortOperation sortOperationSlide15 = Aggregation.sort(
			Sort.by(
				Sort.Order.desc("countD"),  // Sort by "countD" field in descending order
				Sort.Order.asc("_id")       // Then sort by "_id" field in ascending order
			)
		);

		Aggregation pipelineSlide15 = Aggregation.newAggregation(
			groupOperation, 
			sortOperationSlide15);

		AggregationResults<Document> aggregationResultSlide15 = mongoTemplate.aggregate(
			pipelineSlide15, "movies", Document.class);

		List<Document> documentResultsSlide15 = aggregationResultSlide15.getMappedResults();

		// for (Document dr : documentResultsSlide15) {
		// 	System.out.println("Day 28 Slide 15: " + dr);
		// }

		// ------------------------------------------------------------------------------------

		// Day 28 - Slide 17, 18
		ProjectionOperation projectOperations17 = Aggregation
				.project("_id", "Title")
				.and("Plot").as("summary")
				.and("Awards").as("winning");
		
		SortOperation sortOperationSlide17 = Aggregation.sort(
			Sort.by(Direction.ASC, "Title"));

		Aggregation pipelineSlide17 = Aggregation.newAggregation(
			projectOperations17, 
			sortOperationSlide17);

		AggregationResults<Document> aggregationResultsSlide17 = mongoTemplate.aggregate(
			pipelineSlide17, "movies", Document.class);

		List<Document> documentResultsSlide17 = aggregationResultsSlide17.getMappedResults();

		// for (Document dr : documentResultsSlide17) {
		// 	System.out.println("Day 28 Slide 17: " + dr);
		// }

		// ------------------------------------------------------------------------------------

		// Day 28 - Slide 21 - Method 1
		// You cannot do the concatenation outside
		ProjectionOperation projectOperations21 = Aggregation.project("_id", "Title")
						.and("Plot").as("summary")
						.and("Awards").as("winning")
						.and(StringOperators.Concat.valueOf("Title")
								.concat(" (")
								.concatValueOf("Rated")
								.concat(")")
								.toString()).as("Title");
	
		SortOperation sortOperationSlide21 = Aggregation.sort(
			Sort.by(Direction.ASC, "Title"));

		Aggregation pipelineSlide21 = Aggregation.newAggregation(
			projectOperations21, 
			sortOperationSlide21);

		AggregationResults<Document> aggregationResultsSlide21 = mongoTemplate
			.aggregate(pipelineSlide21, "movies", Document.class);

		List<Document> documentResultsSlide21 = aggregationResultsSlide21.getMappedResults();

		// for (Document dr : documentResultsSlide21) {
		// 	System.out.println("Day 28 Slide 21: " + dr);
		// }

		// ------------------------------------------------------------------------------------

		// Day 28 - Slide 23 - Method 2
		ProjectionOperation projectOperations23 = Aggregation.project("_id", "Title")
						.and("Plot").as("summary")
						.and("Awards").as("winning")
						.and(
							AggregationExpression.from(MongoExpression.create("""
								$concat: ["$Title", " (", "$Rated", ")"]
							"""))
						).as("Title");

		SortOperation sortOperationSlide23 = Aggregation.sort(
			Sort.by(Direction.ASC, "Title"));

		Aggregation pipelineSlide23 = Aggregation.newAggregation(
			projectOperations23, 
			sortOperationSlide23);

		AggregationResults<Document> aggregationResultsSlide23 = mongoTemplate.aggregate(
			pipelineSlide23, "movies", Document.class);

		List<Document> documentResultsSlide23 = aggregationResultsSlide23.getMappedResults();

		// for (Document dr : documentResultsSlide23) {
		// 	System.out.println("Day 28 Slide 23: " + dr);
		// }

		// ------------------------------------------------------------------------------------

		// Day 28 -  Slide 27
		AggregationOperation aggregateOperationSlide27 = Aggregation.unwind("Genre");

		GroupOperation groupOperationSlide27 = Aggregation
			.group("Genre")
			.push("Title").as("Titles")
			.count().as("Titles_count");
		
		ProjectionOperation projectionOperationSlide27 = Aggregation
			.project("_id", "Titles", "Titles_count");

		SortOperation sortOperationSlide27 = Aggregation.sort(
			Sort.by(Direction.ASC, "Titles"));

		Aggregation pipelineSlide27 = Aggregation.newAggregation(
			aggregateOperationSlide27, 
			groupOperationSlide27,
			projectionOperationSlide27, 
			sortOperationSlide27);
		
		AggregationResults<Document> aggregationResultsSlide27 = mongoTemplate.aggregate(
			pipelineSlide27, "movies", Document.class);

		List<Document> documentResultsSlide27 = aggregationResultsSlide27.getMappedResults();

		// for (Document dr : documentResultsSlide27) {
		// 	System.out.println("Day 28 Slide 27: " + dr);
		// }

		// ------------------------------------------------------------------------------------

		// Day 28 -  Slide 30_1
		/* db.movies.aggregate([
				{
					$unwind: "$Genre" // Unwind the "Genre" array field
				},
				{
					$bucket: {
						groupBy: "$Genre", // Group by the "Genre" field
						boundaries: ["Action", "Comedy", "Drama", "Fantasy", "Thriller"], // Define bucket boundaries
						default: "ZOther", // Default bucket for values outside the specified boundaries
						output: {
							"titles_count": { $sum: 1 },  // Count number of titles in each bucket
							"titles": {
								$push: { $concat: ["$Title", " (", "$Rated", ")"] }
							}
						}
					}
				},
				{
					$unwind: "$titles" // Unwind the "Genre" array field
				},
				{
					$sort: { titles: 1 } // Sort buckets by titles_count in descending order
				}
			]);
		 */
		AggregationOperation aggregateOperationSlide30_1 = Aggregation.unwind("Genre");

		BucketOperation bucketCategory = Aggregation.bucket("Genre")
				.withBoundaries("Action", "Comedy", "Drama", "Fantasy", "Thriller")
				.withDefaultBucket("ZOther")
				.andOutputCount().as("Titles_count")
				.andOutput(AggregationExpression.from(MongoExpression.create("""
											$push: {$concat: ["$Title", " (", "$Rated", ")"]}
									"""))).as("Titles");
		
		AggregationOperation aggregateOperationSlide30_2 = Aggregation.unwind("Titles");

		SortOperation sortOperationSlide30 = Aggregation.sort(
			Sort.by(Direction.ASC, "Titles"));
		
		Aggregation pipelineSlide30 = Aggregation.newAggregation(
			aggregateOperationSlide30_1,
			bucketCategory,
			aggregateOperationSlide30_2,
			sortOperationSlide30);

		AggregationResults<Document> aggregationResultsSlide30 = mongoTemplate.aggregate(
			pipelineSlide30, "movies", Document.class);

		List<Document> documentResultsSlide30 = aggregationResultsSlide30.getMappedResults();

		// for (Document dr : documentResultsSlide30) {
		// 	System.out.println("Day 28 Slide 30: " + dr);
		// }

		// ------------------------------------------------------------------------------------

		UnwindOperation unwindOperationSlide31 = Aggregation.unwind("Genre");

		BucketOperation bucketOperationSlide31 = Aggregation.bucket("Genre")
				.withBoundaries("Adventure", "Biography", "Comedy", "Drama", "Fantasy", "Horror", "Sci-Fi")
				.withDefaultBucket("ZZZZZZ")
				.andOutputCount().sum(1).as("count")
				.andOutput(AggregationExpression.from(MongoExpression.create("""
						$push : { $concat : [ "$Title", " (", "$Rated", ")"] }"""))).as("titles");

		SortOperation sortOperationSlide31 = Aggregation.sort(
			Sort.by(Direction.DESC, "titles"));

		Aggregation pipelineSlide31 = Aggregation.newAggregation(
			unwindOperationSlide31, 
			bucketOperationSlide31, 
			sortOperationSlide31);

		AggregationResults<Document> resultSlide31 = mongoTemplate.aggregate(
			pipelineSlide31, "movies", Document.class);

		List<Document> documentSlide31 = resultSlide31.getMappedResults();

		// System.out.println("Day 28 slide 31: " + documentSlide31.toString());

		// ------------------------------------------------------------------------------------

		// Day 28 -  Slide 39
		MatchOperation matchOperationSlide39 = Aggregation
			.match(Criteria.where("Rated").is("PG"));

		LookupOperation lookupOperationSlide39 = Aggregation.lookup(
			"commentary", "movie_id", "_id", "Reviews");

		ProjectionOperation projectOperationSlide39 = Aggregation.project(
			"_id", "Title", "Year", "Rated", "Genre", "Reviews");

		SortOperation sortOperationSlide39 = Aggregation.sort(
			Sort.by(Direction.ASC, "Title"));

		Aggregation pipelineSlide39 = Aggregation.newAggregation(
			matchOperationSlide39, 
			lookupOperationSlide39,
			projectOperationSlide39, 
			sortOperationSlide39);

		AggregationResults<Document> aggregationResultSlide39 = mongoTemplate.aggregate(
			pipelineSlide39, "movies", Document.class);

		List<Document> documentResultSlide39 = aggregationResultSlide39.getMappedResults();

		// System.out.println("Day 28 slide 31: " + documentResultSlide39.toString());

		// ------------------------------------------------------------------------------------

		/*
		db.movies.aggregate([{
			$project: {
				_id: 1,
				title: { $concat: ["$Title", " - ", "$Rated"] },
				Released: 1,
				year: {
					$convert: {
						input: "$Year",
						to: "int"
					}
				},
				response: {
					$convert: {
						input: "$Response",
						to: "bool"
					}
				},
				date: ISODate()
			}
		},
		{
			$sort: { title: 1}
		}])
		*/
		// ProjectionOperation projectOperation = Aggregation.project()
        //         .and("_id").as("_id")
        //         .andExpression("concat(Title, ' - ', Rated)").as("title")
        //         .and("Released").as("Released")
        //         .andExpression("{ $convert: { input: '$Year', to: 'int' } }").as("year")
        //         .andExpression("{ $convert: { input: '$Response', to: 'bool' } }").as("response")
        //         .andExpression("new Date()").as("date"); //check again...

        // AggregationOperation sortOperation = Aggregation.sort(Sort.by("title").ascending());

        // Aggregation pipelineSlide48 = Aggregation.newAggregation(
		// 	projectOperation, 
		// 	sortOperation);

		// AggregationResults<Document> aggregationResultSlide48 = mongoTemplate.aggregate(
		// 		pipelineSlide48, "movies", Document.class);

        // // Execute aggregation
        // List<Document> documentResultSlide48 = aggregationResultSlide48.getMappedResults();

		// System.out.println("Day 28 slide 48: " + documentResultSlide48.toString());


		// ------------------------------------------------------------------------------------

		/*
		db.movies.aggregate([{
			$project: {
				_id: 1,
				title: { $concat: ["$Title", " - ", "$Rated"] },
				Released: 1,
				Screened: {
					$dateFromString: {
						dateString: "$Released",
						format: "%d %b %Y",
						onError: ""
					}
				},
				year: {
					$convert: {
						input: "$Year",
						to: "int"
					}
				},
				response: {
					$convert: {
						input: "$Response",
						to: "bool"
					}
				},
				date: ISODate()
			}
		},
		{
			$sort: { title: 1}
		}])
		 */
		// AggregationOperation projectOperationSlide49 = Aggregation.project()
		// 		.and("_id").as("_id")
		// 		.andExpression("concat(Title, ' - ', Rated)").as("title")
		// 		.and("Released").as("Released")
		// 		.and(ConvertOperators.DateFromString.fromStringOf("$Released")
		// 				.withFormat("%d %b %Y")
		// 				.onError("")
		// 		).as("Screened")
		// 		.and(ConvertOperators.ToInt.toValueOf("$Year")).as("year")
		// 		.and(ConvertOperators.ToBool.toValueOf("$Response")).as("response")
		// 		.and(Date.from(Instant.now())).as("date");

		// AggregationOperation sortOperationSlide49 = Aggregation.sort(Sort.by("title").ascending());

		// Aggregation pipelineSlide49 = Aggregation.newAggregation(projectOperationSlide49, sortOperationSlide49);

	}
}
