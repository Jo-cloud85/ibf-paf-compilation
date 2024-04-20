package ibf2023.paf.day24.repo;

public interface Queries {
    public static final String SQL_LI_INSERT_LINEITEM = """
        insert into line_items(item, quantity, po_id) 
        values (?, ?, ?)
    """;

    public static final String SQL_PO_INSERT_PURCHASEORDER = """
        insert into purchase_order(po_id, email, delivery_date, rush, comments, last_update) 
        values (?, ?, ?, ?, ?, ?)
    """;

    public static final String SQL_DOES_CUSTOMER_EXIST = """
        SELECT COUNT(*) FROM customers WHERE email = ?
    """;

    public static final String SQL_INSERT_CUSTOMER = """
        insert into customers(email, name) 
        values (?, ?)   
    """;
}
