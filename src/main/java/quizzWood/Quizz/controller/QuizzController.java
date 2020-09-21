package quizzWood.Quizz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import quizzWood.Quizz.model.Quizz;
import quizzWood.Quizz.model.User;
import quizzWood.Quizz.repository.QuizzRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * Controller qui permet de gerer les quizz
 */
@RestController
@RequestMapping("/Quizz")
public class QuizzController extends JdbcDaoSupport {

    @Autowired
    private QuizzRepository repositorie;

    @Autowired
    private DataSource dataSource;


    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    /**
     * @param currentUser email de l'utilisateur a authentifier
     * @return Id de l'utilisateur
     */
    public Long IdUser(@AuthenticationPrincipal String  currentUser){
        String req = "SELECT id FROM user WHERE email = ?";
        Long idUser = getJdbcTemplate().queryForObject(req, Long.class, currentUser);
        return idUser;
    }

    /**
     * retourne la classe User
     * @param currentUser email de l'utilisateur a authentifier
     * @return la classe user
     */
    public User UserAuth(@AuthenticationPrincipal String  currentUser){
        String req = "SELECT * FROM user WHERE email = ?";
        User idUser = (User) getJdbcTemplate().queryForObject(req, new BeanPropertyRowMapper(User.class), currentUser);
        return idUser;
    }

    /**
     * permet de renvoyer les quizz de l'utilisateur
     * @param currentUser utilisateur connecter
     * @return les quizz de l'utilisateur
     */
    @GetMapping("/MyQuizz")
    public ResponseEntity findQuizzById(@AuthenticationPrincipal String  currentUser){
        Long idUser = IdUser(currentUser);
        // si idUser est null renvoyer un message sinon chercher les quizz
        if(idUser == null){
            return ResponseEntity.badRequest().body("Aucun utilisateur trouver");
        }else{
            //Chercher la liste des quizz avec idUser
            Optional<List<Quizz>> quizz = Optional.ofNullable(repositorie.findAllByUserId(idUser));
            //si les quizz est egal a null
            if(quizz == null){
                //renvoie une erreur de not found
                return ResponseEntity.notFound().build();
            }
            //retourne les quizz trouver
            return ResponseEntity.ok().body(quizz);
        }
    }

    /**
     * Permet de creer un nouveau quizz
     * @param quizzModel données a inserer
     * @param currentUser utilisateur connecter
     * @return le quizz inserer dans la BDD
     */
    @PostMapping("/NewQuizz")
        public ResponseEntity createQuizz(@RequestBody Quizz quizzModel, @AuthenticationPrincipal String  currentUser){
        Quizz createdQuizzModel;
        System.out.println(currentUser);
        //Si le model de quizz est vide
        if(quizzModel == null){
            return ResponseEntity.badRequest().body("Impossible de creer un nouveau quizz");
        }else{
            //Insert l'utilisateur qui est connecter
            quizzModel.setUser(UserAuth(currentUser));
            //Insert la date au moment de l'ajout
            quizzModel.setDate(new java.util.Date());
            //permet l'insert du quizz
            createdQuizzModel = repositorie.save(quizzModel);
        }
        //retourne le quizz créer
        return ResponseEntity.ok(createdQuizzModel);
    }
}
