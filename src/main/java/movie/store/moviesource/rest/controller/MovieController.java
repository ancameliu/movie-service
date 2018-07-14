package movie.store.moviesource.rest.controller;

import movie.store.moviesource.command.AddMovieCommand;
import movie.store.moviesource.model.Genre;
import movie.store.moviesource.model.Movie;
import movie.store.moviesource.rest.controller.exception.MovieNotFoundException;
import movie.store.moviesource.service.MovieService;
import movie.store.moviesource.service.dto.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
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

    @RequestMapping(value = "/{genre}/ratingsover/{rating}", method = RequestMethod.GET)
    public List<MovieDTO> findMoviesOfGenreAndRatingHigherThan(@PathVariable Genre genre,
                                                               @PathVariable("rating") double bottomRating) {
        List<MovieDTO> movies = movieService.findMoviesOfGenreAndRatingHigherThan(genre, bottomRating);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException("No movies of genre " + genre + " and rating higher than "
                                    + bottomRating + " found");
        }
        return movies;
    }

    @RequestMapping(value = "/ratingsover/{rating}", method = RequestMethod.GET)
    public List<MovieDTO> findMoviesOfRatingHigherThan(@PathVariable("rating") double bottomRating) {
        List<MovieDTO> movies = movieService.findMoviesWithRatingHigherThan(bottomRating);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException("No movies of rating higher than " + bottomRating + " found");
        }
        return movies;
    }


    //get by genre

    //get by director

    //get by release year


    //@PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public Long addMovie(@RequestBody AddMovieCommand addMovie) {
        return movieService.addMovie(addMovie);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

}
