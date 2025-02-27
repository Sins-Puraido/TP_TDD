package tp.dudouetg.tp.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tp.dudouetg.tp.services.MemberService;
import tp.dudouetg.tp.services.ReservationService;

import static org.mockito.Mockito.mock;

public class ReservationManagerTest {

    private ReservationService fakeDatabaseService;
    private ReservationService fakeWebService;

    @BeforeEach
    public void setUp() {
        fakeDatabaseService = mock(ReservationService.class);
        fakeWebService = mock(ReservationService.class);
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
}
