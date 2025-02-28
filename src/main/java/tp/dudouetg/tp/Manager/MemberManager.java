package tp.dudouetg.tp.Manager;

import tp.dudouetg.tp.model.Member;
import tp.dudouetg.tp.model.Reservation;
import tp.dudouetg.tp.services.MemberDataService;

import java.util.ArrayList;
import java.util.List;

public class MemberManager {

    private final MemberDataService memberDataService;

    public MemberManager(MemberDataService memberDataService) {
        this.memberDataService = memberDataService;
    }

    public Member updateMemberInfo(Member member, String field, String value) {
        return memberDataService.updateMember(member, field, value);
    }

    public Member createMember(Member newMember) {
        return memberDataService.saveMember(newMember);
    }


    public Member getMember(String code){
        return memberDataService.getMember(code);
    }

    public List<Reservation> getReservationList(String code) {
        return new ArrayList<Reservation>();
    }

    public void deleteMember(String number) {
        memberDataService.removeMember(number);
    }
}
