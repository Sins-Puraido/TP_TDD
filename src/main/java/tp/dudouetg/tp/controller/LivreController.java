package tp.dudouetg.tp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService BookService;

    public BookController(BookService BookService) {
        this.BookService = BookService;
    }

    @PostMapping
    public Book ajouterBook(@RequestBody Book Book) {
        return BookService.ajouterBook(Book);
    }

    @GetMapping
    public List<Book> getBooks() {
        return BookService.getBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return BookService.getBookById(id)
                .orElseThrow(() -> new BookNotFoundException("Book non trouvé avec l'ID : " + id));
    }

    @DeleteMapping("/{id}")
    public void supprimerBook(@PathVariable Long id) {
        BookService.supprimerBook(id);
    }
}