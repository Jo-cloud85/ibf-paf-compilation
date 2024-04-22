package ibf.paf3.day23workshop2.repository;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf.paf3.day23workshop2.model.Order;
import ibf.paf3.day23workshop2.service.Queries;

@Repository
public class OrderRepository implements Queries {
    
    @Autowired
    private JdbcTemplate template;

    public Optional<Order> searchOrderDetailsById(int id) {
        final SqlRowSet rs = template.queryForRowSet(SQL_TOTAL_ORDER_DETAILS_BY_ORDER_ID, id);

        if (rs.next()) {
            Order order = new Order();

            order.setOrderId(rs.getInt("orderId"));

            LocalDateTime ldt = (LocalDateTime) rs.getObject("orderDate");
            order.setOrderDate(ldt);

            order.setCustomerId(rs.getInt("customerId"));
            
            // Format totalPriceOfOrder to display with two decimal places
            double totalPrice = rs.getDouble("totalPriceOfOrder");
            DecimalFormat df = new DecimalFormat("0.00");
            String formattedTotalPrice = df.format(totalPrice);
            order.setTotalPriceOfOrder(Double.parseDouble(formattedTotalPrice));

            // Format totalCostPrice to display with two decimal places
            double totalCostPrice = rs.getDouble("totalCostPrice");
            String formattedTotalCostPrice = df.format(totalCostPrice);
            order.setTotalCostPrice(Double.parseDouble(formattedTotalCostPrice));

            return Optional.of(order);
        }
        return Optional.empty();
    }
}
