package tp.dudouetg.tp.services;

import tp.dudouetg.tp.model.Reservation;

import java.util.List;

public interface ReservationDataService {


    List<Reservation> getReservationFromMember(String code);
}
