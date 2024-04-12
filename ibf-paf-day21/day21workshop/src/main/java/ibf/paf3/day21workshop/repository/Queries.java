package ibf.paf3.day21workshop.repository;

/* Technically, you can still use class instead of interface but since we are not introducing
 * any methods i.e. all our queries so far are static, interface is better. */

public interface Queries {
    public static final String GET_ALL_CUSTOMERS = "SELECT id, first_name, last_name FROM customers";

    public static final String GET_ALL_CUSTOMERS_WITH_PAGINATION = "SELECT id, first_name, last_name FROM customers LIMIT ? OFFSET ?";

    public static final String GET_CUSTOMER_BY_ID = "SELECT id, first_name, last_name FROM customers WHERE id=?";

    public static final String IS_CUSTOMER_EXIST = "SELECT count(*) as count FROM customers WHERE id = ?";

    public static final String GET_CUSTOMER_BY_ORDER = 
        """
        SELECT c.id as c_id , c.first_name as c_fn, c.last_name as c_ln, o.tax_rate as o_trate, o.order_date as o_odate
        FROM customers c, orders o 
        WHERE c.id = o.customer_id
        AND c.id = ?
        """;
}
