package ibf.paf3.day24workshop2.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ibf.paf3.day24workshop2.constant.Queries;
import ibf.paf3.day24workshop2.exception.OrderNotFoundException;
import ibf.paf3.day24workshop2.model.Order;
import ibf.paf3.day24workshop2.model.OrderDetails;
import ibf.paf3.day24workshop2.repository.OrderDetailsRepository;

// Simply put when you have 2 processes/queries that need to happen at the same time then
// we need @Transactional 

@Service
public class OrderService implements Queries {
    
    // @Autowired
    // private OrderRepository orderRepo;

    @Autowired
    private OrderDetailsRepository orderDetailsRepo;

    // Autowire the DataSource bean into your service class
    @Autowired
    private DataSource dataSource;

    // 'throws OrderException' is the rollback
    @Transactional(rollbackFor = OrderNotFoundException.class, isolation = Isolation.READ_COMMITTED)
    public void createOrder(Order order) throws OrderNotFoundException {
            
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                // Start transaction
                connection = dataSource.getConnection(); // Assuming dataSource is properly configured
                preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);

                // Set parameters for the prepared statement
                preparedStatement.setDate(1, java.sql.Date.valueOf(order.getOrderDate()));
                preparedStatement.setString(2, order.getCustomerName());
                preparedStatement.setString(3, order.getShipAddress());
                preparedStatement.setString(4, order.getNotes());
                preparedStatement.setFloat(5, order.getTax());

                // Execute the insert operation
                int affectedRows = preparedStatement.executeUpdate();

                // Check if any rows were affected
                if (affectedRows == 0) {
                    throw new SQLException("Insertion failed, no rows affected.");
                }

                System.out.println(preparedStatement);

                // Retrieve auto-generated keys
                resultSet = preparedStatement.getGeneratedKeys();

                // Process the generated keys
                if (resultSet.next()) {
                    int generatedOrderId = resultSet.getInt(1); // Assuming the auto-generated key is the first column
                    System.out.println("Generated Order ID: " + generatedOrderId);

                    // Set the generated orderId back to the order object
                    order.setOrderId(generatedOrderId);

                    // Set orderId for each order detail
                    List<OrderDetails> orderDetailsList = order.getOrderDetails();
                    if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
                        // Save the order details associated with the orderId
                        orderDetailsRepo.createOrderDetailsList(generatedOrderId, orderDetailsList);
                    }
                } else {
                    throw new SQLException("No auto-generated keys returned.");
                }

                // Commit transaction
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                throw new OrderNotFoundException(ex.getMessage());
            } finally {
                // Close resources
                try {
                    if (resultSet != null) resultSet.close();
                    if (preparedStatement != null) preparedStatement.close();
                    if (connection != null) connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
}



    

