package raisetech.student.management.exception;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler{

  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex){
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>>
  handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(e -> errors.put(e.getField(),e.getDefaultMessage()));

    Map<String, Object> body = new HashMap<>();
    body.put("Message","validation failed");
    body.put("errors",errors);

    return
  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations()
        .forEach(v -> errors.put(v.getPropertyPath()
                .toString(), v.getMessage()));

    Map<String, Object> body = new HashMap<>();
    body.put("message", "validation failed");
    body.put("errors", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

}
