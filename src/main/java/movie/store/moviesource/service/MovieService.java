package movie.store.moviesource.service;

import movie.store.moviesource.command.AddMovieCommand;
import movie.store.moviesource.model.Genre;
import movie.store.moviesource.model.Movie;
import movie.store.moviesource.model.User;
import movie.store.moviesource.repository.MovieRepository;
import movie.store.moviesource.rest.controller.exception.MovieNotFoundException;
import movie.store.moviesource.service.dto.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository repository) {
        this.movieRepository = repository;
    }

    void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Transactional
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies =  movieRepository.findAll();
        List<MovieDTO> foundMovies = new ArrayList<>();
        for (Movie movie : movies) {
            foundMovies.add(movieEntityToDTO(movie));
        }
        return foundMovies;
    }

    @Transactional
    public MovieDTO findMovieWithId(Long id) {
        Movie movie = movieRepository.getOne(id);
        return movieEntityToDTO(movie);
    }

    @Transactional
    public List<MovieDTO> findMoviesWithTitle(String title) {
        List<Movie> movies =  movieRepository.findByTitle(title);
        List<MovieDTO> foundMovies = new ArrayList<>();
        for (Movie movie : movies) {
            foundMovies.add(movieEntityToDTO(movie));
        }
        return foundMovies;
    }

    @Transactional
    public List<MovieDTO> findMoviesOfGenreAndRatingHigherThan(Genre genre, double bottomRating) {
        if (bottomRating < 0.0 && bottomRating > 10.0) {
            bottomRating = 7.0;
        }
        List<Movie> movies =  movieRepository.findByGenreAndBottomRating(genre, bottomRating);
        List<MovieDTO> foundMovies = new ArrayList<>();
        for (Movie movie : movies) {
            foundMovies.add(movieEntityToDTO(movie));
        }
        return foundMovies;
    }

    @Transactional
    public List<MovieDTO> findMoviesWithRatingHigherThan(double bottomRating) {
        if (bottomRating < 0.0 && bottomRating > 10.0) {
            bottomRating = 7.0;
        }
        List<Movie> movies =  movieRepository.findByRatingHigherThan(bottomRating);
        List<MovieDTO> foundMovies = new ArrayList<>();
        for (Movie movie : movies) {
            foundMovies.add(movieEntityToDTO(movie));
        }
        return foundMovies;
    }

    //get by genre

    //get by director

    //get by release year

    @Transactional
    public Long addMovie(AddMovieCommand command) {
        Movie movie = new Movie(command.getTitle(), command.getReleaseYear(), command.getGenre(), command.getDirector(),
                command.getRating(), command.getNumberOfRatings(), command.getPhotoId());
        movieRepository.save(movie);
        return movie.getId();
    }

    @Transactional
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    MovieDTO movieEntityToDTO(Movie movie) {
        return new MovieDTO(movie.getId(), movie.getTitle(), movie.getReleaseYear(), movie.getGenre(),
                movie.getDirector(), movie.getRating(), movie.getNumberOfRatings(), movie.getPhotoId());
    }

    Movie movieDTOToEntity(MovieDTO movieDTO) {
        return new Movie(movieDTO.getId(), movieDTO.getTitle(), movieDTO.getReleaseYear(), movieDTO.getGenre(),
                movieDTO.getDirector(), movieDTO.getRating(), movieDTO.getNumberOfRatings(), movieDTO.getPhotoId());
    }
}
