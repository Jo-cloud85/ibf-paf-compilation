package ibf.paf3.day262728workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ibf.paf3.day262728workshop.model.Review;
import ibf.paf3.day262728workshop.exception.NoMatchFoundException;
import ibf.paf3.day262728workshop.model.EditedReview;
import ibf.paf3.day262728workshop.service.ReviewService;

@Controller
@RequestMapping
public class ReviewController {
    
    @Autowired
    private ReviewService reviewSvc;

    // ======================================================================================================
    // Day 26
    // ====================================================================================================== 

    // You are trying to get the parameters from the URL directly so you cannot use @RequestBody since this is 
    // not a PostMapping i.e. no payload and you also cannot use MultiValueMap (there is no HTML form)

    // Day 26 (a) - Search boardgames by name
    // Example: http://localhost:8080/games?name=star
    @GetMapping("/games")
    public ResponseEntity<String> searchBoardgamesByName (
        @RequestParam(required = true, name = "name") String substrName,
        @RequestParam(defaultValue = "25") int limit,
        @RequestParam(defaultValue = "0") int offset) throws NoMatchFoundException{

        return new ResponseEntity<String>(
            reviewSvc.findGamesByNameWithPaginationInJson(substrName, limit, offset).toString(), 
            HttpStatus.OK
        );
    }

    // Day 26 (b) - Search boardgames by rank
    // Example: http://localhost:8080/games/rank?name=star
    @GetMapping("/games/rank")
    public ResponseEntity<String> searchGamesByNameWRank (
        @RequestParam(required = true, name = "name") String substrName,
        @RequestParam(defaultValue = "25") int limit,
        @RequestParam(defaultValue = "0") int offset) throws NoMatchFoundException{

        return new ResponseEntity<String>(
            reviewSvc.findGamesByNameWithPaginationAndRankInJson(substrName, limit, offset).toString(), 
            HttpStatus.OK
        );
    }

    // Day 26 (c) - Get details of game by game_id
    // Example: http://localhost:8080/game/65b32e132da1824ea35a7b6b
    @GetMapping("/game/{game_id}")
    public ResponseEntity<String> searchGameById(
        @PathVariable("game_id") String idStr) throws NoMatchFoundException {

        return new ResponseEntity<String>(
                reviewSvc.findGameByIdInJson(idStr).toString(), 
                HttpStatus.OK
            );
    }

    // ======================================================================================================
    // Day 27
    // ====================================================================================================== 

    // Day 27 (a)
    // Example: http://localhost:8080/review
    @PostMapping(path="/review", consumes="application/x-www-form-urlencoded") 
    public ResponseEntity<String> createReview(@ModelAttribute Review review) {
        // System.out.println(review);
        if (!reviewSvc.doesGameIdExist(review.getID())) {
            return new ResponseEntity<String>("Game ID does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(
            reviewSvc.createReview(review).toString(), 
            HttpStatus.OK);
    };

    // Day 27 (b)
    // A valid review_id will mean a review has been posted recently
    // Example: http://localhost:8080/review/66309570bd58d730ceb4191a
    @PutMapping(path="/review/{review_id}", consumes="application/json")
    public ResponseEntity<String> updateReview(
        @PathVariable("review_id") String reviewId, 
        @RequestBody EditedReview reviewPayload) {

        int rating = reviewPayload.getRating();
        String comment = reviewPayload.getC_text();

        if (!reviewSvc.doesReviewObjIdExist(reviewId)) {
            return new ResponseEntity<String>("Review ID does not exist", HttpStatus.NOT_FOUND);
        }

        if (reviewSvc.updateReview(reviewId, rating, comment) != 0) {
            return new ResponseEntity<String>("Updated review!", HttpStatus.OK);
        };

        return new ResponseEntity<String>("Update failed!", HttpStatus.BAD_REQUEST);
    }

    // Day 27 (c)
    // Example: http://localhost:8080/review/66309570bd58d730ceb4191a
    @GetMapping(path="/review/{review_id}")
    public ResponseEntity<String> getLatestReview(
        @PathVariable("review_id") String reviewId) {

        if (!reviewSvc.doesReviewObjIdExist(reviewId)) {
            return new ResponseEntity<String>("Review ID does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(reviewSvc.getLatestReviewInJson(reviewId).toString(), HttpStatus.OK);
    }

    // Day 27 (d)
    // Example: http://localhost:8080/review/66309570bd58d730ceb4191a/history
    @GetMapping(path="/review/{review_id}/history")
    public ResponseEntity<String> getFullReview(
        @PathVariable("review_id") String reviewId) {

        if (!reviewSvc.doesReviewObjIdExist(reviewId)) {
            return new ResponseEntity<String>("Review ID does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(reviewSvc.getFullReviewInJson(reviewId).toString(), HttpStatus.OK);
    }

    // ======================================================================================================
    // Day 28
    // ====================================================================================================== 

    // Day 28 (a)
    // Example: http://localhost:8080/game/14/reviews
    @GetMapping(path="/game/{game_id}/reviews")
    public ResponseEntity<String> getGameWithAllReviews(
        @PathVariable("game_id") int gameId){
        
        if (!reviewSvc.doesGameIdExist(gameId)) {
            return new ResponseEntity<String>("Game ID does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(reviewSvc.getGameWithReviewInJson(gameId).toString(), HttpStatus.OK);
    }

    // Day 28 (b)
    // Example: http://localhost:8080/games/highest
    @GetMapping(path="/games/highest")
    public ResponseEntity<String> getListOfGamesWithHighestRanking() {
        return new ResponseEntity<String>(
            reviewSvc.getListofGamesWithRatingInJson("highest").toString(), 
            HttpStatus.OK);  
    }

    // Example: http://localhost:8080/games/lowest
    @GetMapping(path="/games/lowest")
    public ResponseEntity<String> getListOfGamesWithLowestRanking() {
        return new ResponseEntity<String>(
            reviewSvc.getListofGamesWithRatingInJson("lowest").toString(), 
            HttpStatus.OK);  
    }
}