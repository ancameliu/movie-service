package movie.store.moviesource.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public void setNewUser(User user) {
        users.add(user);
    }

    private UserRole() {}

    public UserRole(String role) {
        this.role = role;
    }

    public UserRole(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    @JsonIgnore
    public Set<User> getUsers() {
        return users;
    }
}
