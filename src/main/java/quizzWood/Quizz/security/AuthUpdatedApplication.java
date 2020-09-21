package quizzWood.Quizz.security;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * permet de creer une instance de bCryptPasswordEncoder
 */
@SpringBootApplication
public class AuthUpdatedApplication {

    /**
     * @return une instance bCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
