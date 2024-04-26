package ibf2023.paf.day24;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day24Application implements CommandLineRunner {

	// @Autowired
	// private PurchaseOrderService poSvc;

	public static void main(String[] args) {
		SpringApplication.run(Day24Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// List<LineItem> lineItems = new LinkedList<>();
		// LineItem li = new LineItem();
		// li.setItem("apple");
		// li.setQuantity(10);
		// lineItems.add(li);

		// li = new LineItem();
		// li.setItem("orange");
		// li.setQuantity(5);
		// lineItems.add(li);

		// PurchaseOrder po = new PurchaseOrder();
		// po.setEmail("acme@gmail.com");
		// po.setDeliveryDate(new Date());
		// po.setRush(true);
		// po.setComments("Test order");
		// po.setLineItems(lineItems);

		// poSvc.insertPurchaseOrder(po);

		// System.exit(0);
	}
}
