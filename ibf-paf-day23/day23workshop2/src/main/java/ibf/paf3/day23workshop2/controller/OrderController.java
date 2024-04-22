package ibf.paf3.day23workshop2.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ibf.paf3.day23workshop2.model.Order;
import ibf.paf3.day23workshop2.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderSvc;
    
    @GetMapping(value="/total")
    public ModelAndView getOrderDetailsById(@RequestParam("id") int id) {

        ModelAndView mav = new ModelAndView();

        Optional<Order> opt = orderSvc.searchOrderDetailsById(id);
		if (opt.isEmpty()) {
			mav.setStatus(HttpStatusCode.valueOf(404));
			mav.setViewName("not-found");
			mav.addObject("id", id);
		} else {
			mav.setStatus(HttpStatusCode.valueOf(200));
			mav.setViewName("order-details");
			mav.addObject("order", opt.get());
		}

        return mav;
    }
}
