package ibf.paf3.day23workshop.service;

public interface Queries {
    public final static String QUERY_1 = """
        SELECT game.name AS boardgame_name, game.users_rated AS number_of_reviews, ROUND(AVG(comment.rating),2) AS average_rating
        FROM comment JOIN game ON comment.gid = game.gid 
        WHERE game.name LIKE ? 
        GROUP BY game.ranking
        """;
}
