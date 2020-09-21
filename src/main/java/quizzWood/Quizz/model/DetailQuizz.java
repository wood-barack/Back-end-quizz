package quizzWood.Quizz.model;

import lombok.Data;
import quizzWood.Quizz.model.Quizz;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DetailQuizz implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    private int time;

    private int point;

    private String premierChoix;

    private String deuxiemeChoix;

    private String troisiemeChoix;

    private String quatriemeChoix;

    private String reponse;

    @ManyToOne
    private Quizz quizz;

    public Long getId(){ return id; }

    public String getQuestion(){ return question;}

    public int getTime(){return time;}

    public int getPoint(){return point;}

    public String getPremierChoix(){return premierChoix;}

    public String getDeuxiemeChoix(){return deuxiemeChoix;}

    public String getTroisiemeChoix(){return troisiemeChoix;}

    public String getQuatriemeChoix(){return quatriemeChoix;}


    public Quizz getQuizz(){return quizz;}

    public void setQuestion(String question){this.question = question; }

    public void setTime(int time){this.time = time;}

    public void setPoint(int point){this.point = point;}

    public void setPremierChoix(String premierChoix){this.premierChoix = premierChoix;}

    public void setDeuxiemeChoix(String deuxiemeChoix){this.deuxiemeChoix = deuxiemeChoix;}

    public void setTroisiemeChoix(String troisiemeChoix){this.troisiemeChoix = troisiemeChoix;}

    public void setQuatriemeChoix(String quatriemeChoix){this.quatriemeChoix = quatriemeChoix;}

    public void setResponse(String reponse){this.reponse = reponse;}

    public void setQuizz(Quizz quizz){this.quizz = quizz;}
}
