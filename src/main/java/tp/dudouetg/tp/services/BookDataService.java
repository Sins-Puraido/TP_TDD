package tp.dudouetg.tp.services;

import tp.dudouetg.tp.model.Book;

import java.util.ArrayList;
import java.util.List;

public interface BookDataService {


    default Book saveBook(Book book) {
        return book;
    }

    default Book getBook(String Isbn){
        return new Book();
    };

    default List<Book> getBookByAuthor(String Author){
        return new ArrayList<Book>();
    }

    default List<Book> getBookByTitle(String Title){
        return new ArrayList<Book>();
    }

    default boolean removeBook(String Isbn){
        return true;
    }

   default Book updateBook(Book book, String fields, String value) {
        return book;
   }

   default Book updateAllBooksData(String isbn, Book book) {
        return null;
   }


   default Boolean changeBookReservationStatus(String Isbn) {
        return false;
   }

    default boolean isBookAvailable(String isbn){
        return true;
    };

    default boolean changeBookStatus(String isbn) {
        return true;
    }
}
