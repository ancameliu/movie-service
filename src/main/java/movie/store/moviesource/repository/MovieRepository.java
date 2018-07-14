package movie.store.moviesource.repository;

import movie.store.moviesource.model.Genre;
import movie.store.moviesource.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitle(String title);

    List<Movie> findByReleaseYear(String releaseYear);

    List<Movie> findByGenre(String genre);

    List<Movie> findByDirector(String director);

    @Query("SELECT m FROM Movie m WHERE m.genre = :genre AND m.rating >= :bottomRating")
    List<Movie> findByGenreAndBottomRating(@Param("genre") String genre, @Param("bottomRating") double bottomRating);

    @Query("SELECT m FROM Movie m WHERE m.rating >= :bottomRating")
    List<Movie> findByRatingHigherThan(@Param("bottomRating") double bottomRating);
}
