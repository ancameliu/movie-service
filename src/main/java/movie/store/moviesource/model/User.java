package movie.store.moviesource.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_assign",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<UserRole> roles = new LinkedList<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    private Set<Movie> favoriteMovies;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_rated",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    private Set<Movie> ratedMovies;

    public User() {}

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        favoriteMovies = new HashSet<>();
        ratedMovies = new HashSet<>();
    }

    public User(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.favoriteMovies = user.getFavoriteMovies();
        this.ratedMovies = user.getRatedMovies();
    }

    public void setNewRole(UserRole role) {
        roles.add(role);
        role.setNewUser(this);
    }

    public boolean addFavoriteMovie(Movie movie) {
        if (favoriteMovies.add(movie)) {
            movie.addFavoriteOfUser(this);
            return true;
        } else
            return false;
    }

    public boolean removeFavoriteMovie(Movie movie) {
        if (favoriteMovies.remove(movie)) {
            movie.removeFavoriteOfUser(this);
            return true;
        } else
            return false;
    }

    public boolean rateMovie(Movie movie, double rating) {
        if ((!ratedMovies.contains(movie)) && movie.updateRating(rating)) {
            ratedMovies.add(movie);
            movie.addRatedByUser(this);
            return true;
        } else
            return false;
    }
}
