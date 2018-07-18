package movie.store.moviesource.rest.controller;

import movie.store.moviesource.command.AddMovieCommand;
import movie.store.moviesource.model.Genre;
import movie.store.moviesource.model.Movie;
import movie.store.moviesource.rest.controller.exception.MovieNotFoundException;
import movie.store.moviesource.service.MovieService;
import movie.store.moviesource.service.dto.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@ComponentScan("movie.store.moviesource.service")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MovieDTO findMovieById(@PathVariable Long id) {
        MovieDTO movie = movieService.findMovieWithId(id);
        if (movie == null) {
            throw new MovieNotFoundException("Movie with id " + id + " could not be found");
        }
        return movie;
    }

    @RequestMapping(value = "/titles/{title}", method = RequestMethod.GET)
    public List<MovieDTO> findMoviesByTitle(@PathVariable String title) {
        List<MovieDTO> movies = movieService.findMoviesWithTitle(title);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException("No movies with title " + title + " found");
        }
        return movies;
    }

    @RequestMapping(value = "/year/{year}", method = RequestMethod.GET)
    public List<MovieDTO> findMoviesByReleaseYear(@PathVariable("year") String releaseYear) {
        List<MovieDTO> movies = movieService.findMoviesWithReleaseYear(releaseYear);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException("No movies with release year " + releaseYear + " found");
        }
        return movies;
    }

    @RequestMapping(value = "/genre/{genre}", method = RequestMethod.GET)
    public List<MovieDTO> findMoviesByGenre(@PathVariable Genre genre) {
        List<MovieDTO> movies = movieService.findMoviesOfGenre(genre);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException("No movies of genre " + genre + " found");
        }
        return movies;
    }

    @RequestMapping(value = "/director/{director}", method = RequestMethod.GET)
    public List<MovieDTO> findMoviesByDirector(@PathVariable String director) {
        List<MovieDTO> movies = movieService.findMoviesWithDirector(director);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException("No movies by director " + director + " found");
        }
        return movies;
    }

    @RequestMapping(value = "/{genre}/ratingover/{rating}", method = RequestMethod.GET)
    public List<MovieDTO> findMoviesOfGenreAndRatingHigherThan(@PathVariable Genre genre,
                                                               @PathVariable("rating") double bottomRating) {
        List<MovieDTO> movies = movieService.findMoviesOfGenreAndRatingHigherThan(genre, bottomRating);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException("No movies of genre " + genre + " and rating higher than "
                                    + bottomRating + " found");
        }
        return movies;
    }

    @RequestMapping(value = "/ratingover/{rating}", method = RequestMethod.GET)
    public List<MovieDTO> findMoviesOfRatingHigherThan(@PathVariable("rating") double bottomRating) {
        List<MovieDTO> movies = movieService.findMoviesWithRatingHigherThan(bottomRating);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException("No movies of rating higher than " + bottomRating + " found");
        }
        return movies;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long addMovie(@RequestBody AddMovieCommand addMovie) {
        return movieService.addMovie(addMovie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

}
