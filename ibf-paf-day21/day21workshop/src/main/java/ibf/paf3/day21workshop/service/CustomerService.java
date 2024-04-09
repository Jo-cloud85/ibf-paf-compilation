package ibf.paf3.day21workshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf3.day21workshop.model.Customer;
import ibf.paf3.day21workshop.model.Orders;
import ibf.paf3.day21workshop.repository.CustomerRepository;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers () {
        return customerRepository.getAllCustomers();
    }

    public List<Customer> getAllCustomersWithPagination(int limit, int offset) {
        return customerRepository.getAllCustomersWithPagination(limit, offset);
    }

    public Customer getCustomerById(int id) {
        return customerRepository.getCustomerById(id);
    }

    public List<Orders> getCustomerByOrders(int id){
        return customerRepository.getCustomerByOrders(id);
    }
}
