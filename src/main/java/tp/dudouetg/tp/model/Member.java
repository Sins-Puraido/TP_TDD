package tp.dudouetg.tp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member extends PersistentEntity {

    private String code;

    private String name;

    private String firstname;

    private Date birthdate;

    private String civility;


}
