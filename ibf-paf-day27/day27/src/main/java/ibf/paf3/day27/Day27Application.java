package ibf.paf3.day27;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.attoparser.ParseException;
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
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.DateOperators.DateFromString;
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

import ibf.paf3.day27.model.Instock;
import ibf.paf3.day27.model.Item;
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

		// ------------------------------------------------------------------------------------
		// Day 27
		// ------------------------------------------------------------------------------------

		// Person newPerson01 = new Person("Alan", 25, "M", 
		// 		Arrays.asList("Coding", "Drinking", "Snorkeling"));
		// personRepo.savePerson(newPerson01);

		// Person newPerson02 = new Person("Felicia", 25, "F", 
		// 		Arrays.asList("Skateboarding", "Cycling", "Snorkeling"));
		// personRepo.savePerson(newPerson02);

		// Person newPerson03 = new Person("Charan", 25, "M", 
		// 		Arrays.asList("Coding", "Cycling", "Snorkeling"));
		// personRepo.savePerson(newPerson03);

		// ObjectId objKey = new Object("xxxxxxxxx");

		// See PersonService.java for refactorization -------

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 12
		/*
			db.persons.update(
				{ _id: ObjectId("65a915ff49d6872622906330")},
				{ $set: {name: "Nicole Ng", age: 30, gender: "F", hobbies: ["Reading", "Swimming"]}},
				{ upsert: true}
			)
		*/

		// Query query = new Query(Criteria.where("name").is("Nicole Ng"));

		// Person updatedPerson = new Person();
		// updatedPerson.setName("Nicole Ng Junior");
		// updatedPerson.setAge(28);
		// updatedPerson.setGender("F");
		// updatedPerson.setHobbies(Arrays.asList("Knitting", "Skating"));

        	// Update updateOps = new Update()
		// 		.set("name", updatedPerson.getName())
	        //     	.set("age", updatedPerson.getAge())
		// 		.push("hobbies")
		// 		.each(updatedPerson.getHobbies());
		
		// UpdateResult updateResult = mongoTemplate.upsert(query, updateOps, "persons");

		// System.out.println("Slide 13 results: " + updateResult.getModifiedCount());
		// System.out.println("Slide 13 results: " + result);

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 16
		/*
			db.persons.find(
				{ age: { $gte: 30 }},
				{ _id: 1, name: 1, age: 1, gender: 1}
			);
		 */
		Query query = new Query();
		query.addCriteria(Criteria.where("age").gte(30));
		query.fields()
		     .include("_id", "name", "age", "gender");

		List<Person> resultsSlide16 = mongoTemplate.find(query, Person.class);
		// System.out.printf("Slide 16: %s\n", resultsSlide16.toString());

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 18
		// Before you run this, you must make sure you have already created a text index in MongoDB

		/*
			db.persons.createIndex(
				{ name: "text" }
			);

			db.persons.find(
				{ $text: { $search: "Felicia" } }
			)
		 */
		TextCriteria textCriteria = TextCriteria
				.forDefaultLanguage()
				.matchingPhrase("Felicia");

		TextQuery textQuery = TextQuery.queryText(textCriteria);
		
		List<Document> results = mongoTemplate.find(textQuery, Document.class, "persons");

		// System.out.printf("Slide 18: %s\n", results.toString());

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 21

		// "ng": Matches substrings and partial words.
		// \"ng\": Matches only the exact phrase as a standalone word.
		/*
			db.persons.find(
				{ $text: { $search: "\"ng\"" } }, // Search for the phrase "ng"
				{ score: { $meta: "textScore" } } // Include the text score
			)
			.sort({ score: { $meta: "textScore" } }) // Sort by text score
		*/
		TextCriteria textCriteriaSlide21 = TextCriteria
				.forDefaultLanguage()
				.matchingPhrase("ng") // must be a full word, cannot be substring
				.caseSensitive(false);

		TextQuery textQuerySlide21 = TextQuery.queryText(textCriteriaSlide21)
			.sortByScore()
			.includeScore("score");
		
		List<Document> resultsSlide21 = mongoTemplate.find(textQuerySlide21, Document.class, "persons");
		// System.out.printf("Slide 21: %s\n", resultsSlide21.toString());

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 24
		/*
			db.inventory.insert({
				item: "game console",
				instock: [
					{warehouse: "Ang Mo Kio", qty: 25 },
					{warehouse: "Toa Payoh", qty: 50}
				]
			});
		*/
		// Item item = new Item();
		// item.setItem("game console");
		// Instock instock1 = new Instock("Seletar", 25);
		// Instock instock2 = new Instock("Serangoon", 10);
		// item.setInstock(List.of(instock1, instock2));

		// Item resultSlide24 = mongoTemplate.insert(item, "inventory");
		// System.out.printf("Slide 24: %s\n", resultSlide24);

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 26
		/*
			db.inventory.find(
				{ "instock.warehouse" : "Ang Mo Kio"}
			);
		 */
		Query querySlide26 = new Query(Criteria
			.where("instock.warehouse").is("Ang Mo Kio"));
		List<Document> resultSlide26 = mongoTemplate.find(querySlide26, Document.class, "inventory");
		//System.out.printf("Slide 26: %s\n", resultSlide26);

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 28 Part 1
		/*
			db.inventory.find(
				{
					$and: [
						{ "instock.warehouse": "Ang Mo Kio"},
						{ "instock.qty" : { $gt: 50 }}
					]
				}
			);
		 */
		// Method 1 ----
		// Query querySlide28_I = new Query(Criteria
		// 	.where("instock.warehouse").is("Ang Mo Kio")
		// 	.and("instock.qty").gt(50));

		// Method 2 ----
		Criteria criteria = new Criteria()
			.andOperator(Criteria.where("instock.warehouse").is("Ang Mo Kio"),
						 Criteria.where("instock.qty").gt(50));
	
		Query querySlide28_I = new Query(criteria);
		List<Document> resultSlide27_I = mongoTemplate.find(querySlide28_I, Document.class, "inventory");
		// System.out.printf("Slide 27_I: %s\n", resultSlide27_I);

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 28 Part 2
		/*
			db.inventory.find(
				{
					instock: {
						$elemMatch: {
							warehouse: "Ang Mo Kio",
							qty: { $gte: 20}
						4}
					}
				}
			)
		 */
		Criteria criteriaSlide28_2 = Criteria
			.where("instock").elemMatch(
				Criteria.where("warehouse").is("Ang Mo Kio")
                        .and("qty").gte(20));

        	Query querySlide28_2 = new Query(criteriaSlide28_2);
		List<Document> resultSlide28_2 = mongoTemplate.find(querySlide28_2, Document.class, "inventory");
		// System.out.printf("Slide 28_2: %s\n", resultSlide28_2);

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 29
		/*
			db.inventory.update(
				{ "instock.warehouse": "Serangoon"},
				{
					$inc: {
						"instock.$[].qty": 100
					}
				}
			);
		 */
		// Criteria criteriaSlide29 = Criteria.where("instock.warehouse").is("Serangoon");
	        // Update updateSlide29 = new Update().inc("instock.$[].qty", 100);
	        // UpdateResult resultSlide29 = mongoTemplate.updateMulti(
		// 	Query.query(criteriaSlide29), updateSlide29, Document.class, "inventory");
		// System.out.printf("Slide 29: %s\n", resultSlide29);

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 30
		/*
			db.inventory.update(
				{ _id: ObjectId("65a8c9795805bd8556d3483a")},
				{ $push: { instock: {warehouse: "Ubi", qty: 100} } }
			)
		 */
		// Query querySlide30 = Query.query(Criteria.where("_id").is("6627ae4457f6b42be327d122"));
	        // Update updateSlide30 = new Update().push("instock", new Instock("Ubi", 90));
	        // UpdateResult resultSlide30 = mongoTemplate.updateFirst(
		// 		querySlide30, updateSlide30, Document.class, "inventory");
		// System.out.printf("Slide 30: %s\n", resultSlide30);

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 30_2
		/*
			db.inventory.update(
				{ _id: ObjectId("65a8c9795805bd8556d3483a")},
				{ $pop: { instock: 1 } }
			)
		 */
		// Query querySlide30_2 = Query.query(Criteria.where("_id").is("6627ae4457f6b42be327d122"));
	        // Update updateSlide30_2 = new Update().pop("instock", Update.Position.LAST);
	        // UpdateResult resultSlide30_2 = mongoTemplate.updateFirst(
		// 		querySlide30_2, updateSlide30_2, Document.class, "inventory");
		// System.out.printf("Slide 30: %s\n", resultSlide30_2);

		// ------------------------------------------------------------------------------------
		// Day 27 - Slide 31
		/*
			db.inventory.updateMany(
				{ "instock.warehouse": "Ang Mo Kio" },
				{ $set: { "instock.$[elem].qty": 0 } }, 
				{
					arrayFilters: [
						{ "elem.qty": { $lte: -1}}
					]
				}
			);
		 */
		// Basically if there is qty of value less than 1, set it to 0
		// Criteria criteriaSlide31 = Criteria
		// 		.where("instock.warehouse").is("Ang Mo Kio")
	        //      .and("instock.qty").lte(-1);
	        // Query querySlide31 = new Query(criteriaSlide31);
	        // Update updateSlide31 = new Update().set("instock.$.qty", 0);
	        // UpdateResult resultSlide31 = mongoTemplate.updateMulti(
		// 		querySlide31, updateSlide31, Document.class, "inventory");
		// System.out.printf("Slide 31: %s\n", resultSlide31);


		// ======================================================================================
		// Day 28
		// ======================================================================================

		// Day 28 - Slide 9
		/*
			db.movies.aggregate([
				{ $match : { Rated: "PG" }}
			]);
		 */
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
		/*
			db.movies.aggregate([
				{ $match : { Rated: "PG" }},
				{ $project: { _id: 0, Title: 1, Year: 1, Rated: 1, Released: 1 }}
			]);
		 */
		ProjectionOperation projectOperations = Aggregation
			.project("Title", "Year", "Rated", "Released")
			.andExclude("_id");

		Aggregation pipelineSlide11 = Aggregation.newAggregation(
			matchOperation, // matchOperation from Slide 9
			projectOperations);

		AggregationResults<Document> aggregationResultsSlide11 = mongoTemplate.aggregate(
			pipelineSlide11, "movies", Document.class);

		List<Document> documentResultsSlide11 = aggregationResultsSlide11.getMappedResults();

		// for (Document dr : documentResultsSlide11) {
		// 	System.out.println("Day 28 Slide 11: " + dr);
		// }

		// ------------------------------------------------------------------------------------
		// Day 28 - Slide 15
		/*
			db.movies.aggregate([
				{
					$group: {
						_id: "$Rated",
						count: { $sum: 1},
						titles: { $push: "$Title"}    
					}
				},
				{ $sort: { count: -1, _id: -1} }
			]);
		 */
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
		/*
			db.movies.aggregate([
				{ $project: { _id: 1, Title: 1, summary: "$Plot", winning: "$Awards"} },
				{ $sort: { Title: 1, summary: 1} }
			]);
		 */
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
		/*
			db.movies.aggregate([
				{
					$project: { 
						_id: 1, 
						Title: {
							$concat : [ "$Title", "(", "$Rated", ")" ]
						}, 
						summary: "$Plot", 
						winning: "$Awards"}
				},
				{
					$sort: { Title: 1}
				}
			]);
		 */
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
		// Day 28 - Slide 23 - Method 2 - Native mongo query in MongoExpression
		ProjectionOperation projectOperations23 = Aggregation.project("_id", "Title")
						.and("Plot").as("summary")
						.and("Awards").as("winning")
						.and(AggregationExpression.from(MongoExpression.create("""
								$concat: ["$Title", " (", "$Rated", ")"]"""))).as("Title");

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
		/*
			db.movies.aggregate([
				{ $unwind: "$Genre" },
				{
					$group: {
						_id: "$Genre",
						titles: { $push: "$Title" },
						titles_count: { $sum : 1 }
					}
				},
				{ $sort: { titles_count: -1 } }, 
				{ $out: { db: "test", coll: "categories" } }
			]);
		 */
		UnwindOperation unwindOperationSlide27 = Aggregation.unwind("Genre");

		GroupOperation groupOperationSlide27 = Aggregation
			.group("Genre")
			.push("Title").as("Titles")
			.count().as("Titles_count");
		
		ProjectionOperation projectionOperationSlide27 = Aggregation
			.project("_id", "Titles", "Titles_count");

		SortOperation sortOperationSlide27 = Aggregation.sort(Sort.by(Direction.ASC, "Titles"));

		Aggregation pipelineSlide27 = Aggregation.newAggregation(
			unwindOperationSlide27, 
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

		// Day 28 -  Slide 30_1 - Native mongo query in MongoExpression
		/* 
			db.movies.aggregate([
				{ $unwind: "$Genre" },
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
				{ $unwind: "$titles" },
				{ $sort: { titles: 1 } } // Sort buckets by titles_count in descending order
			]);
		 */
		UnwindOperation unwindOperationSlide30_1 = Aggregation.unwind("Genre");

		BucketOperation bucketCategory = Aggregation.bucket("Genre")
				.withBoundaries("Action", "Comedy", "Drama", "Fantasy", "Thriller")
				.withDefaultBucket("ZOther")
				.andOutputCount().as("Titles_count")
				.andOutput(AggregationExpression.from(MongoExpression.create("""
						$push: {$concat: ["$Title", " (", "$Rated", ")"]}"""))).as("Titles");
		
		UnwindOperation unwindOperationSlide30_2 = Aggregation.unwind("Titles");

		SortOperation sortOperationSlide30 = Aggregation.sort(Sort.by(Direction.ASC, "Titles"));
		
		Aggregation pipelineSlide30 = Aggregation.newAggregation(
			unwindOperationSlide30_1,
			bucketCategory,
			unwindOperationSlide30_2,
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
		/*
			db.movies.aggregate([
				{ $match: { Rated: "PG" } },
				{
					$lookup: {
						from: "commentary",
						foreignField: "movie_id",
						localField: "_id",
						as: "Reviews"
					}
				},
				{
					$project: { _id: 1, Title: 1, Year: 1, Rated: 1, Genre: 1, Reviews: 1 }
				},
				{ $unwind: "$Reviews" },
				{ $sort: {Title: 1} }
			]);
		 */
		MatchOperation matchOperationSlide39 = Aggregation
			.match(Criteria.where("Rated").is("PG"));

		LookupOperation lookupOperationSlide39 = Aggregation.lookup(
			"commentary", "_id", "movie_id", "Reviews");

		ProjectionOperation projectOperationSlide39 = Aggregation.project(
			"_id", "Title", "Year", "Rated", "Genre", "Reviews");

		SortOperation sortOperationSlide39 = Aggregation.sort(Sort.by(Direction.ASC, "Title"));

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
					date: ISODate() //when adding this in SB, you can only append at the doc part.
				}
			},
			{
				$sort: { title: 1}
			}])
		*/
		ProjectionOperation projectOperationSlide48 = Aggregation.project()
                .and("_id").as("_id")
                .andExpression("concat(Title, ' - ', Rated)").as("title")
                .and("Released").as("Released")
                .andExpression("{ $convert: { input: '$Year', to: 'int' } }").as("year")
                .andExpression("{ $convert: { input: '$Response', to: 'bool' } }").as("response");

	        SortOperation sortOperationSlide48 = Aggregation.sort(Sort.by(Direction.ASC, "title"));
	
	        Aggregation pipelineSlide48 = Aggregation.newAggregation(
				projectOperationSlide48, 
				sortOperationSlide48);
	
			AggregationResults<Document> aggregationResultSlide48 = mongoTemplate.aggregate(
					pipelineSlide48, "movies", Document.class);
	
	        List<Document> documentResultSlide48 = aggregationResultSlide48.getMappedResults();
		for (Document doc : documentResultSlide48) {
			doc.append("date", LocalDateTime.now());
		}

		// System.out.println("Day 28 slide 48: " + documentResultSlide48.toString());


		// ------------------------------------------------------------------------------------
		/*
			db.movies.aggregate([
				{
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
				{ $sort: { title: 1} }
			])
		 */
		ProjectionOperation projectOperationSlide49 = Aggregation.project()
				.and("_id").as("_id")
				.andExpression("concat(Title, ' - ', Rated)").as("title")
				.and("Released").as("Released")
				.and(AggregationExpression.from(MongoExpression.create("""
					$dateFromString: {
						dateString: "$Released",
						format: "%d %b %Y",
						onError: ""
					}
				"""))).as("Screened")
				.andExpression("{ $convert: { input: '$Year', to: 'int' } }").as("year")
                .andExpression("{ $convert: { input: '$Response', to: 'bool' } }").as("response");

		SortOperation sortOperationSlide49 = Aggregation.sort(Sort.by("title").ascending());

		Aggregation pipelineSlide49 = Aggregation.newAggregation(projectOperationSlide49, sortOperationSlide49);

		AggregationResults<Document> aggregationResultSlide49 = mongoTemplate.aggregate(
				pipelineSlide49, "movies", Document.class);

		List<Document> documentResultSlide49 = aggregationResultSlide49.getMappedResults();
		for (Document doc : documentResultSlide49) {
			doc.append("date", LocalDateTime.now());
		}

		// System.out.println("Day 28 slide 49: " + documentResultSlide49.toString());


		// ======================================================================================
		// Day 29 - Chuk's listings exercise (make sure listings collection is inside the 'test' database in local when you run)
		// ======================================================================================

		// ------------------------------------------------------------------------------------
		// Task 3
		/*
			db.listings.aggregate([
				{ $project: {_id: 0, reviews: 1}},
				{ $unwind: "$reviews"},
				{ 
					$project: {
						_id: "$reviews._id",
						date: "$reviews.date",
						listing_id: "$reviews.listing_id",
						reviewer_name: { $replaceAll: 
							{ input: "$reviews.reviewer_name", find: ",", replacement: "" }},
						comments: { $replaceAll: 
							{ input: "$reviews.comments", find: "\n", replacement: "" }}
					}
				},
				{
					$project: {
						_id: 1,
						date: 1,
						listing_id: 1,
						reviewer_name: 1,
						comments: { $replaceAll: 
							{ input: "$comments", find: "\r", replacement: "" }}
					}
				},
				// { $out: "reviews" }
			])
		 */
		ProjectionOperation projectOperation1 = Aggregation.project().andExclude("_id").andInclude("reviews");
		UnwindOperation unwindOperation1 = Aggregation.unwind("reviews");
		ProjectionOperation projectOperation2 = Aggregation.project()
			.and("reviews._id").as("_id")
			.and("reviews.date").as("date")
			.and("reviews.listing_id").as("listing_id")
			.andExpression("replaceAll(reviews.reviewer_name, ',', '')").as("reviewer_name")
			.andExpression("replaceAll(reviews.comments, '\n', '')").as("comments");
		ProjectionOperation projectOperation3 = Aggregation.project()
			.andInclude("_id", "date", "listing_id", "reviewer_name")
			.andExpression("replaceAll(comments, '\r', '')").as("comments");

		Aggregation pipelineTask3 = Aggregation.newAggregation(
			projectOperation1, 
			unwindOperation1, 
			projectOperation2, 
			projectOperation3);
		AggregationResults<Document> aggregationResultsTask3 = mongoTemplate.aggregate(
				pipelineTask3, "listings", Document.class);
		
		List<Document> documentResultTask3 = aggregationResultsTask3.getMappedResults();
		
		// System.out.println("Day 29 Task 3: " + documentResultTask3.toString());

		// ------------------------------------------------------------------------------------
		// Task 4
		/*
			db.listings.updateMany(
				{}, 
				{"$unset": {"reviews": 1}}
			)
		 */
		// Query queryTask4 = new Query();
		// Update updateTask4 = new Update().unset("reviews");
		// UpdateResult updateResultTask4 = mongoTemplate.updateMulti(queryTask4, updateTask4, "listings");
		// System.out.printf("Task 4: %s\n", updateResultTask3);
	}
}
