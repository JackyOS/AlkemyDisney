package spring.demo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	//lo registramos en spring para luego poder injectarlo cona autowire en el service
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


/*
	@Bean
	CommandLineRunner run(PersonService personService, MovieService movieService, UserService userService, GenreService genreService){
		return args -> {

			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			//agregamos usuarios
			userService.saveUser(new User(null, "Pepe", "Lopez", "pepo", "pepe@gmail.com", "1234", new HashSet<>()));
			userService.saveUser(new User(null, "Juan", "Martinez","juanjo", "juan@gmail.com", "1234", new HashSet<>()));
			userService.saveUser(new User(null, "Lola", "Rodriguez","lola" , "lola@gmail.com", "1234", new HashSet<>()));
			userService.saveUser(new User(null, "Fernanda", "Que tal Anda", "fer","fernanda@gmail.com", "1234", new HashSet<>()));

			//userService.addRoleToUser("pepo", "ROL_USER");
			userService.addRoleToUser("juanjo", "ROLE_ADMIN");
			userService.addRoleToUser("lola", "ROLE_USER");
			userService.addRoleToUser("fer", "ROLE_ADMIN");


			//agregamos personajes
			movieService.saveMovie(new Movie(null, "urlVer", "Los Simpson", LocalDate.of(1990, 10, 26),3, new HashSet<>(), new HashSet<>()));
			movieService.saveMovie(new Movie(null, "urlVer", "Avengers", LocalDate.of(2015, 8, 15),5, new HashSet<>(), new HashSet<>()));


			//agregamos usuarios
			personService.savePerson(new Person(null, "urlVer", "Homero Simpson", 55, 120, "Hombre de familia bla bla", new HashSet<>()));
			personService.savePerson(new Person(null, "urlVer", "Iron Man",45, 80, "super heroe genio", new HashSet<>()));
			personService.savePerson(new Person(null, "urlVer", "Black Widow",36 , 65, "Heroina bla bla", new HashSet<>()));
			personService.savePerson(new Person(null, "urlVer", "Marge Simpson", 52,70, "Mujer bla bla", new HashSet<>()));

			//creamos generos
			genreService.saveGenre(new Genre(null, "Accion","urlVer", new HashSet<>()));
			genreService.saveGenre(new Genre(null, "Comedia","urlVer", new HashSet<>()));


			//agregamos el personaje a la pelicula
			movieService.addPersonToMovie("Homero Simpson", "Los Simpson");
			movieService.addPersonToMovie("Iron Man","Avengers");
			movieService.addPersonToMovie("Black Widow", "Avengers");
			movieService.addPersonToMovie("Marge Simpson", "Los Simpson");

			personService.addMovieToPerson("Homero Simpson", "Los Simpson");
			personService.addMovieToPerson("Iron Man","Avengers");
			personService.addMovieToPerson("Black Widow", "Avengers");
			personService.addMovieToPerson("Marge Simpson", "Los Simpson");


		}; //esto va a iniciar despues de que la aplicacion haya iniciado
		//lo usamos para guardar el usuario, guardar rol y agregar rol al usuario
	};
*/

}
