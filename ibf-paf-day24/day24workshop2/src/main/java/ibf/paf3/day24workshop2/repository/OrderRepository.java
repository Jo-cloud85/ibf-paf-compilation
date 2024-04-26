package ibf.paf3.day24workshop2.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf.paf3.day24workshop2.constant.Queries;
import ibf.paf3.day24workshop2.model.Order;

// Redundant now since most of the work is in OrderService

@Repository
public class OrderRepository implements Queries {
 
    @Autowired
    private JdbcTemplate template;

    public void createOrder(Order order) {
        System.out.println("FROM ORDER REPO " + order);
        template.update(
            SQL_INSERT_ORDER, 
            order.getOrderDate(),
            order.getCustomerName(),
            order.getShipAddress(),
            order.getNotes(),
            order.getTax());
    }
}
