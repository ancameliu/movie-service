package movie.store.moviesource.service;

import movie.store.moviesource.model.Genre;
import movie.store.moviesource.model.Movie;
import movie.store.moviesource.model.User;
import movie.store.moviesource.model.UserRole;
import movie.store.moviesource.repository.UserRepository;
import movie.store.moviesource.repository.UserRoleRepository;
import movie.store.moviesource.rest.controller.exception.InvalidValuesException;
import movie.store.moviesource.rest.controller.exception.MovieNotFoundException;
import movie.store.moviesource.service.dto.MovieDTO;
import movie.store.moviesource.service.dto.UserDTO;
import movie.store.moviesource.service.dto.UserRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@ComponentScan("movie.store.moviesource")
@EnableJpaRepositories("movie.store.moviesource.repository")
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final MovieService movieService;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository roleRepository, MovieService movieService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.movieService = movieService;
    }

    @Transactional
    public void addFavoriteMovie(Long userId, Long movieId) {
        User user = userRepository.getOne(userId);
        Movie movie = movieService.getDirectMovieById(movieId);
        if (user.addFavoriteMovie(movie)) {
            userRepository.save(user);
            movieService.saveMovie(movie);
        }
    }

    @Transactional
    public void removeFavoriteMovie(Long userId, Long movieId) {
        User user = userRepository.getOne(userId);
        Movie movie = movieService.getDirectMovieById(movieId);
        if (user.removeFavoriteMovie(movie)) {
            userRepository.save(user);
            movieService.saveMovie(movie);
        }
    }

    @Transactional
    public void rateMovie(Long userId, Long movieId, double rating) {
        if (rating < 0.0 && rating > 10.0) {
            throw new InvalidValuesException("Rating must be between 0.0 and 10.0");
        }
        User user = userRepository.getOne(userId);
        Movie movie = movieService.getDirectMovieById(movieId);
        if (user.rateMovie(movie, rating)) {
            userRepository.save(user);
            movieService.saveMovie(movie);
        }
    }

    @Transactional
    public Set<MovieDTO> getRecommendations(Long userId, double bottomRating) {
        if (bottomRating < 0.0 && bottomRating > 10.0) {
            bottomRating = 7.0;
        }
        User user = userRepository.getOne(userId);
        Set<Movie> userFavorites = user.getFavoriteMovies();
        if (userFavorites.isEmpty()) {
            throw new MovieNotFoundException("Favorites list is empty. Cannot make recommendations.");
        }
        Set<Genre> favoriteGenres = new HashSet<>();
        Set<MovieDTO> userRecommendations = new HashSet<>();
        for (Movie favoriteMovie : userFavorites) {
            favoriteGenres.add(favoriteMovie.getGenre());
        }
        for (Genre genre : favoriteGenres) {
            userRecommendations.addAll(movieService.findMoviesOfGenreAndRatingHigherThan(genre, bottomRating));
        }
        return userRecommendations;
    }

    @Transactional
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> foundUsers = new ArrayList<>();
        for (User user : users) {
            foundUsers.add(userEntityToDTO(user));
        }
        return foundUsers;
    }

    @Transactional
    public List<UserDTO> getAllUsersWithFavoriteMovie(Long movieId) {
        List<UserDTO> allUsers = getAllUsers();
        MovieDTO movie = movieService.findMovieWithId(movieId);
        movie.setFavoriteOfUsers(null);
        movie.setRatedByUsers(null);
        return allUsers.stream().filter(user -> user.getFavoriteMovies().contains(movie))
                .collect(Collectors.toList());
    }

    UserDTO userEntityToDTO(User user) {
        List<UserRole> roles = user.getRoles();
        Set<UserRoleDTO> roleDTOS = new HashSet<>();
        for (UserRole role : roles) {
            roleDTOS.add(new UserRoleDTO(role.getId(), role.getRole()));
        }
        Set<Movie> favoriteMovies = user.getFavoriteMovies();
        Set<MovieDTO> favoriteMovieDTOS = new HashSet<>();
        for (Movie movie : favoriteMovies) {
            MovieDTO movieDTO = movieService.movieEntityToDTO(movie);
            movieDTO.setFavoriteOfUsers(null);
            movieDTO.setRatedByUsers(null);
            favoriteMovieDTOS.add(movieDTO);
        }
        Set<Movie> ratedMovies = user.getRatedMovies();
        Set<MovieDTO> ratedMovieDTOS = new HashSet<>();
        for (Movie movie : ratedMovies) {
            MovieDTO movieDTO = movieService.movieEntityToDTO(movie);
            movieDTO.setFavoriteOfUsers(null);
            movieDTO.setRatedByUsers(null);
            ratedMovieDTOS.add(movieDTO);
        }
        return new UserDTO(user.getId(), user.getName(), user.getPassword(), roleDTOS, favoriteMovieDTOS, ratedMovieDTOS);
    }

    /*
    User userDTOToEntity(UserDTO userDTO) {
        Set<UserRoleDTO> roleDTOS = userDTO.getRoles();
        List<UserRole> roles = new LinkedList<>();
        for (UserRoleDTO roleDTO : roleDTOS) {
            roles.add(roleRepository.getOne(roleDTO.getId()));
        }
        Set<MovieDTO> favoriteMovieDTOS = userDTO.getFavoriteMovies();
        Set<Movie> favoriteMovies = new HashSet<>();
        for (MovieDTO movieDTO : favoriteMovieDTOS) {
            favoriteMovies.add(movieService.movieDTOToEntity(movieDTO));
        }
        Set<MovieDTO> ratedMovieDTOS = userDTO.getRatedMovies();
        Set<Movie> ratedMovies = new HashSet<>();
        for (MovieDTO movieDTO : ratedMovieDTOS) {
            ratedMovies.add(movieService.movieDTOToEntity(movieDTO));
        }
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getPassword(), roles, favoriteMovies, ratedMovies);
    }
    */
}
