package ibf.paf3.day21workshop.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibf.paf3.day21workshop.model.Customer;
import ibf.paf3.day21workshop.model.Orders;
import ibf.paf3.day21workshop.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {
    
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers(@RequestParam int limit, @RequestParam int offset) {
        return customerService.getAllCustomersWithPagination(limit, offset);
    }
    
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") int id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/{id}/orders")
    public List<Orders> getCustomerByOrder(@PathVariable("id") int id) {
        return customerService.getCustomerByOrders(id);
    }
}
