package tp.dudouetg.tp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter
@Setter
public class Reservation extends PersistentEntity {

    @ManyToOne
    private Book book;

    @ManyToOne
    private Member member;

    private Date startDate;

    private Date EndDate;

}
