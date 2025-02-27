package tp.dudouetg.tp.Manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tp.dudouetg.tp.services.MemberDataService;

import static org.mockito.Mockito.mock;

public class MemberManagerTest {

    private MemberDataService fakeDatabaseService;
    private MemberDataService fakeWebService;

    @BeforeEach
    public void setUp() {
        fakeDatabaseService = mock(MemberDataService.class);
        fakeWebService = mock(MemberDataService.class);
    }

    /* -------------------------------------------------------
     * CREATE
     * -------------------------------------------------------*/
    @Test
    public void MemberCreationSuceed_shoudReturnSameMember(){
    };

    @Test
    public void MemberCreationFail_ShouldReturnInvalidMemberDataExcpetion(){

    }

    /* -------------------------------------------------------
     * READ
     * -------------------------------------------------------*/
    @Test
    public void GivenMemberIsInDB_ShouldReturnMember() {
    }

    @Test
    public void GivenMemberIsNotInDB_ShouldReturnMember() {
    }

    @Test
    public void GetMemberReservation_shouldReturnReservations() {}

    /* -------------------------------------------------------
     * UPDATE
     * -------------------------------------------------------*/
    @Test
    public void MemberModificationIsValid_ShouldReturnModifiedMember() {
    }

    @Test
    public void MemberModificationIsInvalid_ShouldReturnFalse() {
    }

    /* -------------------------------------------------------
     * DELETE
     * -------------------------------------------------------*/
    @Test
    public void MemberDeletionIsValid_ShouldReturnTrue() {
    }

    @Test
    public void MemberDeletionIsInvalid_ShouldReturnFalse() {
    }

    /* -------------------------------------------------------
     * OTHER
     * -------------------------------------------------------*/





}
