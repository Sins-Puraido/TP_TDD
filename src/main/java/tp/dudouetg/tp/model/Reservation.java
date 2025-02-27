package tp.dudouetg.tp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation extends PersistentEntity {

    @ManyToOne
    private Book book;

    @ManyToOne
    private Member member;

    private Date startDate;

    private Date EndDate;

}
