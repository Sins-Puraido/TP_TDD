package tp.dudouetg.tp.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book extends PersistentEntity {

    private String title;

    private String author;

    private String isbn;

    private String publisher;

    private String format;

    private Boolean available;
}
