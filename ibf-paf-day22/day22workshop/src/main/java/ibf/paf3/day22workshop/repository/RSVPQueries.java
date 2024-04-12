package ibf.paf3.day22workshop.repository;

public interface RSVPQueries {
    public static final String COUNT_RSVP= """
            select count(*) from rsvp;
            """;

    public static final String COUNT_RSVP_BY_EMAIL= """
        select count(*) as count from rsvp where email= ?;
        """;

    
    public static final String ALL_RSVP = """
            select * from rsvp
            """;

    public static final String GET_RSVP_BY_NAME = """
        select * from rsvp where full_name like ?
        """;

    public static final String INSERT_RSVP = """
            insert into rsvp (full_name, email, phone, 
            confirmation_date, comment) values (?, ?, ?, ?, ?)
            """;
    
    public static final String UPDATE_RSVP = """
        update rsvp set phone=?, confirmation_date=?, comment=? where email = ?
        """;
}
