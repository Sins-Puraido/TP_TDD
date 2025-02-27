package tp.dudouetg.tp.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tp.dudouetg.tp.services.ReservationDataService;

import static org.mockito.Mockito.mock;

public class ReservationManagerTest {

    private ReservationDataService fakeDatabaseService;
    private ReservationDataService fakeWebService;

    @BeforeEach
    public void setUp() {
        fakeDatabaseService = mock(ReservationDataService.class);
        fakeWebService = mock(ReservationDataService.class);
    }


    /* -------------------------------------------------------
     * CREATE
     * -------------------------------------------------------*/

    @Test
    public void BookReservationIsValid_ShouldReturnBookReservation() {
    }

    @Test
    public void BookReservationIsInvalid_ShouldReturnFalse() {}

    @Test
    public void MemberAsTooMuchReservation_ShouldReturnReservationFullMemberException(){}


    /* -------------------------------------------------------
     * READ
     * -------------------------------------------------------*/
    @Test
    public void BookGetReserved_shouldReturnTrue() {

    }

    @Test
    public void BookReservationFail_shouldReturnFalse() {}


    @Test
    public void BookGetReserved_shouldReturnFalse() {}



    /* -------------------------------------------------------
     * OTHER
     * -------------------------------------------------------*/
    @Test
    public void MemberCantReserveMoreBooks_ShouldReturnTooMuchReservationMemberException() {}

    @Test
    public void ReservationDateAfterGiveBackDate_shouldReturnInvalidReservationDateException() {}

    @Test
    public void GiveBackDateTooLate_shouldReturnInvalidReservationDateException() {}


    //envois mail a tout les utilisateur qui ont commende a échéance
}
