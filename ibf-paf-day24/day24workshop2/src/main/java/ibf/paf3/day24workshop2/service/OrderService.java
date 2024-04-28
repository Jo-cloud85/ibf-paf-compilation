package ibf.paf3.day24workshop2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf.paf3.day24workshop2.exception.OrderNotFoundException;
import ibf.paf3.day24workshop2.model.Order;
import ibf.paf3.day24workshop2.model.OrderDetails;
import ibf.paf3.day24workshop2.repository.OrderRepository;
import ibf.paf3.day24workshop2.repository.Queries;

// Simply put when you have 2 processes/queries that need to happen at the same time then
// we need @Transactional 

@Service
public class OrderService implements Queries {
    
    @Autowired
    private OrderRepository orderRepo;
    
    @Transactional(rollbackFor = OrderNotFoundException.class)
    public void createOrderwithDetails(Order order, List<OrderDetails> details) throws OrderNotFoundException {
        try {
            int orderId = orderRepo.addOrder(order);
            int detailId = 0;
            for (OrderDetails detail : details)
                detailId = orderRepo.addOrderDetails(orderId, detail);
            if (orderId == 0 || detailId == 0)
                throw new OrderNotFoundException();
        } catch (Exception e) {
            throw new OrderNotFoundException("Unable to add order");
        }
    }

    // Autowire the DataSource bean into your service class
    // @Autowired
    // private DataSource dataSource;

    // @Transactional(rollbackFor = OrderNotFoundException.class)
    // public void createOrder(Order order) throws OrderNotFoundException {
    //     Connection connection = null;
    //     PreparedStatement preparedStatement = null;
    //     ResultSet resultSet = null;
    //     try {
    //         // Start transaction
    //         connection = dataSource.getConnection(); // Assuming dataSource is properly configured
    //         preparedStatement = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
    //         // Set parameters for the prepared statement
    //         preparedStatement.setDate(1, java.sql.Date.valueOf(order.getOrderDate()));
    //         preparedStatement.setString(2, order.getCustomerName());
    //         preparedStatement.setString(3, order.getShipAddress());
    //         preparedStatement.setString(4, order.getNotes());
    //         preparedStatement.setFloat(5, order.getTax());

    //         // Execute the insert operation
    //         int affectedRows = preparedStatement.executeUpdate();

    //         // Check if any rows were affected
    //         if (affectedRows == 0) {
    //             throw new SQLException("Insertion failed, no rows affected.");
    //         }

    //         System.out.println(preparedStatement);

    //         // Retrieve auto-generated keys
    //         resultSet = preparedStatement.getGeneratedKeys();

    //         // Process the generated keys
    //         if (resultSet.next()) {
    //             int generatedOrderId = resultSet.getInt(1); // Assuming the auto-generated key is the first column
    //             System.out.println("Generated Order ID: " + generatedOrderId);

    //             // Set the generated orderId back to the order object
    //             order.setOrderId(generatedOrderId);

    //             // Set orderId for each order detail
    //             List<OrderDetails> orderDetailsList = order.getOrderDetails();
    //             if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
    //                 for (OrderDetails od: orderDetailsList) {
    //                     // Save the order details associated with the orderId
    //                     orderRepo.addOrderDetails(generatedOrderId, od);
    //                 }
                    
    //             }
    //         } else {
    //             throw new SQLException("No auto-generated keys returned.");
    //         }

    //         // Commit transaction
    //     } catch (Exception ex) {
    //         System.out.println(ex.getMessage());
    //         throw new OrderNotFoundException(ex.getMessage());
    //     } finally {
    //         // Close resources
    //         try {
    //             if (resultSet != null) resultSet.close();
    //             if (preparedStatement != null) preparedStatement.close();
    //             if (connection != null) connection.close();
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }
}



    

