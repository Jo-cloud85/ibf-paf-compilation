package ibf.paf3.day23workshop2.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf.paf3.day23workshop2.model.Order;
import ibf.paf3.day23workshop2.repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepo;

    public Optional<Order> searchOrderDetailsById(int id) {
        return orderRepo.searchOrderDetailsById(id);
    }
}
