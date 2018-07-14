package movie.store.moviesource.rest.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidValuesException extends RuntimeException {

    public InvalidValuesException(String message) {
        super(message);
    }
}
