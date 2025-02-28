package tp.dudouetg.tp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "member")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends PersistentEntity {

    private String code;

    private String name;

    private String firstname;

    private String email;

    private Date birthdate;

    private String civility;


    public Member(String code, String name, String firstname, String email, String stringBirthdate, String civility) {
        this.code = code;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.civility = civility;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.birthdate = formatter.parse(stringBirthdate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Format de date invalide : " + stringBirthdate);
        }
    }
}
