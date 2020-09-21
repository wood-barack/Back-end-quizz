package quizzWood.Quizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import quizzWood.Quizz.model.DetailQuizz;

import java.util.List;

/**
 * Repository du model DetailQuizz
 */
public interface DetailQuizzRepository extends JpaRepository<DetailQuizz, Long> {

    /**
     * Liste des question en fonction de identifiant du quizz
     * @param id identifiant du quizz
     * @return la liste des question en fonction du quizz
     */
    List<DetailQuizz> findAllByQuizzId(@Param("id") Long id);

}
