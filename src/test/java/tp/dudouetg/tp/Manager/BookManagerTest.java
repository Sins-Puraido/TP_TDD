package tp.dudouetg.tp.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tp.dudouetg.tp.services.BookService;

import static org.mockito.Mockito.mock;

public class BookManagerTest {

    private BookService fakeDatabaseService;
    private BookService fakeWebService;

    @BeforeEach
    public void setUp() {
        fakeDatabaseService = mock(BookService.class);
        fakeWebService = mock(BookService.class);
    }


    /* -------------------------------------------------------
     * CREATE
     * -------------------------------------------------------*/
    @Test
    public void BookCreationIsValid_getBookFromDB() {
    }

    @Test
    public void BookCreationAsInvalidIsbn_ShouldReturnFalse() {
    }



    /* -------------------------------------------------------
     * READ
     * -------------------------------------------------------*/

    @Test
    public void getBookByAuthor_shouldReturnBook() {}

    @Test
    public void getBookByName_shouldReturnBook() {}

    @Test
    public void givenBookIsInDB_getBookDataFromDB(){
    }

    @Test
    public void givenBookIsInNotInDB_getBookDataFromWebService() {
    }

    @Test
    public void givenBookIsInNotInDB_bookFromWebserviceIsStoredInDB() {

    }

    @Test
    public void givenBookIsInDBMissingData_bookWithMissingDataFromWebService() {

    }

    /* -------------------------------------------------------
     * UPDATE
     * -------------------------------------------------------*/

    @Test
    public void BookModificationIsValid_ShouldReturnModifiedBook() {
    }

    @Test
    public void BookModificationIsInvalid_ShouldReturnFalse() {
    }

    /* -------------------------------------------------------
     * DELETE
     * -------------------------------------------------------*/
    @Test
    public void BookToDeleteISNotInDB_ShouldReturnFalse() {
    }

    @Test
    public void BookDeletionWorked_ShouldReturnTrue() {
    }

    /* -------------------------------------------------------
     * OTHER
     * -------------------------------------------------------*/
    @Test
    public void BookIsGettingReserved(){

    }

}
