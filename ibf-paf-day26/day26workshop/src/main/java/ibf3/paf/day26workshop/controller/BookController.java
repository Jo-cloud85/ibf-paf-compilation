package ibf3.paf.day26workshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ibf3.paf.day26workshop.model.Book;
import ibf3.paf.day26workshop.model.BookSummary;
import ibf3.paf.day26workshop.service.BooksService;

@RestController
@RequestMapping
public class BookController {

    @Autowired
    private BooksService bookService;
    
    @GetMapping(path="/search")
    public ModelAndView findBooksByTitle(@RequestParam("search") String title){

        ModelAndView mav = new ModelAndView("book-titles");
        List<BookSummary> bookList = bookService.findBooksbyTitle(title);
        mav.addObject("bookList", bookList);

        return mav;
    }

    @GetMapping(value="/book/{id}")
    public ModelAndView getBookDetails(@PathVariable String id){

        ModelAndView mav = new ModelAndView();
		Optional<Book> opt = bookService.findBookById(id);
		if (opt.isEmpty()) {
			mav.setStatus(HttpStatusCode.valueOf(404));
			mav.setViewName("not-found");
			mav.addObject("id", id);
		} else {
			mav.setStatus(HttpStatusCode.valueOf(200));
			mav.setViewName("book-details");
			mav.addObject("book", opt.get());
		}
		return mav;
    }
}
