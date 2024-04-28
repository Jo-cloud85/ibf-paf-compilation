package ibf.paf3.day24workshop2.repository;

public interface Queries {
    public static final String SQL_INSERT_ORDER =
        """
        insert into orders(order_date, customer_name, ship_address, notes, tax) 
        values (?, ?, ?, ?, ?)
        """;
    
    public static final String SQL_INSERT_ORDER_DETAILS =
        """
        insert into order_details(product, unit_price, discount, quantity, order_id) 
        values (?, ?, ?, ?, ?)
        """;
    
    public static final String SQL_GET_ID = 
        """
        SELECT LAST_INSERT_ID()
        """;
}
