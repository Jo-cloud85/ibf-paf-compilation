package ibf2023.paf.day25;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf2023.paf.day25.services.MessageService;

@SpringBootApplication
public class Day25Application implements CommandLineRunner {

	@Autowired
	private MessageService msgSvc;

	public static void main(String[] args) {
		SpringApplication.run(Day25Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Thread.currentThread();
		Thread.sleep(5000);

		System.out.println("**** sending....");
		String msg = "hello %s".formatted(UUID.randomUUID().toString().substring(0, 8));
		System.out.printf(">>> sending: %s\n", msg);

		/* To further send new messages, connect to the Railway redis first 
		(you can of course test on localhost as well):
		redis-cli -h viaduct.proxy.rlwy.net -p 34386 -a JPLvvdChMTdiohbVevExECEirwhgBNyE

		Then make sure you push new messages in json format i.e.
		LPUSH myqueue '{"id":"another_id","message":"This is a new message"}'
		
		You will then see new messages printed on the console. The printing comes from 
		MessageSubscriber class. */

		msgSvc.send(msg);
	}
}
