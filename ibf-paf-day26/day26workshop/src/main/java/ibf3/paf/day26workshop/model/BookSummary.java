package ibf3.paf.day26workshop.model;

import org.bson.Document;

import ibf3.paf.day26workshop.Constants;

public record BookSummary(String id, String title) { 

	public static BookSummary toBookSummary(Document doc) {
		return new BookSummary(
				doc.getObjectId(Constants.F_ID).toHexString(), 
				doc.getString(Constants.F_TITLE));
	}
}
