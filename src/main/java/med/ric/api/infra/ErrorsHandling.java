package med.ric.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleException404() {
        return ResponseEntity.notFound().build();
    }
}
