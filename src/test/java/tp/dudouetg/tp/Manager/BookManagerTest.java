package tp.dudouetg.tp.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tp.dudouetg.tp.exception.InvalidIsbnLengthException;
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
    public void BookCreationAsInvalidIsbn_ShouldReturnFalse() {
        Book book = new Book("1984", "George Orwell", "invalid_isbn", "Secker & Warburg", "Poche", true);

        assertThrows(InvalidIsbnLengthException.class, () -> bookManager.isValidBookIsbn(book.getIsbn()));

    }


    /* -------------------------------------------------------
     * READ
     * -------------------------------------------------------*/

    @Test
    public void getBookByAuthor_shouldReturnBook() {
        Book book = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        when(fakeDatabaseService.getBookByAuthor("George Orwell")).thenReturn(List.of(book));
        assertEquals(1, bookManager.getBooksByAuthor("George Orwell").size());

    }

    @Test
    public void getBookByName_shouldReturnBook() {
        Book book = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        when(fakeDatabaseService.getBookByAuthor("George Orwell")).thenReturn(List.of(book));
        assertEquals(1, bookManager.getBooksByAuthor("George Orwell").size());
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
        when(fakeDatabaseService.getBook("2253009687")).thenReturn(null);
        when(fakeWebService.getBook("2253009687")).thenReturn(book);
        assertEquals(book, bookManager.getBook("2253009687"));
    }

    @Test
    public void givenBookIsInDBMissingData_bookWithMissingDataFromWebService() {

    }

    /* -------------------------------------------------------
     * UPDATE
     * -------------------------------------------------------*/
    @Test
    public void BookModificationIsValid_ShouldReturnModifiedBook() {
        Book book = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);
        when(fakeDatabaseService.updateBook(book)).thenReturn(book);
        Book updatedBook = bookManager.updateBook(book);
        assertNotNull(updatedBook);
    }

    @Test
    public void BookModificationIsbnIsInvalid_ShouldReturnException() {
        Book originalBook = new Book("1984", "George Orwell", "1234567890", "Secker & Warburg", "Poche", true);

        when(fakeDatabaseService.updateBook(originalBook, "format", "Broché")).thenReturn(book);
        Book updatedBook = bookManager.updateBook(book);
        assertNotNull(updatedBook);
    }

    @Test
    public void BookModificationFormatIsInvalid_ShouldReturnException() {

    }

    @Test
    public void BookModificationFieldIsInvalid_ShouldReturnException() {

    }

    /* -------------------------------------------------------
     * DELETE
     * -------------------------------------------------------*/
    @Test
    public void BookToDeleteISNotInDB_ShouldReturnFalse() {
        doNothing().when(fakeDatabaseService).removeBook("2253009687");
        assertFalse(bookManager.deleteBook("2253009687"));
    }

    @Test
    public void BookDeletionWorked_ShouldReturnTrue() {
        doNothing().when(fakeDatabaseService).removeBook("2253009687");
        assertTrue(bookManager.deleteBook("2253009687"));
    }

    /* -------------------------------------------------------
     * OTHER
     * -------------------------------------------------------*/
    @Test
    public void BookIsGettingReserved(){

    }

}
