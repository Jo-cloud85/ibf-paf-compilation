package ibf.paf3.day23workshop2.service;

public interface Queries {
    public static final String SQL_TOTAL_ORDER_DETAILS_BY_ORDER_ID =
    """
        SELECT 
            od.order_id AS orderId,
            o.order_date AS orderDate,
            o.customer_id AS customerId,
            SUM(od.quantity*od.unit_price*(1-od.discount)) AS totalPriceOfOrder,
            SUM(od.quantity*p.standard_cost) AS totalCostPrice
        FROM 
            northwind.orders o JOIN northwind.order_details od JOIN northwind.products p
        ON
            o.id = od.order_id AND od.product_id = p.id
        WHERE
            od.order_id = ?
        GROUP BY
            od.order_id, o.order_date, o.customer_id
        ORDER BY
            od.order_id
    """;
}
