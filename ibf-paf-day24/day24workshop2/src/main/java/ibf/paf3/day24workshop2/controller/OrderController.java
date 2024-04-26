package ibf.paf3.day24workshop2.controller;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf.paf3.day24workshop2.model.Order;
import ibf.paf3.day24workshop2.model.OrderDetails;
import ibf.paf3.day24workshop2.service.OrderService;

@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderSvc;

    // You cannot use @RequestBody alone because your Content-Type should be: application/x-www-form-urlencoded
    // so you need MultiValueMap

    // You also cannot use @ModelAttribute because of the OrderDetails. The HTML cannot convert the String to a List
    // for Spring Boot to read so your OrderDetails will always be an empty list

    @SuppressWarnings("null")
    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody MultiValueMap<String, String> orderFormMap) {
        
        // Process orderForm and save order to database using OrderService
        try {
            // System.out.println("Received OrderForm: " + orderFormMap);

            // Extract form data from MultiValueMap
            String orderDateStr = orderFormMap.getFirst("orderDate");
            String customerName = orderFormMap.getFirst("customerName");
            String shipAddress = orderFormMap.getFirst("shipAddress");
            String notes = orderFormMap.getFirst("notes");
            String taxStr = orderFormMap.getFirst("tax");

            // Process order and save to database using OrderService
            Order order = new Order();
            order.setOrderDate(LocalDate.parse(orderDateStr)); // Assuming orderDate is in ISO format
            order.setCustomerName(customerName);
            order.setShipAddress(shipAddress);
            order.setNotes(notes);
            order.setTax(Float.parseFloat(taxStr)/100);

            // Extract and process orderDetails
            List<OrderDetails> orderDetailsList = new LinkedList<>();

            List<String> productList = orderFormMap.get("product");
            int listSize = productList.size(); //for iteration
            List<String> unitPriceList = orderFormMap.get("unitPrice");
            List<String> discountList = orderFormMap.get("discount");
            List<String> quantityList = orderFormMap.get("quantity");

            for (int i=0; i < listSize; i++) {
                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setProduct(productList.get(i));
                orderDetail.setUnitPrice(Float.parseFloat(unitPriceList.get(i)));
                orderDetail.setDiscount(Float.parseFloat(discountList.get(i))/100);
                orderDetail.setQuantity(Integer.parseInt(quantityList.get(i)));
                orderDetailsList.add(orderDetail);
            }

            // Set orderDetails list to the Order object
            order.setOrderDetails(orderDetailsList);

            // System.out.println("Order: " + order);

            // Save order using OrderService
            orderSvc.createOrder(order);

            return ResponseEntity.ok("Order created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order");
        }
    }
}
