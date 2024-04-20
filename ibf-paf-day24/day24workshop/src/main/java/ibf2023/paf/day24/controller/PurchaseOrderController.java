package ibf2023.paf.day24.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2023.paf.day24.exception.PurchaseOrderException;
import ibf2023.paf.day24.model.LineItem;
import ibf2023.paf.day24.model.PurchaseOrder;
import ibf2023.paf.day24.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService orderSvc;

    @PostMapping
    public ResponseEntity<String> submitOrder(@RequestBody PurchaseOrder order) throws PurchaseOrderException {

        // Logging the received order details 
        String poId = UUID.randomUUID().toString().substring(0, 8);
        System.out.println("Order id: " + poId);
        System.out.println("Received order for: " + order.getName());
        System.out.println("Email: " + order.getEmail());
        System.out.println("Delivery Date: " + order.getDeliveryDate());
        System.out.println("Rush: " + order.isRush());
        System.out.println("Comments: " + order.getComments());

        // From the payload, you can see that the key for lineItems is 'items' thus you have to match your
        // PurchaseOrder object field w/ 'items' i.e. private List<LineItem> items = new LinkedList<>();
        System.out.println("Ordered Items:" + order.getItems());
        for (LineItem item : order.getItems()) {
            System.out.println(item.getItem() + ": " + item.getQuantity());
        }

        order.setPoId(poId);

        orderSvc.insertPurchaseOrder(order);

        // Return success message
        return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Order submitted successfully!\"}");
    }
}
