package ibf.paf3.day24workshop2.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf.paf3.day24workshop2.exception.OrderNotFoundException;
import ibf.paf3.day24workshop2.model.Order;
import ibf.paf3.day24workshop2.model.OrderDetails;
import ibf.paf3.day24workshop2.service.OrderService;

@Controller
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderSvc;

    // You cannot use @RequestBody alone because your Content-Type should be: application/x-www-form-urlencoded
    // so you need MultiValueMap

    // You also cannot use @ModelAttribute alone because of the OrderDetails. The HTML cannot convert the String to a List
    // for Spring Boot to read so your OrderDetails will always be an empty list

    // Method 1 -------------------------------------------------------
    @PostMapping("/order")
    public String createOrder(
            @ModelAttribute Order order, 
            @RequestBody MultiValueMap<String,String> orderFormMap) throws OrderNotFoundException {

        List<OrderDetails> details = new LinkedList<>();
        if (orderFormMap.containsKey("product")) {
            for (int i = 0; i < orderFormMap.get("product").size(); i++) {
                OrderDetails detail = new OrderDetails();
                detail.setProduct(orderFormMap.get("product").get(i));
                detail.setUnitPrice(Float.parseFloat(orderFormMap.get("unitPrice").get(i)));
                detail.setDiscount(Float.parseFloat(orderFormMap.get("discount").get(i))/100);
                detail.setQuantity(Integer.parseInt(orderFormMap.get("quantity").get(i)));
                details.add(detail);
            }
        }

        orderSvc.createOrderwithDetails(order, details);
        return "order-success";
    }

    // Method 2 -------------------------------------------------------
    // @PostMapping("/order")
    // public String createOrder(HttpServletRequest request) throws OrderNotFoundException {
    //     Order order = new Order();
    //     order.setCustomerName(request.getParameter("customerName"));
    //     order.setShipAddress(request.getParameter("shipAddress"));
    //     order.setNotes(request.getParameter("notes"));
    //     order.setTax(Float.parseFloat(request.getParameter("tax"))/100);
    
    //     List<OrderDetails> details = new LinkedList<>();
    //     String[] products = request.getParameterValues("product");
    //     String[] unitPrices = request.getParameterValues("unitPrice");
    //     String[] discounts = request.getParameterValues("discount");
    //     String[] quantities = request.getParameterValues("quantity");
    
    //     if (products != null) {
    //         for (int i = 0; i < products.length; i++) {
    //             OrderDetails detail = new OrderDetails();
    //             detail.setProduct(products[i]);
    //             detail.setUnitPrice(Float.parseFloat(unitPrices[i]));
    //             detail.setDiscount(Float.parseFloat(discounts[i])/100);
    //             detail.setQuantity(Integer.parseInt(quantities[i]));
    //             details.add(detail);
    //         }
    //     }
    
    //     orderSvc.createOrderwithDetails(order, details);
    //     return "order-success";
    // }


    // Method 3 -------------------------------------------------------
    // @PostMapping("/order")
    // public ResponseEntity<String> createOrder(@RequestBody MultiValueMap<String, String> orderFormMap) {
        
    //     try {
    //         // Extract form data from MultiValueMap
    //         String orderDateStr = orderFormMap.getFirst("orderDate");
    //         String customerName = orderFormMap.getFirst("customerName");
    //         String shipAddress = orderFormMap.getFirst("shipAddress");
    //         String notes = orderFormMap.getFirst("notes");
    //         String taxStr = orderFormMap.getFirst("tax");

    //         // Process order and save to database using OrderService
    //         Order order = new Order();
    //         order.setOrderDate(LocalDate.parse(orderDateStr));
    //         order.setCustomerName(customerName);
    //         order.setShipAddress(shipAddress);
    //         order.setNotes(notes);
    //         order.setTax(Float.parseFloat(taxStr)/100);

    //         // Extract and process orderDetails
    //         List<OrderDetails> orderDetailsList = new LinkedList<>();

    //         List<String> productList = orderFormMap.get("product");
    //         int listSize = productList.size(); //for iteration
    //         List<String> unitPriceList = orderFormMap.get("unitPrice");
    //         List<String> discountList = orderFormMap.get("discount");
    //         List<String> quantityList = orderFormMap.get("quantity");

    //         for (int i=0; i < listSize; i++) {
    //             OrderDetails orderDetail = new OrderDetails();
    //             orderDetail.setProduct(productList.get(i));
    //             orderDetail.setUnitPrice(Float.parseFloat(unitPriceList.get(i)));
    //             orderDetail.setDiscount(Float.parseFloat(discountList.get(i))/100);
    //             orderDetail.setQuantity(Integer.parseInt(quantityList.get(i)));
    //             orderDetailsList.add(orderDetail);
    //         }

    //         // Set orderDetails list to the Order object
    //         order.setOrderDetails(orderDetailsList);

    //         // System.out.println("Order: " + order);

    //         // Save order using OrderService
    //         orderSvc.createOrder(order);

    //         return ResponseEntity.ok("Order created successfully");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order");
    //     }
    // }
}
