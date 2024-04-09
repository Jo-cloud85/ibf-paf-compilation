package ibf.paf3.day21workshop.repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf.paf3.day21workshop.model.Customer;
import ibf.paf3.day21workshop.model.Orders;

@Repository
public class CustomerRepository implements Queries {
    
    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<Customer> getAllCustomers() {
        List<Customer> result = new LinkedList<Customer>();
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_CUSTOMERS);
        while(rs.next()) {
            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            result.add(customer);
        }
        return (Collections.unmodifiableList(result));
    }


    public List<Customer> getAllCustomersWithPagination(int limit, int offset) {
        List<Customer> result = new LinkedList<Customer>();
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_ALL_CUSTOMERS_WITH_PAGINATION, limit, offset);
        while(rs.next()) {
            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            result.add(customer);
        }
        return (Collections.unmodifiableList(result));
    }


    public Customer getCustomerById(int id) {
        Customer customer = new Customer();
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_CUSTOMER_BY_ID, id);
        if(rs.next()) {
            customer.setId(rs.getInt("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
        }
        return customer;
    }


    public List<Orders> getCustomerByOrders(int id){
        List<Orders> result = new LinkedList<Orders>();
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_CUSTOMER_BY_ORDER, id);
        while(rs.next()){
            Orders o = new Orders();
            o.setId(rs.getInt("c_id"));
            o.setFirstName(rs.getString("c_fn"));
            o.setLastName(rs.getString("c_ln"));
            o.setTaxRate(rs.getDouble("o_trate"));
            // Method 1 - and orderDate has to be of LocalDateTime
            // o.setOrderDate(rs.getTimestamp("o_odate").toLocalDateTime());
            // Method 2 - and orderDate has to be of Date
            String dateString = rs.getString("o_odate");
            if (dateString != null) {
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    Date orderDate = dateFormat.parse(dateString);
                    o.setOrderDate(orderDate);
                } catch (ParseException e) {
                    System.err.println("Error parsing order date: " + e.getMessage());
                }
            }
            result.add(o);
        }
        return Collections.unmodifiableList(result);
    }
}
