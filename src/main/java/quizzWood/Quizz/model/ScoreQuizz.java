package quizzWood.Quizz.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ScoreQuizz implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int score;

    private int tempsMoyenReponse;

    private int nbBonneReponse;

    private int nbMauvaiseReponse;

    private int nbquestion;

    @ManyToOne
    private Quizz quizz;

    @ManyToOne
    private User user;

    public Quizz getQuizz(Quizz idQuizz) {
        return idQuizz;
    }
}
