package quizzWood.Quizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import quizzWood.Quizz.model.ScoreQuizz;
import quizzWood.Quizz.model.User;

import java.util.List;

/**
 * Repository du model ScoreQuizz
 */
public interface ScoreQuizzRepository extends JpaRepository<ScoreQuizz, Long> {

    /**
     * Trouve le score en fonction de l'identifiant du quizz et identifiant de l'utilisateur
     * @param idQuizz identifiant du quizz
     * @param idUser identifiant de l'utilisateur
     * @return le score de l'utilisateur en fonction du quizz
     */
    ScoreQuizz findAllByQuizzIdAndUser(@Param("idQuizz") Long idQuizz, @Param("idUser") User idUser);

    /**
     * permet d'afficher tout les quizz auquel l'utilisateur a participer
     * @param idUser identifiant de l'utilisateur connecter
     * @return les scores de l'utilisateur
     */
    List<ScoreQuizz>  findAllByUser(@Param("idUser") User idUser);
}
