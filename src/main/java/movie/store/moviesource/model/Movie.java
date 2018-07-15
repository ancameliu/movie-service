package movie.store.moviesource.model;

import lombok.*;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "releaseYear")
    private String releaseYear;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private String director;
    private double rating;
    @Column(name = "numberOfRatings")
    private long numberOfRatings;
    @ManyToMany(mappedBy = "favoriteMovies")
    private Set<User> favoriteOfUsers = new HashSet<>();
    @ManyToMany(mappedBy = "ratedMovies")
    private Set<User> ratedByUsers = new HashSet<>();

    public Movie() {}

    public Movie(String title, String releaseYear, Genre genre, String director,
                 double rating, long numberOfRatings) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.director = director;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
    }

    public Movie(Long id, String title, String releaseYear, Genre genre, String director,
                 double rating, long numberOfRatings) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.director = director;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
    }

    public synchronized boolean updateRating(double userRating) {
        double initialRating = formatMovieRating(rating);
        numberOfRatings++;
        rating = formatMovieRating((rating + userRating) / numberOfRatings);
        if (initialRating != rating) {
            return true;
        } else
            return false;
    }

    public double formatMovieRating(double rating) {
        NumberFormat formatter = new DecimalFormat("#0.0");
        return Double.parseDouble(formatter.format(rating));
    }

    public void addFavoriteOfUser(User user) {
        favoriteOfUsers.add(user);
    }

    public void removeFavoriteOfUser(User user) {
        favoriteOfUsers.remove(user);
    }

    public void addRatedByUser(User user) {
        ratedByUsers.add(user);
    }
}
