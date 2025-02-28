package tp.dudouetg.tp.services;

import tp.dudouetg.tp.model.Book;
import tp.dudouetg.tp.model.Member;
import tp.dudouetg.tp.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public interface MemberDataService {


    default Member saveMember(Member newMember){
        return newMember;
    }

    default Member getMember(String code){
        return new Member();
    };

    default boolean removeMember(String code){
        return true;
    }

    default Member updateMember(Member member, String fields, String value) {
        return member;
    }

    default List<Reservation> getMemberReservation(String code){
        return new ArrayList<Reservation>();
    };

    default List<Reservation> getMemberCurrentReservation(String code){
        return new ArrayList<Reservation>();
    };
}

