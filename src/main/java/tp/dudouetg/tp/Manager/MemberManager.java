package tp.dudouetg.tp.Manager;

import tp.dudouetg.tp.exception.*;
import tp.dudouetg.tp.model.Book;
import tp.dudouetg.tp.model.Member;
import tp.dudouetg.tp.model.Reservation;
import tp.dudouetg.tp.services.MemberDataService;
import tp.dudouetg.tp.services.ReservationDataService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class MemberManager {

    private final MemberDataService memberDataService;

    private final ReservationDataService reservationDataService;

    public MemberManager(MemberDataService memberDataService, ReservationDataService reservationDataService) {
        this.memberDataService = memberDataService;
        this.reservationDataService = reservationDataService;
    }

    public Member updateMemberInfo(Member member, String field, String value) {
        if(isValidfield(field, Member.class)){
            if(Objects.equals(field, "email")){
                if(checkEmail(value)){
                    throw new InvalidMemberDataException("Invalid mail");
                }
            }
            return memberDataService.updateMember(member, field, value);
        }
        else throw new InvalidFieldNameException();
    }

    public Member createMember(Member newMember) {

        if(!getMissingFields(newMember).isEmpty()){
            throw new InvalidMemberDataException("Book entry is missing data for :" + getMissingFields(newMember).toString());
        }
        if(newMember.getBirthdate().after(new Date())){
            throw new InvalidMemberDataException("Invalide Birthdate");
        }
        if(!checkEmail(newMember.getEmail())){
            throw new InvalidMailSyntaxe();
        }
        memberDataService.saveMember(newMember);




        return memberDataService.saveMember(newMember);
    }


    public Member getMember(String code){
        return memberDataService.getMember(code);
    }

    public List<Reservation> getReservationList(String code) {
        return reservationDataService.getReservationFromMember(code);
    }


    public void deleteMember(String number) {
        memberDataService.removeMember(number);
    }

    public boolean checkEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regex
        Pattern p = Pattern.compile(emailRegex);

        // Check if email matches the pattern
        return email != null && p.matcher(email).matches();
    }

    private boolean isValidfield(String fieldname, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldname)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getMissingFields(Member member) {
        List<String> missingFields = new ArrayList<>();

        if (member.getName() == null || member.getName().isEmpty()) {
            missingFields.add("name");
        }

        if (member.getBirthdate() == null) {
            missingFields.add("Birthdate");
        }
        if (member.getFirstname() == null || member.getFirstname().isEmpty()) {
            missingFields.add("Firstname");
        }
        if (member.getEmail() == null || member.getEmail().isEmpty()) {
            missingFields.add("mail");
        }
        if (member.getCivility() == null) {
            missingFields.add("civility");
        }

        return missingFields;

    }



    }
