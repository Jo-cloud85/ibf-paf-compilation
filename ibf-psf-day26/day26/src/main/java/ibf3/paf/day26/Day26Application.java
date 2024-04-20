package ibf3.paf.day26;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf3.paf.day26.repo.TvShowRepository;

@SpringBootApplication
public class Day26Application implements CommandLineRunner {

	@Autowired
	private TvShowRepository tvShowRepo;

	public static void main(String[] args) {
		SpringApplication.run(Day26Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("======================\n");
		for (Document doc: tvShowRepo.findShowsByName("and")){
            String name = doc.getString(Constants.F_NAME);
            List<String> genres = doc.getList("genres", String.class);
            System.out.printf("name: %s, genres: %s\n", name, genres);
			System.out.printf(">> doc: %s\n ", doc.toJson());
        }
        
		System.out.printf("Number of English shows: %s\n", tvShowRepo.countShowsByLanguage("English"));

		System.out.printf("Types of shows with an average rating gte 7: %s\n", tvShowRepo.getTypesByRating(7f));

		System.exit(0);
	}
}
