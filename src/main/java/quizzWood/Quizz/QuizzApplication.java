package quizzWood.Quizz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@SpringBootApplication
@EntityScan(basePackages = {"quizzWood/Quizz/model"})
@Configuration
public class QuizzApplication {


	/**
	 * classe maitre de l'application avec spécification de connexion a la BDD
	 * @param args argument de base
	 */
	public static void main(String[] args) {
		SpringApplication.run(QuizzApplication.class, args);
		//Parametre de connexion a la base de donnée
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://us-cdbr-east-02.cleardb.com:3306/heroku_4902f147ae1e459");
		dataSource.setUsername("b8092e7d4ac726");
		dataSource.setPassword("65c7eaf1");
	}
}
