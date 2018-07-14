package movie.store.moviesource.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_assign",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRole> roles = new HashSet<>();
    private Set<Movie> favoriteMovies;
    private Set<Movie> ratedMovies;

    private User() {}

    public User(String name, String password) {
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
        return favoriteMovies.add(movie);
    }

    public boolean removeFavoriteMovie(Movie movie) {
        return favoriteMovies.remove(movie);
    }

    public boolean rateMovie(Movie movie, double rating) {
        return ratedMovies.add(movie) && movie.updateRating(rating);
    }
}
