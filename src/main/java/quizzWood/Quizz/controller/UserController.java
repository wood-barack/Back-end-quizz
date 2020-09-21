package quizzWood.Quizz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import quizzWood.Quizz.model.User;
import quizzWood.Quizz.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Optional;

/**
 * Controller User :
 * Retourne la liste des utilisateur
 * Retourne profile des utilisateur
 * Creation d'un compte utilisateur
 */
@RestController
@RequestMapping("/users")
public class UserController extends JdbcDaoSupport {

    /**
     * repositorie de l'utilisateur
     */
    @Autowired
    private UserRepository repositorie;

    /**
     * cryptage pour le mot de passe
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Datasource pour la BDD
     */
    @Autowired
    private DataSource dataSource;


    /**
     * initialisation de datasource
     */
    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
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
     * @param userRepositorie repositorie du model User
     * @param bCryptPasswordEncoder methode de cryptage du mot de passe
     * Constructeur du controller user
     */
    public UserController(UserRepository userRepositorie, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repositorie = userRepositorie;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * @return la liste des utilisateur
     */
    @GetMapping("/listUser")
    public ResponseEntity findAll(){
        return ResponseEntity.ok(repositorie.findAll());
    }

    /**
     * @param currentUser email de l'utilisateur connecter
     * @return les information du profile de l'utilisateur
     */
    @GetMapping("/profile")
    public ResponseEntity findUserById(@AuthenticationPrincipal String  currentUser){
        // recuperation de id de l'utilisateur
        Long idUser = IdUser(currentUser);
        if(idUser == null){
            // en cas d'erreur on retourne le message suivant
            return ResponseEntity.badRequest().body("Aucun utilisateur trouver");
        }else{
            //recherche en fonction de l'ID de l'utilisateur
            Optional<User> user = repositorie.findById(idUser);
            if(user == null){
                // si le modele est vide
                return ResponseEntity.notFound().build();
            }
            //retourne le resultat
            return ResponseEntity.ok().body(user);
        }
    }

    /**
     * @param userModel pour passer en parametre les information attendu pour la creation d'un utilisateur
     * @return les informations saisie pour les enregistrer
     */
    @PostMapping("/createUser")
    public ResponseEntity createUser(@RequestBody User userModel){
        User createdUserModel;
        //verifie que l'email n'existe pas dans la BDD
        User userExists = repositorie.findByEmail(userModel.getEmail());
        if(userModel == null){
            // si une erreur arrive
            return ResponseEntity.badRequest().body("Impossible de creer un utilisateur");
        }else{
            if(userExists != null){
                return ResponseEntity.badRequest().body("Email existant");
            }
            //crypte le mot de passe
            userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
            //insere le nouvelle utilisateur
            createdUserModel = repositorie.save(userModel);
        }
        //retourne le resultat
        return ResponseEntity.ok(createdUserModel);
    }
}
