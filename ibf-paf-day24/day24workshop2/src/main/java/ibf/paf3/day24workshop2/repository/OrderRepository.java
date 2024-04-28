package ibf.paf3.day24workshop2.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf.paf3.day24workshop2.model.Order;
import ibf.paf3.day24workshop2.model.OrderDetails;

@Repository
public class OrderRepository implements Queries {
 
    @Autowired
    private JdbcTemplate template;

    // You don't need this method if you are using preparedStatement method in OrderService
    public Integer addOrder(Order order) {
        // System.out.println("FROM ORDER REPO " + order);
        template.update(
            SQL_INSERT_ORDER, 
            order.getOrderDate(),
            order.getCustomerName(),
            order.getShipAddress(),
            order.getNotes(),
            order.getTax());
        return template.queryForObject(SQL_GET_ID, Integer.class);
    }

    public Integer addOrderDetails(int orderId, OrderDetails od) {
        template.update(
            SQL_INSERT_ORDER_DETAILS, 
            od.getProduct(), 
            od.getUnitPrice(), 
            od.getDiscount(), 
            od.getQuantity(),
            orderId
        );
        return template.queryForObject(SQL_GET_ID, Integer.class);
    }
}
