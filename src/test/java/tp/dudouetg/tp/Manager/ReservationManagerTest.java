package tp.dudouetg.tp.Manager;

import com.mysql.cj.jdbc.CallableStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tp.dudouetg.tp.model.Book;
import tp.dudouetg.tp.model.Member;
import tp.dudouetg.tp.model.Reservation;
import tp.dudouetg.tp.services.BookDataService;
import tp.dudouetg.tp.services.MailerService;
import tp.dudouetg.tp.services.MemberDataService;
import tp.dudouetg.tp.services.ReservationDataService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationManagerTest {

    private ReservationDataService fakeReservationService;
    private BookDataService fakeBookService;
    private MemberDataService fakeMemberService;
    private MailerService fakeMailService;
    private ReservationManager reservationManager;

    @BeforeEach
    public void setUp() {
        fakeReservationService = mock(ReservationDataService.class);
        fakeBookService = mock(BookDataService.class);
        fakeMemberService = mock(MemberDataService.class);
        fakeMailService = mock(MailerService.class);
        reservationManager = new ReservationManager(fakeReservationService, fakeMemberService, fakeBookService, fakeMailService);
    }

    /* -------------------------------------------------------
     * CREATE
     * -------------------------------------------------------*/

    @Test
    public void BookReservationIsValid_ShouldReturnBookReservation() {
        Book book = new Book();
        Member member = new Member();
        Date date = new Date();
        CallableStatement startDate;
        Date endDate = new Date(date.getTime() + (120 * 24L * 60 * 60 * 1000));
        Reservation reservation = new Reservation(book, member, date, endDate, true);

        when(fakeReservationService.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationManager.reserveBook(member, book, new Date());

        assertNotNull(result);
        assertEquals(reservation, result);
    }


    @Test
    public void MemberAsTooMuchReservation_ShouldThrowException() {
        Book book = new Book();
        Member member = new Member();
        List<Reservation> memberReservations = Arrays.asList(
                new Reservation(),
                new Reservation(),
                new Reservation());
        Date date = new Date();
        CallableStatement startDate;
        Date endDate = new Date(date.getTime() + (120 * 24L * 60 * 60 * 1000));
        Reservation reservation = new Reservation(book, member, date, endDate, true);

        when(fakeMemberService.getMemberCurrentReservation(member.getCode())).thenReturn(memberReservations);


        assertThrows(RuntimeException.class, () -> reservationManager.reserveBook(member, book, new Date()));

    }

    @Test
    public void BookGetReserved_shouldReturnTrue() {
        Book book = new Book();
        Member member = new Member();
        Date date = new Date();
        CallableStatement startDate;
        Date endDate = new Date(date.getTime() + (120 * 24L * 60 * 60 * 1000));
        Reservation reservation = new Reservation(book, member, date, endDate, true);


        when(fakeBookService.isBookAvailable(book.getIsbn())).thenReturn(false);
        when(fakeReservationService.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationManager.reserveBook(member, book, new Date());

        assertNotNull(result);
        assertEquals(reservation, result);
    }

    @Test
    public void BookReservationFail_shouldReturnFalse() {
        Book book = new Book();
        Member member = new Member();
        Date date = new Date();
        CallableStatement startDate;
        Date endDate = new Date(date.getTime() + (120 * 24L * 60 * 60 * 1000));
        Reservation reservation = new Reservation(book, member, date, endDate, true);

        when(fakeBookService.isBookAvailable(book.getIsbn())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> reservationManager.reserveBook(member, book, new Date()));
    }
    /* -------------------------------------------------------
     * READ
     * -------------------------------------------------------*/


    //TODO not finished
    @Test
    public void getAllCurentReservation() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Member member = new Member("M001", "Dupont", "Jean", "jean.dupont@email.com", "12/03/1990", "M.");
        Member member2 = new Member("M002", "Dupont", "Jean", "jean.dupont@email.com", "12/03/1990", "M.");
        Member member3 = new Member("M003", "Dupont", "Jean", "jean.dupont@email.com", "12/03/1990", "M.");

        Book book1 = new Book("L'Étranger", "Albert Camus", "2070360024", "Gallimard", "Broché", true);
        Book book2 = new Book("1984", "George Orwell", "2070256626", "Secker & Warburg", "Poche", true);
        Book book3 = new Book("Le Petit Prince", "Antoine de Saint-Exupéry", "2070256626", "Reynal & Hitchcock", "Relié", false);

        List<Reservation> reservations = Arrays.asList(
                new Reservation(book1, member, formatter.parse("01/02/2024"), formatter.parse("01/03/2024"), false),
                new Reservation(book2, member, formatter.parse("10/03/2024"), formatter.parse("24/03/2024"), false),
                new Reservation(book3, member, formatter.parse("20/04/2024"), formatter.parse("05/05/2024"), false),
                new Reservation(book3, member, formatter.parse("20/04/2024"), formatter.parse("05/05/2024"), true)
        );



    }


    /* -------------------------------------------------------
     * OTHER
     * -------------------------------------------------------*/

    @Test
    public void GiveBackDateTooLate_ShouldThrowException() {
    }

    @Test
    public void DelayMailIsSent_ShouldInteractWithMailer(){

    }
}