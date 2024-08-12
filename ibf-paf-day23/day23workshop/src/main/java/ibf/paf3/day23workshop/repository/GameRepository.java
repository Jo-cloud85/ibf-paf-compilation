package ibf.paf3.day23workshop.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf.paf3.day23workshop.model.Game;
import ibf.paf3.day23workshop.service.Queries;

@Repository
public class GameRepository implements Queries {
    
    @Autowired
    private JdbcTemplate template;

    public List<Game> findGamebyName (String gameName){
        List<Game> result =new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(QUERY_1, "%" + gameName + "%");
        while(rs.next()){
            Game r = new Game();
            r.setGameName(rs.getString("boardgame_name"));
            r.setGameReviewCount(rs.getInt("number_of_reviews"));
            String formattedNumber = "%.2f".formatted(rs.getDouble("average_rating"));
            r.setGameAvgRating(Double.parseDouble(formattedNumber));
            result.add(r);
        }
        return result;
    }
}
