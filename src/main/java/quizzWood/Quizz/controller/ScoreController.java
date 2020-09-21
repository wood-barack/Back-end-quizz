package quizzWood.Quizz.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quizzWood.Quizz.model.DetailQuizz;
import quizzWood.Quizz.model.ScoreQuizz;
import quizzWood.Quizz.model.User;
import quizzWood.Quizz.repository.ScoreQuizzRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Score")
    public class ScoreController extends JdbcDaoSupport {

    @Autowired
    private ScoreQuizzRepository repositorieScoreQuizz;


    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    /**
     * retourne la classe User
     * @param currentUser email de l'utilisateur a authentifier
     * @return la classe user
     */
    public User UserAuth(@AuthenticationPrincipal String  currentUser){
        String req = "SELECT * FROM user WHERE email = ?";
        User idUser = (User) getJdbcTemplate().queryForObject(req, new BeanPropertyRowMapper(User.class), currentUser);
        //System.out.println(idUser);
        return idUser;
    }

    /**
     * @param currentUser email de l'utilisateur a authentifier
     * @return Id de l'utilisateur
     */
    public Long IdUser(@AuthenticationPrincipal String  currentUser){
        //requete pour selectionner ID de l'utilisateur
        String req = "SELECT id FROM user WHERE email = ?";
        //execution de la requete
        Long idUser = getJdbcTemplate().queryForObject(req, Long.class, currentUser);
        return idUser;
    }



    @GetMapping("/MyScore")
    public ResponseEntity myScore(@AuthenticationPrincipal String  currentUser){
        User iduser = UserAuth(currentUser);
        if(iduser == null){
            return ResponseEntity.badRequest().body("Aucun utilisateur trouver");
        }else{
            Optional<List<ScoreQuizz>> scoreQuizz = Optional.ofNullable(repositorieScoreQuizz.findAllByUser(iduser));
            if(scoreQuizz == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(scoreQuizz);
        }
    }
}
