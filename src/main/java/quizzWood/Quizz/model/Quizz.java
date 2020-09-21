package quizzWood.Quizz.model;

import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Quizz implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date dateCreation;

    private String titre;

    private String description;

    @ManyToOne
    private User user;

    public Long getId(){ return id; }

    public Date getDateCreation(){return dateCreation; }

    public String getTitre(){ return titre; }

    public String getDescription(){ return description; }

    public User getUser() {return user;}

    public void setTitre(String titre){ this.titre = titre;}

    public void setDescription(String description){this.description = description;}

    public void setUser(User user){ this.user = user;}

    public void setDate(Date dateCreation){ this.dateCreation = dateCreation;}
}
