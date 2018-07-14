package movie.store.moviesource.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import movie.store.moviesource.model.Genre;
import movie.store.moviesource.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMovieCommand {
    private String title;
    private String releaseYear;
    private Genre genre;
    private String director;
    private double rating;
    private long numberOfRatings;
    private String photoId;
}
