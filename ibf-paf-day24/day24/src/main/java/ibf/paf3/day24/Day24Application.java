package ibf.paf3.day24;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf.paf3.day24.service.AccountsService;

@SpringBootApplication
public class Day24Application implements CommandLineRunner {

	@Autowired
	private AccountsService accountsSvc;

	public static void main(String[] args) {
		SpringApplication.run(Day24Application.class, args);
	}

	// Rmb, any error that leads to RuntimeException is unchecked, any error that leads to Throwable is checked exception
	@Override
	public void run(String... args) throws Exception {
		try {
			accountsSvc.fundsTransfer("1234", "abcd", 200);
		} catch (Exception ex) {
			System.out.printf(">>>> error: %s\n".formatted(ex.getMessage()));
		}

		System.exit(0);
	}
}
