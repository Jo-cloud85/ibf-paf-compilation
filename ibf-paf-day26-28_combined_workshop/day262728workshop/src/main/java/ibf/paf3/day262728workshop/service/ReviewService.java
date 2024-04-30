package ibf.paf3.day262728workshop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf3.day262728workshop.exception.IdNotFoundException;
import ibf.paf3.day262728workshop.model.Review;
import ibf.paf3.day262728workshop.model.EditedReview;
import ibf.paf3.day262728workshop.model.Game;
import ibf.paf3.day262728workshop.model.GameSummary;
import ibf.paf3.day262728workshop.model.GameWithReview;
import ibf.paf3.day262728workshop.repo.ReviewRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepo;

    // ======================================================================================================
    // Day 26
    // ====================================================================================================== 

    public long getTotalGamesCount(String substrName) {
        return reviewRepo.getTotalGamesCount(substrName);
    }

    // Day 26 (a) - Finding Boardgames by name with pagination --------------------------------
    public List<GameSummary> findGamesByNameWithPagination(
        String substrName, int limit, int offset) {
        return reviewRepo.findGamesByNameWithPagination(substrName, limit, offset);
    }

    public JsonObject findGamesByNameWithPaginationInJson(
        String substrName, int limit, int offset) {
        List<GameSummary> list = findGamesByNameWithPagination(substrName, limit, offset);
        return convertGameSearchResultsToJson(list, substrName, limit, offset);
    }

    // Day 26 (b) - Finding games by name with pagination and rank ----------------------------
    public List<GameSummary> findGamesByNameWithPaginationAndRank(
        String substrName, int limit, int offset) { 
        return reviewRepo.findGamesByNameWithPaginationAndRank(substrName, limit, offset);
    }

    public JsonObject findGamesByNameWithPaginationAndRankInJson(
        String substrName, int limit, int offset) {
        List<GameSummary> listwRank = findGamesByNameWithPaginationAndRank(substrName, limit, offset);
        return convertGameSearchResultsToJson(listwRank, substrName, limit, offset);
    }

    public JsonObject convertGameSearchResultsToJson(
        List<GameSummary> list, String substrName, int limit, int offset) {
        List<GameSummary> boardgameSummaryList = list;

        // YOU CANNOT DIRECTLY create JsonArray from a List<BoardgameSummary>!! You have to iterate through.
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (GameSummary gameSumm : boardgameSummaryList) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("game_id", gameSumm.get_id().toString())
                    .add("name", gameSumm.getName());
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArr = jsonArrayBuilder.build();
        
        return Json.createObjectBuilder()
            .add("games", jsonArr)
            .add("offset", offset)
            .add("limit", limit)
            .add("total", getTotalGamesCount(substrName))
            .add("timestamp", LocalDateTime.now().toString())
            .build();
    }

    // Day 26 (c) - Finding game by Id --------------------------------------------------
    public Game findGameById(String idStr) {
        try {
            ObjectId objId = new ObjectId(idStr);
            return reviewRepo.findGameById(objId);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid ObjectId: " + idStr);
            e.printStackTrace();
            return null;
        }
    }

    public JsonObject findGameByIdInJson(String idStr) {
        return gameToJson(findGameById(idStr));
    }

    public JsonObject gameToJson(Game game) {
        return Json.createObjectBuilder()
            .add("game_id", game.get_id().toString())
            .add("name", game.getName())
            .add("year", game.getYear())
            .add("ranking", game.getRanking())
            .add("users_rated", game.getUsersRated())
            .add("url", game.getUrl())
            .add("thumbnail", game.getImage())
            .add("timestamp", game.getTimestamp().toString())
            .build();
    }


    // ======================================================================================================
    // Day 27
    // ====================================================================================================== 

    public boolean doesGameIdExist(int gameId) {
        return reviewRepo.doesGameIdExist(gameId);
    }

    // Day 27 (a) --------------------------
    public Review createReview(Review review) {
        return reviewRepo.createReview(review);
    }

    // Day 27 (b) --------------------------
    public boolean doesReviewObjIdExist(String objIdStr) {
        try {
            ObjectId objId = new ObjectId(objIdStr);
            return reviewRepo.doesReviewObjIdExist(objId);
        } catch (IdNotFoundException e) {
            System.err.println("Invalid ObjectId: " + objIdStr);
            e.printStackTrace();
        }
        return false;
    }

    public long updateReview(String reviewObjIdStr, Integer rating, String comment) {
        ObjectId objId = new ObjectId(reviewObjIdStr);
        return reviewRepo.updateReview(objId, rating, comment);
    }

    // Day 27 (c) --------------------------
    public Review getReview(String reviewObjIdStr) {
        ObjectId objId = new ObjectId(reviewObjIdStr);
        return reviewRepo.getReview(objId);
    }

    public JsonObject getLatestReviewInJson(String reviewObjIdStr) {
        Review latestReview = getReview(reviewObjIdStr);

        boolean isEdited = false;
        if (latestReview.getEdited().size() != 0) {
            isEdited =true;
        } 
        
        return Json.createObjectBuilder()
                    .add("user", latestReview.getUser())
                    .add("rating", latestReview.getRating())
                    .add("comment", latestReview.getC_text())
                    .add("ID", latestReview.getID())
                    .add("posted", latestReview.getPosted().toString())
                    .add("name", latestReview.getName())
                    .add("edited", isEdited)
                    .add("timestamp", LocalDateTime.now().toString())
                    .build();
    }

    // Day 27 (d) --------------------------
    public JsonObject getFullReviewInJson(String reviewObjIdStr) {
        Review latestReview = getReview(reviewObjIdStr);

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (EditedReview updatedReview : latestReview.getEdited()) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("comment", updatedReview.getC_text())
                    .add("rating", updatedReview.getRating())
                    .add("posted", updatedReview.getPosted().toString());
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }
        JsonArray jsonArr = jsonArrayBuilder.build();
        
        return Json.createObjectBuilder()
                    .add("user", latestReview.getUser())
                    .add("rating", latestReview.getRating())
                    .add("comment", latestReview.getC_text())
                    .add("ID", latestReview.getID())
                    .add("posted", latestReview.getPosted().toString())
                    .add("name", latestReview.getName())
                    .add("edited", jsonArr)
                    .add("timestamp", LocalDateTime.now().toString())
                    .build();
    }

    // ======================================================================================================
    // Day 28
    // ====================================================================================================== 

    // Day 28 (a) --------------------------
    public GameWithReview getGameWithReview(int gameId) {
        return reviewRepo.getGameWithReviews(gameId);
    }

    public JsonObject getGameWithReviewInJson(int gameId) {
        GameWithReview gameWithReview = reviewRepo.getGameWithReviews(gameId);

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Review review : gameWithReview.getReviews()) {
            jsonArrayBuilder.add("/review/" + review.get_id());
        }
        JsonArray jsonArr = jsonArrayBuilder.build();
        
        return Json.createObjectBuilder()
                    .add("game_id", gameWithReview.getGid())
                    .add("name", gameWithReview.getName())
                    .add("year", gameWithReview.getYear())
                    .add("rank", gameWithReview.getRanking())
                    .add("users_rated", gameWithReview.getUsersRated())
                    .add("url", gameWithReview.getUrl())
                    .add("thumbnail", gameWithReview.getImage())
                    .add("reviews", jsonArr)
                    .add("timestamp", LocalDateTime.now().toString())
                    .build();
    }

    // Day 28 (b) --------------------------
    public List<Document> getListofGamesWithRating(String highestOrlowest) {
        return reviewRepo.getListofGamesWithRating(highestOrlowest);
    }

    public JsonObject getListofGamesWithRatingInJson(String highestOrlowest) {
        List<Document> listOfDocument = reviewRepo.getListofGamesWithRating(highestOrlowest);

        JsonArrayBuilder jsonArrBuilder = Json.createArrayBuilder();

        for (Document doc : listOfDocument) {
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                    .add("_id", doc.get("_id").toString())
                    .add("name", doc.get("name").toString())
                    .add("rating", doc.get("rating").toString())
                    .add("user", doc.get("user").toString())
                    .add("comment", doc.get("comment").toString())
                    .add("review_id", doc.get("review_id").toString());
                jsonArrBuilder.add(jsonObjectBuilder);
        }

        JsonArray jsonArr = jsonArrBuilder.build();

        JsonObject jsonObject = Json.createObjectBuilder()
            .add("rating", highestOrlowest)
            .add("games", jsonArr)
            .add("timestamp", LocalDateTime.now().toString())
            .build();
        return jsonObject;
    }
}
