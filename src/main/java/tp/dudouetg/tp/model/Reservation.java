package tp.dudouetg.tp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends PersistentEntity {

    @ManyToOne
    private Book book;

    @ManyToOne
    private Member member;

    private Date startDate;

    private Date EndDate;

}
