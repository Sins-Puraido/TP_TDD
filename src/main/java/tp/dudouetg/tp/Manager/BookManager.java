package tp.dudouetg.tp.Manager;

import tp.dudouetg.tp.model.Book;
import tp.dudouetg.tp.services.BookDataService;
import tp.dudouetg.tp.services.IsbnValidator;

import java.util.Collection;

public class BookManager {

    private final BookDataService databaseService;
    private final BookDataService webService;
    private final IsbnValidator validator;

    public BookManager(BookDataService databaseService, BookDataService webService) {
        this.databaseService = databaseService;
        this.webService = webService;
        this.validator = new IsbnValidator();
    }

    public Book createBook(Book book) {
        return databaseService.saveBook(book);
    }

    public boolean isValidBookIsbn(String isbn) {
        return validator.validateIsbn(isbn);
    }

    public Collection<Book> getBooksByAuthor(String author) {

     return databaseService.getBookByAuthor(author);
    }

    public Book getBook(String isbn) {
        return databaseService.getBook(isbn);
    }

    public Book updateBook(Book book) {
        return databaseService.updateBook(book);
    }

    public boolean deleteBook(String isbn) {
        return true;
    }
}