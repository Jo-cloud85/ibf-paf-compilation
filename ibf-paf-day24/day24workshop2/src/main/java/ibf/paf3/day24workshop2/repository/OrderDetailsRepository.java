package ibf.paf3.day24workshop2.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf.paf3.day24workshop2.constant.Queries;
import ibf.paf3.day24workshop2.model.OrderDetails;

@Repository
public class OrderDetailsRepository implements Queries {
    
    @Autowired
    private JdbcTemplate template;

    public void createOrderDetailsList(Integer orderId, List<OrderDetails> orderDetailsList) {
        for (OrderDetails od : orderDetailsList) {
            template.update(
                SQL_INSERT_ORDER_DETAILS, 
                od.getProduct(), 
                od.getUnitPrice(), 
                od.getDiscount(), 
                od.getQuantity(),
                orderId
            );
        }
    }
}
