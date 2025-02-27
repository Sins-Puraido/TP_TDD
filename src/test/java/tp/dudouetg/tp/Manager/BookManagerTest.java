package tp.dudouetg.tp.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import tp.dudouetg.tp.exception.InvalidBookFormatException;
import tp.dudouetg.tp.exception.InvalidFieldNameException;
import tp.dudouetg.tp.exception.InvalidIsbnLengthException;
import tp.dudouetg.tp.exception.IsdbNotInDBException;
import tp.dudouetg.tp.model.Book;
import tp.dudouetg.tp.services.BookDataService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookManagerTest {

    private BookDataService fakeDatabaseService;
    private BookDataService fakeWebService;
    private BookManager bookManager;

    @BeforeEach
    public void setUp() {
        fakeDatabaseService = mock(BookDataService.class);
        fakeWebService = mock(BookDataService.class);
        bookManager = new BookManager(fakeDatabaseService, fakeWebService);
    }


    /* -------------------------------------------------------
     * CREATE
     * -------------------------------------------------------*/
    @Test
    public void BookCreationIsValid_getBookFromDB() {
        Book book = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        when(fakeDatabaseService.saveBook(book)).thenReturn(book);
        Book savedBook = bookManager.createBook(book);
        assertNotNull(savedBook);
        assertEquals("1234567890", savedBook.getIsbn());
        verify(fakeDatabaseService, times(1)).saveBook(book);
    }

    @Test
    public void BookCreationAsInvalidIsbn_ShouldThrowException() {
        Book book = new Book("1984", "George Orwell", "invalid_isbn", "Secker & Warburg", "Poche", true);

        assertThrows(InvalidIsbnLengthException.class, () -> bookManager.createBook(book));

    }

    /* -------------------------------------------------------
     * READ
     * -------------------------------------------------------*/

    @Test
    public void getBookByAuthor_shouldReturnBookArray() {
        Book book = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        Book book2 = new Book("1984", "George", "1234567890", "Secker & Warburg", "Poche", true);
        Book book3 = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        when(fakeDatabaseService.getBookByAuthor("George Orwell")).thenReturn(List.of(book, book3));
        assertEquals(2, bookManager.getBooksByAuthor("George Orwell").size());

    }

    @Test
    public void getBookByName_shouldReturnBookArray() {
        Book book = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        Book book2 = new Book("1984", "George Orwell", "1234567890", "Harper Perennial", "Poche", true);
        Book book3 = new Book("la ferme aux animaux", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);

        when(fakeDatabaseService.getBookByAuthor("George Orwell")).thenReturn(List.of(book, book2));
        assertEquals(2, bookManager.getBooksByAuthor("George Orwell").size());
    }

    @Test
    public void givenBookIsInDB_getBookDataFromDB()
    {
        Book book = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        when(fakeDatabaseService.getBook("1234567890")).thenReturn(book);
        assertEquals(book, bookManager.getBook("1234567890"));
    }

    @Test
    public void givenBookIsNotInDB_getBookDataFromWebService() {
        Book book = new Book("1984", "George Orwell", "2253009687", "Secker & Warburg", "Poche", true);
        when(fakeDatabaseService.getBook("2253009687")).thenReturn(null);
        when(fakeWebService.getBook("2253009687")).thenReturn(book);
        assertEquals(book, bookManager.getBook("2253009687"));
    }

    @Test
    public void givenBookIsNotInDB_bookFromWebserviceIsStoredInDB() {
        Book book = new Book("1984", "George Orwell", "2253009687", "Secker & Warburg", "Poche", true);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        when(fakeDatabaseService.getBook("2253009687")).thenReturn(null);
        when(fakeWebService.getBook("2253009687")).thenReturn(book);

        bookManager.getBook("2253009687");
        verify(fakeDatabaseService).saveBook(bookArgumentCaptor.capture());
    }

    @Test
    public void givenBookIsInDBMissingData_bookWithMissingDataFromWebService() {
        Book bookFromDB = new Book("1984", "George Orwell", "2253009687", "", "", true);
        Book bookFromWebService = new Book("1984", "George Orwell", "2253009687", "Secker & Warburg", "Poche", true);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        when(fakeDatabaseService.getBook("2253009687")).thenReturn(bookFromDB);
        when(fakeWebService.getBook("2253009687")).thenReturn(bookFromWebService);

        assertEquals(bookFromWebService, bookManager.getBook("2253009687"));
        verify(fakeDatabaseService, times(1)).updateAllBooksData(bookFromDB.getIsbn(), bookFromWebService);

    }

    /* -------------------------------------------------------
     * UPDATE
     * -------------------------------------------------------*/
    @Test
    public void BookModificationIsValid_ShouldReturnModifiedBook() {
        Book book = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        Book expectedBook = new Book("la ferme aux animaux", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        String newTitle = "la ferme aux animaux";
        when(fakeDatabaseService.updateBook(book, "title", newTitle)).thenReturn(expectedBook);
        Book updatedBook = bookManager.updateBook(book, "title", newTitle);
        assertEquals(expectedBook.toString(), updatedBook.toString());
    }


    @Test
    public void BookModificationFormatIsInvalid_ShouldReturnException() {
        Book book = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        Book expectedBook = new Book("la ferme aux animaux", "George Orwell", "1234567890", "Secker & Warburg", "newFormat", true);
        String newFormat = "Invalide Format";
        when(fakeDatabaseService.updateBook(book, "title", newFormat)).thenReturn(book);
        assertThrows(InvalidBookFormatException.class, () -> bookManager.updateBook(book, "format", newFormat));
    }

    @Test
    public void BookModificationFieldIsInvalid_ShouldReturnException() {
        Book book = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        Book expectedBook = new Book("la ferme aux animaux", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        String newTitle = "la ferme aux animaux";
        when(fakeDatabaseService.updateBook(book, "invalideField", newTitle)).thenReturn(book);
        assertThrows(InvalidFieldNameException.class, () -> bookManager.updateBook(book, "invalidField", newTitle));
    }

    /* -------------------------------------------------------
     * DELETE
     * -------------------------------------------------------*/
    @Test
    public void BookToDeleteISNotInDB_ShouldReturnFalse() {
        when(fakeDatabaseService.removeBook("2253009687")).thenThrow(IsdbNotInDBException.class);
        assertThrows(IsdbNotInDBException.class, () -> bookManager.deleteBook("2253009687"));

    }

    @Test
    public void BookDeletionWorked_ShouldReturnTrue() {
        assertTrue(bookManager.deleteBook("2253009687"));
    }

    /* -------------------------------------------------------
     * OTHER
     * -------------------------------------------------------*/
    @Test
    public void BookIsGettingReserved(){

    }

}
