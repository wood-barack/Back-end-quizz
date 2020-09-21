package quizzWood.Quizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import quizzWood.Quizz.model.Quizz;

import java.util.List;

/**
 * Repository du model Quizz
 */
public interface QuizzRepository extends JpaRepository<Quizz, Long> {

    //

    /**
     * Liste des Quizz en fonction de identifiant de l'utilisateur
     * @param id identifiant du quizz
     * @return la liste des quizz en focntion de l'utilisateur
     */
    List<Quizz> findAllByUserId(@Param("id") Long id);

}
