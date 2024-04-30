package ibf.paf3.day262728workshop.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class EditedReview {
    
    private String c_text; //to match comments collection column name
    @Min(value = 0)
    @Max(value = 10)
    private int rating;
    private LocalDateTime posted;
    
    public EditedReview() {
        this.posted = LocalDateTime.now();
    }

    public EditedReview(String c_text, @Min(0) @Max(10) int rating, LocalDateTime posted) {
        this.c_text = c_text;
        this.rating = rating;
        this.posted = posted;
    }

    public String getC_text() {
        return c_text;
    }

    public void setC_text(String c_text) {
        this.c_text = c_text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    @Override
    public String toString() {
        return "UpdatedReview [c_text=" + c_text + ", rating=" + rating + ", posted=" + posted + "]";
    }
}
