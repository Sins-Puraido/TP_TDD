package tp.dudouetg.tp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

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

    private Date birthdate;

    private String civility;


}
