package quizzWood.Quizz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import quizzWood.Quizz.model.DetailQuizz;
import quizzWood.Quizz.model.Quizz;
import quizzWood.Quizz.repository.DetailQuizzRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/DetailQuizz")
public class DetailQuizzController extends JdbcDaoSupport {

    @Autowired
    private DetailQuizzRepository repositorie;

    @Autowired
    private DataSource dataSource;


    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    /**
     * permet de renvoyer id du quizz
     * @param id du quizz
     * @return identifiant du quizz
     */
    public Long Quizz(Long id) {
        String req = "SELECT id FROM quizz WHERE id = ?";
        Long idQuizz = getJdbcTemplate().queryForObject(req, Long.class, id);

        return idQuizz;
    }

    /**
     * Retourne une classse du qizz
     * @param id identifiant du quizz
     * @return la classe quizz
     */
    public Quizz QuizzModel(Long id){
        String req = "SELECT * FROM quizz WHERE id = ?";
        Quizz quizzModel = (Quizz) getJdbcTemplate().queryForObject(req, new BeanPropertyRowMapper(Quizz.class), id);
        return quizzModel;
    }

    /**
     * renvoie toute les question du quizz
     * @param idQuizz identifiant du quizz
     * @return toute les question du quizz
     */
    @GetMapping("/listeQuestion/{idQuizz}")
    public ResponseEntity findDetailQuizzById(@PathVariable(name = "idQuizz") Long idQuizz){
        Long id = Quizz(idQuizz);
        //si id est egal a null alors on affiche un message sinon on retourne la liste
        if(id == null){
            return ResponseEntity.badRequest().body("Aucun utilisateur trouver");
        }else{
            Optional<List<DetailQuizz>> detailQuizz = Optional.ofNullable(repositorie.findAllByQuizzId(id));
            //si la variable est egal a null alors on envoie un message
            if(detailQuizz == null){
                return ResponseEntity.notFound().build();
            }
            //if(detailQuizz.isEmpty()){
               //return ResponseEntity.notFound().build();
            //}
            //retourne la liste des question
            return ResponseEntity.ok().body(detailQuizz);
        }
    }

    /**
     * retourne une question de l'utilisateur
     * @param id identifiant de la question
     * @return une question de l'utilisateur
     */
    @GetMapping("/question/{id}")
    public ResponseEntity findById(@PathVariable(name = "id") Long id){
        //si id est egal a null alors on affiche un message
        if(id == null){
            return ResponseEntity.badRequest().body("Aucun utilisateur trouver");
        }else{
            Optional<DetailQuizz> question = repositorie.findById(id);
            //si la question est egal a null alors on affiche un message
            if(question == null){
                return ResponseEntity.notFound().build();
            }
            //si la variable est vide on affiche un message
            //if(question.isEmpty()){
                //return ResponseEntity.notFound().build();
            //}
            //retourne la question
            return ResponseEntity.ok().body(question);
        }
    }

    /**
     * permet de créer une question dans le quizz
     * @param detailQuizz classe de la question
     * @param quizzId identifiant du quizz
     * @return la question enregistrer dans la bdd
     */
    @PostMapping("/nouvelleQuestion/{quizzId}")
    public ResponseEntity createQuestion(@RequestBody DetailQuizz detailQuizz, @PathVariable(name = "quizzId") Long quizzId){
        Quizz id = QuizzModel(quizzId);
        DetailQuizz details;
        //si la variable detailQuiz est égal a null
        if(detailQuizz == null){
            return ResponseEntity.badRequest().body("Impossible de creer un nouveau quizz");
        }else{
            //specifie l'id du quizz
            detailQuizz.setQuizz(id);
            //save les information dans la BDD
            details = repositorie.save(detailQuizz);
        }
        //retourne la question créer
        return ResponseEntity.ok(details);
    }

    /**
     * modifier et mettre a jours la question
     * @param id identifiant de la question
     * @param requestQuestion données a envoyer a la bdd
     * @return les question mise a jours
     */
    @PutMapping("/nouvelleQuestionModif/{id}")
    public ResponseEntity<DetailQuizz> updateQuestion(@PathVariable("id") long id, @RequestBody DetailQuizz requestQuestion) {
        Optional<DetailQuizz> detailQuizz = repositorie.findById(id);

        //Si la question a modifier est bien remplie
        if (detailQuizz.isPresent()) {
            DetailQuizz detailQuizz1 = detailQuizz.get();
            detailQuizz1.setQuestion(requestQuestion.getQuestion());
            detailQuizz1.setTime(requestQuestion.getTime());
            detailQuizz1.setPoint(requestQuestion.getPoint());
            detailQuizz1.setPremierChoix(requestQuestion.getPremierChoix());
            detailQuizz1.setDeuxiemeChoix(requestQuestion.getDeuxiemeChoix());
            detailQuizz1.setTroisiemeChoix(requestQuestion.getTroisiemeChoix());
            detailQuizz1.setQuatriemeChoix(requestQuestion.getQuatriemeChoix());
            detailQuizz1.setReponse(requestQuestion.getReponse());
            //Retourne la question save
            return new ResponseEntity<>(repositorie.save(detailQuizz1), HttpStatus.OK);
        } else {
            //retourne not found si on toruve rien
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
