package movie.store.moviesource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String releaseYear;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private String director;
    private double rating;
    private long numberOfRatings;
    private String photoId;

    private Movie() {}

    public Movie(String title, String releaseYear, Genre genre, String director,
                 double rating, long numberOfRatings, String photoId) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.director = director;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
        this.photoId = photoId;
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
}
