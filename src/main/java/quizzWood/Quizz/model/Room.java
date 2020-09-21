package quizzWood.Quizz.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Room implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int numberRoom;

    private Date dateCreation;

    private String lien;

    @ManyToOne
    private Quizz quizz;
}
