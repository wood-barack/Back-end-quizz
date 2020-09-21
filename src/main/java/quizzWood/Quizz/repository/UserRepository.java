package quizzWood.Quizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quizzWood.Quizz.model.User;

/**
 * Repository du model User
 */
public interface UserRepository  extends JpaRepository<User, Long> {

    /**
     * @param email email a trouver
     * @return l'utilisateur
     */
    User findByEmail(String email);
}
