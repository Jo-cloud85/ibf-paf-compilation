package ibf.paf3.day23workshop.service;

public interface Queries {
    // Try to use java to round off values rather than through sql query manipulation
    public final static String QUERY_1 = """
        SELECT game.name AS boardgame_name, game.users_rated AS number_of_reviews, AVG(comment.rating) AS average_rating
        FROM comment JOIN game ON comment.gid = game.gid 
        WHERE game.name LIKE ? 
        GROUP BY game.ranking
        ORDER BY number_of_reviews
        """;
}
