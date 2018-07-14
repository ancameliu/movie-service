package movie.store.moviesource.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import movie.store.moviesource.model.Movie;
import movie.store.moviesource.model.UserRole;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String password;
    private Set<UserRoleDTO> roles;
    private Set<MovieDTO> favoriteMovies;
    private Set<MovieDTO> ratedMovies;
}
