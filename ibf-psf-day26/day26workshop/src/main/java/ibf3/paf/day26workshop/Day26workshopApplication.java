package ibf3.paf.day26workshop;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf3.paf.day26workshop.model.Book;
import ibf3.paf.day26workshop.model.BookSummary;
import ibf3.paf.day26workshop.service.BooksService;

@SpringBootApplication
public class Day26workshopApplication implements CommandLineRunner {

	@Autowired
	private BooksService booksSvc;

	public static void main(String[] args) {
		SpringApplication.run(Day26workshopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// System.out.println("=============================");
		
		// for (BookSummary b: booksSvc.findBooksbyTitle("harry")) {
		// 	System.out.printf("\nSummary: %s\n", b);
		// 	Optional<Book> opt = booksSvc.findBookById(b.id());
		// 	System.out.printf("Book: %s\n", opt.get());
		// }
		// System.exit(0);
		
	}
}
