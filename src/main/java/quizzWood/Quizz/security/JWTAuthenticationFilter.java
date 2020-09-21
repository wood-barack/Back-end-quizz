package quizzWood.Quizz.security;

import com.auth0.jwt.JWT;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static quizzWood.Quizz.security.SecurityConstants.EXPIRATION_TIME;
import static quizzWood.Quizz.security.SecurityConstants.HEADER_STRING;
import static quizzWood.Quizz.security.SecurityConstants.SECRET;
import static quizzWood.Quizz.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * @param req Définition d'un objet contenant la requête du client
     * @param res Définition d'un objet qui contient la réponse renvoyée par la servlet
     * @return l'authentifiaction
     * @throws AuthenticationException
     * analyse les informations d'identification de l'utilisateur et les envoie au AuthenticationManager
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            quizzWood.Quizz.model.User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), quizzWood.Quizz.model.User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param req Définition d'un objet contenant la requête du client
     * @param res Définition d'un objet qui contient la réponse renvoyée par la servlet
     * @param chain permet de filtrer les chaines
     * @param auth authentification
     * @throws IOException capture des exceptions
     * @throws ServletException capture des exceptions
     * Cette méthode est appelée lorsqu'un utilisateur réussit à se connecter
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.getWriter().write("{\"" + HEADER_STRING + "\":\"" + TOKEN_PREFIX+token + "\"}");

        System.out.println(token);
        System.out.println(quizzWood.Quizz.model.User.class.getName());

    }

}
