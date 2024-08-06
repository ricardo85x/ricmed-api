package med.ric.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.ric.api.domain.CustomValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Pattern;
import java.util.stream.Stream;

@RestControllerAdvice
public class ErrorsHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleException404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Stream<ValidationErrorData>> handleException400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ValidationErrorData::new));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleExceptionNotReadable(HttpMessageNotReadableException ex) {
        var message = ex.getCause().getMessage();
        var responseMessage = "Invalid input.";
        if (message != null && message.contains("not one of the values accepted for Enum class")) {
            var invalidValue = message.split("\"")[1];
            var patterValidValues = Pattern.compile( ".*\\[([^\\]]+)\\].*");
            var matches = patterValidValues.matcher(message);
            if(matches.find()) {
                var acceptedValues = matches.group(1);
                responseMessage = String.format("Invalid value '%s'. Accepted values are: %s.", invalidValue, acceptedValues);
            }
        }
        return ResponseEntity.badRequest().body(responseMessage);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<String> handleCustomValidationException(CustomValidationException ex) {
       return ResponseEntity.badRequest().body(ex.getMessage());
    }

    public record ValidationErrorData(String field, String message) {
        public ValidationErrorData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
