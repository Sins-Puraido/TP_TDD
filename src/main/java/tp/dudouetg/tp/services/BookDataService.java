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

    default void removeBook(String Isbn){}

   default Book updateBook(Book book, String fields, String value) {
        return book;
   }

   default Boolean changeBookReservationStatus(String Isbn) {
        return false;
   }

}
