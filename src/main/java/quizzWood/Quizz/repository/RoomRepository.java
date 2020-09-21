package quizzWood.Quizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import quizzWood.Quizz.model.Room;


/**
 * Repository du model Room
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * Trouve toute les Room avec l'identifiant du quizz
     * @param id identifiant du quizz
     * @return les Room du quizz
     */
    Room findAllByQuizzId(@Param("id") Long id);

    /**
     * Trouve toute les room en fonction du numberRoom
     * @param numberRoom numeros de la room
     * @return toute les room en fonction de son numeros
     */
    Room findAllByNumberRoom(@Param("numberRoom") int numberRoom);
}
