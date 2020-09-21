package quizzWood.Quizz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import quizzWood.Quizz.model.*;
import quizzWood.Quizz.model.list.ResponseQuizz;
import quizzWood.Quizz.repository.DetailQuizzRepository;
import quizzWood.Quizz.repository.RoomRepository;
import quizzWood.Quizz.repository.ScoreQuizzRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.*;


/**
 * Controller LanceQuizzController
 * Permet de créer un liens pour le quizz
 * Permet de lancer un quizz
 * retourne une liste des quizz
 * Retourne Parametre de la room
 * Permet de valider les question du quizz
 */
@RestController
@RequestMapping("/LanceQuizz")
public class LanceQuizzController extends JdbcDaoSupport {

    @Autowired
    private DetailQuizzRepository repositorie;

    @Autowired
    private RoomRepository repositorieRoom;

    @Autowired
    private ScoreQuizzRepository repositorieScoreQuizz;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }

    /**
     * Permet de selectionnez id du quizz
     * @param id du quizz
     * @return idQuizz = identifiant du quizz
     */
    public Long Quizz(Long id) {
        String req = "SELECT id FROM quizz WHERE id = ?";
        Long idQuizz = getJdbcTemplate().queryForObject(req, Long.class, id);

        return idQuizz;
    }

    /**
     * retourne la classe Quizz grace a son Id
     * @param id du quizz
     * @return la classe du quizz
     */
    public Quizz QuizzModel(Long id){
        String req = "SELECT * FROM quizz WHERE id = ?";
        Quizz quizzModel = (Quizz) getJdbcTemplate().queryForObject(req, new BeanPropertyRowMapper(Quizz.class), id);
        return quizzModel;
    }

    /**
     * Permet d'inserer les score dans la table score
     * @param score score au quizz
     * @param tempsMoyenReponse temps moyenne de reponse au question
     * @param nbBonneReponse nombre de bonne reponse
     * @param quizz identifiant du quizz
     * @param user utilisateur qui a particper au quizz
     * @param  nbMauvaiseReponse mauvaise reponse au quizz
     * @param nbquestion nombre de question dans le quizz
     * @return les information inserer
     */
    public int insertScoreQuiz(int score, int tempsMoyenReponse, int nbBonneReponse, int nbMauvaiseReponse, int nbquestion, Long quizz, Long user){
        String req = "INSERT INTO score_quizz (nb_bonne_reponse, nb_mauvaise_reponse, nbquestion, score, temps_moyen_reponse, quizz_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int scoreQuizz = getJdbcTemplate().update(req,new Object[]{nbBonneReponse,nbMauvaiseReponse,nbquestion, score, tempsMoyenReponse, quizz, user} );
        return scoreQuizz;
    }

    /**
     * Permet de mettre a jours le score des quizz
     * @param score score au quizz
     * @param tempsMoyenReponse temps moyenne de reponse au question
     * @param nbBonneReponse nombre de bonne reponse
     * @param quizz identifiant du quizz
     * @param user utilisateur qui a particper au quizz
     * @param  nbMauvaiseReponse mauvaise reponse au quizz
     * @param nbquestion nombre de question dans le quizz
     * @param idScore id unique du score
     * @return les information update
     */
    public int UpdateScoreQuizz(int score, int tempsMoyenReponse, int nbBonneReponse,int nbMauvaiseReponse, int nbquestion, Long quizz, Long user, Long idScore){
        String req = "UPDATE score_quizz SET nb_bonne_reponse = ?, score = ?, temps_moyen_reponse = ?, quizz_id = ?, user_id = ?,nb_mauvaise_reponse = ?,nbquestion = ?   WHERE score_quizz.id = ?;";
        int scoreQuizz = getJdbcTemplate().update(req,new Object[]{nbBonneReponse, score, tempsMoyenReponse, quizz, user,nbMauvaiseReponse,nbquestion, idScore} );
        return scoreQuizz;
    }

    /**
     * Retourne le numeros de la room pour lancer le quizz
     * @param quizz identifiant du quizz
     * @return la room
     */
    public int room(Long quizz) {
        String req = "SELECT number_room FROM room WHERE quizz_id = ?";
        int room = getJdbcTemplate().queryForObject(req, int.class, quizz);

        return room;
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

    /**
     * Creation d'un lien pour lancer le quizz
     * @param room classe de la room
     * @param idQuizz identifiant du quizz
     * @return la classe room
     */
    @PostMapping("/NewLien/{idQuizz}")
    public ResponseEntity lienQuizz(@RequestBody Room room, @PathVariable(name = "idQuizz") Long idQuizz){
        Room createRoomModel;
        Quizz id = QuizzModel(idQuizz);
        Random random = new Random();
        int nb;
        nb = random.nextInt(100000);
        System.out.println("Le ramdom est : "+nb);
        //si la room est egal a null
        if(room == null){
            return ResponseEntity.badRequest().body("Impossible de creer un nouveau quizz");
        }else{
            Room Quizz = repositorieRoom.findAllByQuizzId(idQuizz);
            //si le Quizz est different de null il insert les données sinon il les update
            if(Quizz != null){
                //update les données dans la BDD
                Quizz.setDateCreation(new java.util.Date());
                Quizz.setNumberRoom(nb);
                Quizz.setQuizz(id);
                Quizz.setLien("https://barackquizz.herokuapp.com/StartQuizz/"+idQuizz+"/"+nb);
                createRoomModel = repositorieRoom.save(Quizz);
                System.out.println("tes dans le Update !!!!!!!!!!!!!!!!!!");
            }else{
                //insert les données dans la BDD
                room.setDateCreation(new java.util.Date());
                room.setNumberRoom(nb);
                room.setQuizz(id);
                room.setLien("https://barackquizz.herokuapp.com/StartQuizz/"+idQuizz+"/"+nb);
                System.out.println("tes dans le insert !!!!!!!!!!!!!!!!!!");
                createRoomModel = repositorieRoom.save(room);
            }

        }
        //return les données mis a jour ou insert
        return ResponseEntity.ok(createRoomModel);
    }

    /**
     * Envoie toute les question du quizz
     * @param idQuizz identifiant du quizz
     * @param room numeros de la room
     * @return toute les question du quizz
     */
    @GetMapping("/Quizz/{idQuizz}/{room}")
    public ResponseEntity lanceQuizz(@PathVariable(name = "idQuizz") Long idQuizz, @PathVariable(name = "room") int room){
        Long id = Quizz(idQuizz);
        room = room(idQuizz);
        System.out.println(room);
        if(id == null){
            return ResponseEntity.badRequest().body("Aucun quizz trouver");
        }else{
            Optional<List<DetailQuizz>> detailQuizz = Optional.ofNullable(repositorie.findAllByQuizzId(id));
            if(detailQuizz == null){
                return ResponseEntity.notFound().build();
            }
            //if(detailQuizz.isEmpty()){
                //return ResponseEntity.notFound().build();
            //}
            return ResponseEntity.ok().body(detailQuizz);
        }
    }

    /**
     * Liste tout les room ( Quizz )
     * @return liste des room
     */
    @GetMapping("/ListeRoom")
    public ResponseEntity listeRoom(){
        Optional<List<Room>> room = Optional.ofNullable(repositorieRoom.findAll());
        if(room == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(room);
    }

    /**
     * retourne la classe Room
     * @param numberRoom numeros de la room
     * @return la room
     */
    @GetMapping("/MyLink/{numberRoom}")
    public ResponseEntity myLink(@PathVariable(name = "numberRoom") int numberRoom){
        Optional<Room> room = Optional.ofNullable(repositorieRoom.findAllByNumberRoom(numberRoom));
        if(room == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(room);

    }

    /**
     * retourne la room
     * @param idQuizz identifiant du quizz
     * @return la room en fonction de son identifiant du quizz
     */
    @GetMapping("/MyRoom/{idQuizz}")
    public ResponseEntity myRoom(@PathVariable(name = "idQuizz") Long idQuizz){
        Optional<Room> room = Optional.ofNullable(repositorieRoom.findAllByQuizzId(idQuizz));
        if(room == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(room);

    }

    /**
     *
     * @param reponseQuestion reponse des question
     * @param id identifiant du quizz
     * @param currentUser utilisateur connecter
     * @return la classe ScoreQuizz
     */
    @PostMapping("/validQuestiontest/{id}")
    public ResponseEntity ValidQuizz(@RequestBody ResponseQuizz reponseQuestion, @PathVariable(name = "id") Long id, @AuthenticationPrincipal String  currentUser) {

        int totalScore = 0;
        int nbBonneReponse = 0;
        int tempsReponse = 0;
        int nbMauvaiseReponse = 0;
        int nbQuestion = 0;

        //Pour tout les question presente dans laliste
        for(int i =0; i < reponseQuestion.getReponseQuizz().size(); i++){
            Optional<DetailQuizz> question = repositorie.findById(reponseQuestion.getReponseQuizz().get(i).getIdQuestion());
            String ResponseServer = question.map(DetailQuizz::getReponse).get();
            int scoreQuestion = question.map(DetailQuizz::getPoint).get();
            //si les reponse envoyer corresponde au response stocker
            if(ResponseServer.equals(reponseQuestion.getReponseQuizz().get(i).getReponse())){
                //Ajoute le score - temps de reponse x 4
                totalScore += scoreQuestion - reponseQuestion.getReponseQuizz().get(i).getTempsReponse() * 4;
                //compte le nombre de bonne reponse
                nbBonneReponse += 1;
                //Calcul le temps de reponse
                tempsReponse += reponseQuestion.getReponseQuizz().get(i).getTempsReponse();

            }else{
                //on ajoute juste le temps de reponse
                tempsReponse += reponseQuestion.getReponseQuizz().get(i).getTempsReponse();
                //Compte le nombre de mauvaise Reponse
                nbMauvaiseReponse += 1;
            }
            //compte le nombre de question
            nbQuestion = reponseQuestion.getReponseQuizz().size();
        }

        int tempsMoyenneReponse = tempsReponse / reponseQuestion.getReponseQuizz().size();
        Long idUser = IdUser(currentUser);
        User tesUser = UserAuth(currentUser);
        ScoreQuizz scoreQuizz = repositorieScoreQuizz.findAllByQuizzIdAndUser(id, tesUser);
        //Si le score du quizz n'a jamais été inserer dans la BDD
        if(scoreQuizz != null){
            //met a jour les données si elle existe
            UpdateScoreQuizz(totalScore, tempsMoyenneReponse, nbBonneReponse,nbMauvaiseReponse,nbQuestion, id, idUser, scoreQuizz.getId());
            //Retourne sous format json les données update
            return ResponseEntity.ok().body("{\""+"id" + "\":" + scoreQuizz.getId() + ","+"\""+ "score" + "\":" + totalScore + ","+"\""+ "nbMauvaiseReponse" + "\":" + nbMauvaiseReponse + ","+"\""+ "nbQuestion" + "\":" + nbQuestion + ","+"\""+ "tempsMoyenReponse"+"\":" + tempsMoyenneReponse+","+"\""+ "nbBonneReponse"+"\":" + nbBonneReponse+","+"\""+ "quizz"+"\":" + id+","+"\""+ "user"+"\":" + idUser+ "}");
        }else{
            //Insert le score dans la BDD
            insertScoreQuiz(totalScore, tempsMoyenneReponse, nbBonneReponse,nbMauvaiseReponse,nbQuestion, id, idUser);
            //Retourne sous format json les données inserer
            return ResponseEntity.ok().body("{\"" + "score" + "\":" + totalScore + ","+"\""+ "nbMauvaiseReponse" + "\":" + nbMauvaiseReponse + ","+"\""+ "nbQuestion" + "\":" + nbQuestion + ","+"\""+ "tempsMoyenReponse"+"\":" + tempsMoyenneReponse+","+"\""+ "nbBonneReponse"+"\":" + nbBonneReponse+","+"\""+ "quizz"+"\":" + id+","+"\""+ "user"+"\":" + idUser+ "}");
        }
    }
}
