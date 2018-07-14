package movie.store.moviesource.rest.controller;

import movie.store.moviesource.rest.controller.exception.InvalidValuesException;
import movie.store.moviesource.service.MovieService;
import movie.store.moviesource.service.UserService;
import movie.store.moviesource.service.dto.MovieDTO;
import movie.store.moviesource.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{userId}/addfave/{movieId}", method = RequestMethod.PUT)
    public void addFavoriteMovie(@PathVariable Long userId, @PathVariable Long movieId) {
        userService.addFavoriteMovie(userId, movieId);
    }

    @RequestMapping(value = "/{userId}/removefave/{movieId}", method = RequestMethod.DELETE)
    public void removeFavoriteMovie(@PathVariable Long userId, @PathVariable Long movieId) {
        userService.removeFavoriteMovie(userId, movieId);
    }

    @RequestMapping(value = "/{userId}/recommendations", method = RequestMethod.GET)
    public Set<MovieDTO> getRecommendations(@PathVariable Long userId,
                                @RequestParam(defaultValue = "7.0", required = false)double bottomRating) {
        return userService.getRecommendations(userId, bottomRating);
    }

    @RequestMapping(value = "/{userId}/ratemovie/{movieId}", method = RequestMethod.PUT)
    public void rateMovie(@PathVariable Long userId, @PathVariable Long movieId,
                          @RequestParam double rating) {
        userService.rateMovie(userId, movieId, rating);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/favoritemovie/{movieId}", method = RequestMethod.GET)
    public List<UserDTO> getAllUsersWithFavoriteMovie(@PathVariable Long movieId) {
        List<UserDTO> users = userService.getAllUsersWithFavoriteMovie(movieId);
        if (users.isEmpty()) {
            throw new InvalidValuesException("No users with this favorite movie found");
        }
        return users;
    }
}
