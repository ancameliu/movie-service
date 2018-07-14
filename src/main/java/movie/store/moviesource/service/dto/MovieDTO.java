package movie.store.moviesource.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import movie.store.moviesource.model.Genre;
import movie.store.moviesource.model.User;

import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String releaseYear;
    private Genre genre;
    private String director;
    private double rating;
    private long numberOfRatings;
    private String photoId;
}
