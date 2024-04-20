package ibf3.paf.day26workshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf3.paf.day26workshop.repo.BooksRepository;
import ibf3.paf.day26workshop.model.Book;
import ibf3.paf.day26workshop.model.BookSummary;

@Service
public class BooksService {

    @Autowired
    private BooksRepository bookRepo;

    public List<BookSummary> findBooksbyTitle (String title){
        return bookRepo.findBooksByTitle(title);
    }

    public Optional<Book> findBookById(String id) {
        return bookRepo.findBookById(id);
    }
}