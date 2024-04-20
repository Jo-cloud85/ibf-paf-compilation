package ibf.paf3.day23workshop.service;

public interface Queries {
    // Try to use java to round off values rather than through sql query manipulation

    /* This query will have issues when you upload to Railway because the new 5.7v of MySQL has this 
    ONLY_FULL_GROUP_BY sql mode by default that you cannot turn off in Railway. So if you use the 
    commented-out query, you will have bad grammar */

    // public final static String QUERY_1 = """
    //     SELECT game.name AS boardgame_name, game.users_rated AS number_of_reviews, AVG(comment.rating) AS average_rating
    //     FROM comment JOIN game ON comment.gid = game.gid 
    //     WHERE game.name LIKE ? 
    //     GROUP BY boardgame_name
    //     ORDER BY number_of_reviews
    //     """;

    public final static String QUERY_1 = """
        SELECT 
            game.name AS boardgame_name, 
            game.users_rated AS number_of_reviews, 
            AVG(comment.rating) AS average_rating
        FROM 
            comment JOIN game ON comment.gid = game.gid 
        WHERE 
            game.name LIKE ?
        GROUP BY 
            game.name, game.users_rated
        ORDER BY 
            game.users_rated;
    """;
}


