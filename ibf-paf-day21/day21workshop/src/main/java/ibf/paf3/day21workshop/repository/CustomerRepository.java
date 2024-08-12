package ibf.paf3.day21workshop.repository;

import java.time.LocalDateTime;
import java.util.Collections;
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
        List<Customer> result = new LinkedList<>();
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
        List<Customer> result = new LinkedList<>();
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

    public boolean isCustomerExist(int id){
        boolean isExist = false;
        final SqlRowSet rs = jdbcTemplate
                        .queryForRowSet(IS_CUSTOMER_EXIST, id);
        System.out.println("rs: "+rs);
        if(rs.next()){
            int x = rs.getInt("count");
            if(x>0){
                isExist = true;
            }
        }
        return isExist;
    }


    public List<Orders> getCustomerByOrders(int id) {
        List<Orders> result = new LinkedList<>();
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_CUSTOMER_BY_ORDER, id);
        while(rs.next()){
            Orders o = new Orders();
            o.setId(rs.getInt("c_id"));
            o.setFirstName(rs.getString("c_fn"));
            o.setLastName(rs.getString("c_ln"));
            o.setTaxRate(rs.getDouble("o_trate"));
            // Method 1 - and orderDate has to be of LocalDateTime
            // Retrieve the orderDate as Object (cannot be as Timestamp due to an internal bug)
            Object oDateObject = rs.getObject("o_odate");
            // System.out.println("oDateObject type: " + (oDateObject != null ? oDateObject.getClass().getName() : "null"));
            LocalDateTime orderDateTime = (LocalDateTime) oDateObject;
            o.setOrderDate(orderDateTime);

            // Method 2 - and orderDate has to be of Date - but then Date is not ideal since data from sql contains time
            // String dateString = rs.getString("o_odate");
            // if (dateString != null) {
            //     try {
            //         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            //         Date orderDate = dateFormat.parse(dateString);
            //         o.setOrderDate(orderDate);
            //     } catch (ParseException e) {
            //         System.err.println("Error parsing order date: " + e.getMessage());
            //     }
            // }
            result.add(o);
        }
        return Collections.unmodifiableList(result);
    }
}
