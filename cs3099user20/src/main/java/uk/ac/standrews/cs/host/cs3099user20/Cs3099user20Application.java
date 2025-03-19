package uk.ac.standrews.cs.host.cs3099user20;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;

//@SpringBootApplication
//@RestController

//public class Cs3099user20Application {

	//@Value("${spring.application.name}")
	//private String name; // used to get the application name.`

	//public static void main(String[] args) {
		//SpringApplication.run(Cs3099user20Application.class, args);
	//}

	/*
	@RequestMapping(value = "/")
	public String name() {
		return "Welcome to this demo page of: " + name;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	*/

	/*
	@RequestMapping(value = "/")
	public String hello() {
		return "Hello World";
	}
	@RequestMapping(value = "/login")
	public String login() {
		return "Login";
	}

	 */

//}
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.standrews.cs.host.cs3099user20.dao.DatabaseInitialisationRepository;
import uk.ac.standrews.cs.host.cs3099user20.database.Database;
import java.sql.SQLException;

@SpringBootApplication
public class Cs3099user20Application {

	public static void main(String[] args) throws SQLException {
		// Specify database
		Database application = new Database();

		// Check database is open
		application.tryToAccessDB();
		SpringApplication.run(Cs3099user20Application.class, args);
	}
}
