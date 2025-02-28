package tp.dudouetg.tp.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tp.dudouetg.tp.exception.InvalidBookFormatException;
import tp.dudouetg.tp.exception.InvalidMailSyntaxe;
import tp.dudouetg.tp.exception.InvalidMemberDataException;
import tp.dudouetg.tp.exception.IsdbNotInDBException;
import tp.dudouetg.tp.model.Book;
import tp.dudouetg.tp.model.Member;
import tp.dudouetg.tp.model.Reservation;
import tp.dudouetg.tp.services.MemberDataService;
import tp.dudouetg.tp.services.ReservationDataService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemberManagerTest {

    private MemberDataService fakeDatabaseService;
    private ReservationDataService fakeReservationDataService;
    private MemberManager memberManager;

    @BeforeEach
    public void setUp() {
        fakeDatabaseService = mock(MemberDataService.class);
        fakeReservationDataService = mock(ReservationDataService.class);
        memberManager = new MemberManager(fakeDatabaseService, fakeReservationDataService);
    }

    /* -------------------------------------------------------
     * CREATE
     * -------------------------------------------------------*/
    @Test
    public void MemberCreationSuceed_shoudReturnSameMember() {
        Member newMember = new Member("031245","john", "doe", "john.doe@example.com", "08/07/2002", "1");
        when(fakeDatabaseService.saveMember(newMember)).thenReturn(newMember);

        Member result = memberManager.createMember(newMember);

        assertNotNull(result);
        assertEquals(newMember, result);
    }


    @Test
    public void MemberCreationFail_ShouldReturnExcpetion(){
        Member invalidMember = new Member("031245","john", "", "", "08/07/2002", "1");
        when(fakeDatabaseService.saveMember(invalidMember)).thenThrow(InvalidMemberDataException.class);

        assertThrows(InvalidMemberDataException.class, () -> memberManager.createMember(invalidMember));
    }

    @Test
    public void MemberBirthDateInvalide_ShouldReturnException(){
        Member invalidMember = new Member("031245","john", "doe", "john.doe@example.com", "08/07/2032", "1");

        assertThrows(InvalidMemberDataException.class, () -> memberManager.createMember(invalidMember));
    }

    @Test
    public void MemberInvalideEmailFormat_ShouldReturnExcpetion(){
        Member invalidMember = new Member("031245","john", "doe", "john.doeexample.com", "08/07/2002", "1");

        assertThrows(InvalidMailSyntaxe.class, () -> memberManager.createMember(invalidMember));
    }

    /* -------------------------------------------------------
     * READ
     * -------------------------------------------------------*/
    @Test
    public void GivenMemberIsInDB_ShouldReturnMember() {
        Member member = new Member("031245","john", "doe", "john.doe@example.com", "08/07/2002", "1");
        when(fakeDatabaseService.getMember("031245")).thenReturn(member);

        assertEquals(memberManager.getMember(member.getCode()), member);
    }

    @Test
    public void GivenMemberIsNotInDB_ShouldReturnFalse() {
        Member member = new Member("031245","john", "doe", "john.doe@example.com", "08/07/2002", "1");
        when(fakeDatabaseService.getMember("031245")).thenReturn(null);

        assertNull(memberManager.getMember(member.getCode()));
    }

    @Test
    public void GetMemberReservations_shouldReturnReservationList() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Member member = new Member("M001", "Dupont", "Jean", "jean.dupont@email.com", "12/03/1990", "M.");

        Book book1 = new Book("L'Étranger", "Albert Camus", "2070360024", "Gallimard", "Broché", true);
        Book book2 = new Book("1984", "George Orwell", "2070256626", "Secker & Warburg", "Poche", true);
        Book book3 = new Book("Le Petit Prince", "Antoine de Saint-Exupéry", "2070256626", "Reynal & Hitchcock", "Relié", false);

        List<Reservation> reservations = Arrays.asList(
                new Reservation(book1, member, formatter.parse("01/02/2024"), formatter.parse("01/03/2024"), true),
                new Reservation(book2, member, formatter.parse("10/03/2024"), formatter.parse("24/03/2024"), false),
                new Reservation(book3, member, formatter.parse("20/04/2024"), formatter.parse("05/05/2024"), true)
        );

        when(fakeReservationDataService.getReservationFromMember("M001")).thenReturn(reservations);

        assertEquals(reservations, memberManager.getReservationList(member.getCode()));
    }



    /* -------------------------------------------------------
     * UPDATE
     * -------------------------------------------------------*/
    @Test
    public void MemberModificationIsValid_ShouldReturnModifiedMember() {
        Member member = new Member("031245","john", "doe", "john.doe@example.com", "08/07/2002", "1");
        Member expectedMember = new Member("031245","jack", "doe", "john.doe@example.com", "08/07/2002", "1");
        String newName = "jack";
        when(fakeDatabaseService.updateMember(member, "name", newName)).thenReturn(expectedMember);
        Member updatedMember = memberManager.updateMemberInfo(member, "name", "jack");
        assertEquals(expectedMember.toString(), updatedMember.toString());
    }


    /* -------------------------------------------------------
     * DELETE
     * -------------------------------------------------------*/
    @Test
    public void MemberDeletionIsValid_ShouldReturnTrue() {
        when(fakeDatabaseService.removeMember("2253009687")).thenThrow(IsdbNotInDBException.class);
        assertThrows(IsdbNotInDBException.class, () -> memberManager.deleteMember("2253009687"));
    }

    /* -------------------------------------------------------
     * OTHER
     * -------------------------------------------------------*/





}
