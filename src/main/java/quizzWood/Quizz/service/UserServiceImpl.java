package quizzWood.Quizz.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import quizzWood.Quizz.repository.UserRepository;

import static java.util.Collections.emptyList;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepositorie;

    public UserServiceImpl(UserRepository applicationUserRepository) {
        this.userRepositorie = applicationUserRepository;
    }

    /**
     * @param email de l'utilisateur qui tente de se connecter
     * @return l'utilisateur connecter
     * @throws UsernameNotFoundException
     * recherche dans la base de données un enregistrement le contenant  (s'il est trouvé) renvoie une instance de User
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        quizzWood.Quizz.model.User applicationUserModel = userRepositorie.findByEmail(email);
        if (applicationUserModel == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(applicationUserModel.getEmail(), applicationUserModel.getPassword(), emptyList());
    }

}
