/**
 * Controller to handle exceptions
 */
package uk.ac.standrews.cs.host.cs3099user20.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> exception(UserNotFoundException exception) {
        System.out.println("what");
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
