package tp.dudouetg.tp.services;

import tp.dudouetg.tp.model.Book;
import tp.dudouetg.tp.model.Member;
import tp.dudouetg.tp.model.Reservation;

import java.util.Collection;
import java.util.List;

public interface ReservationDataService {


    List<Reservation> getReservationFromMember(String code);


    int countMemberReservations(Member member);

    boolean isBookReserved(Book book);

    default Reservation save(Reservation reservation){
        return reservation;
    };

    List<Reservation> findOverdueReservations();
}
