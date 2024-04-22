package ibf.paf3.day22workshop.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf.paf3.day22workshop.model.RSVP;

@Repository
public class RSVPRepository implements RSVPQueries {

    @Autowired
    private JdbcTemplate template;

    @SuppressWarnings("null")
    public int count(){
        int rs = template.queryForObject(COUNT_RSVP, Integer.class);
        return rs;
    }

    public List<RSVP> findAll(){
        List<RSVP> result =new LinkedList<RSVP>();
        result = template.query(ALL_RSVP, 
                BeanPropertyRowMapper.newInstance(RSVP.class));
        return result;
    }

    /* In this case, since you want to return all the fields of RSVP i.e. you are not customizing what you 
    want to return, you can also use BeanPropertyRowMapper. Either Method 1 or 2 is fine. */

    // Method 1
    public List<RSVP> findRSVPbyName(String name) {
        List<RSVP> result = new LinkedList<RSVP>();
        result = template.query(
                GET_RSVP_BY_NAME, 
                BeanPropertyRowMapper.newInstance(RSVP.class),
                "%" + name + "%");
        return result;
    }

    // Method 2
    // public List<RSVP> findRSVPbyName (String name){
    //     List<RSVP> result =new LinkedList<RSVP>();

    //     final SqlRowSet rs = template.queryForRowSet(GET_RSVP_BY_NAME, "%" + name + "%");
    //     while(rs.next()){
    //         RSVP r = new RSVP();
    //         r.setId(rs.getInt("id"));
    //         r.setFullName(rs.getString("full_name"));
    //         r.setPhone(rs.getString("phone"));
    //         r.setEmail(rs.getString("email"));
    //         r.setComment(rs.getString("comment"));
    //         Date ldt = (Date) rs.getObject("confirmation_date");
    //         r.setConfirmationDate(ldt);
    //         result.add(r);
    //     }
        
    //     return result;
    // }

    public Boolean saveRSVP(RSVP rsvp){
        int insertResult  = 0;
        insertResult = template.update(INSERT_RSVP, 
                            rsvp.getFullName(), 
                            rsvp.getEmail(), 
                            rsvp.getPhone(), 
                            rsvp.getConfirmationDate(),
                            rsvp.getComment());
        return insertResult > 0 ? true : false;
    }

    public Boolean updateRSVP(RSVP rsvp){
        int insertResult  = 0;
        insertResult = template.update(UPDATE_RSVP, 
                            rsvp.getPhone(), 
                            rsvp.getConfirmationDate(),
                            rsvp.getComment(),
                            rsvp.getEmail());
        return insertResult > 0 ? true : false;
    }

    public boolean isRSVPExist(String email){
        boolean isRSVPExist = false;
        final SqlRowSet rs = template.queryForRowSet(COUNT_RSVP_BY_EMAIL, email);
        if(rs.next()){
            int x = rs.getInt("count");
            if(x > 0){
                isRSVPExist = true;
            }
        }                 
        return isRSVPExist;
    }
}
