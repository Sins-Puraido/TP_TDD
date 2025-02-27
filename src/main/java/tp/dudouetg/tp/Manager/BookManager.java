package tp.dudouetg.tp.Manager;

import tp.dudouetg.tp.exception.InvalidBookFormatException;
import tp.dudouetg.tp.exception.InvalidFieldNameException;
import tp.dudouetg.tp.model.Book;
import tp.dudouetg.tp.services.BookDataService;
import tp.dudouetg.tp.services.IsbnValidator;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

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
        isValidBookIsbn(book.getIsbn());
        return databaseService.saveBook(book);
    }

    public boolean isValidBookIsbn(String isbn) {
        return validator.validateIsbn(isbn);
    }

    public Collection<Book> getBooksByAuthor(String author) {

     return databaseService.getBookByAuthor(author);
    }

    public Book getBook(String isbn) {
        Book book =  databaseService.getBook(isbn);

        //case were the book is not in db
        if(book == null) {
            book = webService.getBook(isbn);
            databaseService.saveBook(book); //save the book in db for later
            return book;
        }
        else {
            if(!isBookComplete(book)) { //case were the book is missing informations
                Book completeBook = webService.getBook(isbn);
                databaseService.updateAllBooksData(book.getIsbn(), completeBook); //we update every info about the book except isdb and availability
                return completeBook;
            }
            else return book;
        }
    }

    public boolean isBookComplete(Book book) {
        return !book.getAuthor().isEmpty() && !book.getTitle().isEmpty() && !book.getFormat().isEmpty() && !book.getPublisher().isEmpty();
    }

    public Book updateBook(Book book,  String field, String value) {
        if(isValidfield(field, Book.class)){
            if(Objects.equals(field, "format")){
                if(!Objects.equals(value, "Broché") && !Objects.equals(value, "Poche") && !Objects.equals(value, "Grand format")){
                    throw new InvalidBookFormatException();
                }
            }
            return databaseService.updateBook(book, field, value);
        }
        else throw new InvalidFieldNameException();


    }

    public boolean deleteBook(String isbn) {
        return databaseService.removeBook(isbn);
    }

    public boolean isValidfield(String fieldname, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldname)) {
                return true;
            }
        }
        return false;
    }
}