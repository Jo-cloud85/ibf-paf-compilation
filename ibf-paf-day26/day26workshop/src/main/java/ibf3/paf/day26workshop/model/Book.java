package ibf3.paf.day26workshop.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;

import ibf3.paf.day26workshop.Constants;
import ibf3.paf.day26workshop.util.Utils;

public class Book {
    
    private String id;
    private int bookId;
    private String title;
    private String authors;
    private double averageRating;
    private String languageCode;
    private int numPages;
    private int ratingsCount;
    private int textReviewsCount;
    private Date publicationDate;
    private String publisher;

    public Book() {
    }

    public Book(String id, int bookId, String title, String authors, double averageRating, String languageCode,
            int numPages, int ratingsCount, int textReviewsCount, Date publicationDate, String publisher) {
        this.id = id;
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.averageRating = averageRating;
        this.languageCode = languageCode;
        this.numPages = numPages;
        this.ratingsCount = ratingsCount;
        this.textReviewsCount = textReviewsCount;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public int getTextReviewsCount() {
        return textReviewsCount;
    }

    public void setTextReviewsCount(int textReviewsCount) {
        this.textReviewsCount = textReviewsCount;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public static Book toBook(Document doc) {
		Book book = new Book();
		book.setId(doc.getObjectId(Constants.F_ID).toHexString());
		book.setBookId(doc.getInteger(Constants.F_BOOKID));
		book.setTitle(doc.getString(Constants.F_TITLE));
		book.setAuthors(doc.getString(Constants.F_AUTHORS));
		book.setAverageRating(Utils.readDouble(Constants.F_AVERAGE_RATING, doc, 0.0).floatValue());
		book.setLanguageCode(Utils.readString(Constants.F_LANGUAGE, doc, "eng"));
		book.setNumPages(Utils.readDouble(Constants.F_PAGES, doc, 0.0).intValue());
        book.setRatingsCount(doc.getInteger(Constants.F_RATINGS_COUNT));
        book.setTextReviewsCount(doc.getInteger(Constants.F_TEXT_REVIEWS_COUNT));
        String dateString = doc.getString("publication_date");
        DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
        try {
            Date date = dateFormat.parse(dateString);
            book.setPublicationDate(date);
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
        book.setPublisher(doc.getString("publisher")); 
		return book;
	}

    @Override
    public String toString() {
        return "Book [id=" + id + ", bookId=" + bookId + ", title=" + title + ", authors=" + authors
                + ", averageRating=" + averageRating + ", languageCode=" + languageCode + ", numPages=" + numPages
                + ", ratingsCount=" + ratingsCount + ", textReviewsCount=" + textReviewsCount + ", publicationDate="
                + publicationDate + ", publisher=" + publisher + "]";
    }
}
