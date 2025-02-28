package tp.dudouetg.tp.Manager;

import tp.dudouetg.tp.model.Book;
import tp.dudouetg.tp.model.Member;
import tp.dudouetg.tp.model.Reservation;
import tp.dudouetg.tp.services.BookDataService;
import tp.dudouetg.tp.services.MailerService;
import tp.dudouetg.tp.services.MemberDataService;
import tp.dudouetg.tp.services.ReservationDataService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationManager {
    private final ReservationDataService reservationDataService;
    private final MailerService emailService;
    private final BookDataService bookDataService;
    private final MemberDataService memberDataService;
    private static final int MAX_RESERVATIONS = 3;
    private static final int MAX_RESERVATION_DURATION_DAYS = 120;

    public ReservationManager(ReservationDataService reservationDataService, MemberDataService memberDataService, BookDataService bookDataService, MailerService emailService) {
        this.reservationDataService = reservationDataService;
        this.emailService = emailService;
        this.bookDataService = bookDataService;
        this.memberDataService = memberDataService;
    }

    public Reservation reserveBook(Member member, Book book, Date startDate) {
        if (memberHasTooManyReservations(member)) {
            throw new IllegalStateException("Member has reached the reservation limit.");
        }
        if (bookIsAlreadyReserved(book)) {
            throw new IllegalStateException("Book is already reserved.");
        }

        Date endDate = new Date(startDate.getTime() + (MAX_RESERVATION_DURATION_DAYS * 24L * 60 * 60 * 1000));
        Reservation reservation = new Reservation(book, member, startDate, endDate, true);
        return reservationDataService.save(reservation);
    }

    public boolean cancelReservation(Reservation reservation) {
        if (reservation == null || !reservation.isStatus()) {
            return false;
        }
        reservation.setStatus(false);
        reservationDataService.save(reservation);
        return true;
    }

    public boolean bookIsAlreadyReserved(Book book) {
        return bookDataService.isBookAvailable(book.getIsbn());
    }

    public boolean memberHasTooManyReservations(Member member) {
        return memberDataService.getMemberCurrentReservation(member.getCode()).size() >= MAX_RESERVATIONS;
    }

    //TODO not finished
    public void sendReminderEmails() {
        List<Reservation> overdueReservations = reservationDataService.findOverdueReservations();
        List<Member> membersToNotify = overdueReservations.stream()
                .map(Reservation::getMember)
                .distinct()
                .toList();

        for (Member member : membersToNotify) {
            List<Reservation> memberReservations = overdueReservations.stream()
                    .filter(res -> res.getMember().equals(member))
                    .collect(Collectors.toList());
            emailService.sendOverdueNotification(member, memberReservations);
        }
    }
}
