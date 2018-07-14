package movie.store.moviesource.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import movie.store.moviesource.model.User;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private Long id;
    private String role;
}
