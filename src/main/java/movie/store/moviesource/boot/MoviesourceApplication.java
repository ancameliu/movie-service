package movie.store.moviesource.boot;

import movie.store.moviesource.rest.controller.MovieController;
import movie.store.moviesource.rest.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses=MovieController.class)
@ComponentScan(basePackageClasses=UserController.class)
public class MoviesourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesourceApplication.class, args);
	}
}
