package fornari.nucleo.helper.validation;

import fornari.nucleo.helper.messages.Message;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Message> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Erro de validação desconhecido");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(errorMessage));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Message> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String violatedField = "";
        String message = "Erro desconhecido.";
        if (e.getMessage() != null) {
            if (e.getMessage().contains(".cpf]")) {
                violatedField = "CPF";
            } else if (e.getMessage().contains(".email]")) {
                violatedField = "Email";
            } else if (e.getMessage().contains(".ra]")) {
                violatedField = "RA";
            } else if (e.getMessage().contains(".tipo]")) {
                violatedField = "Tipo";
            }
            message = "Este valor de " + violatedField + " já está em uso. Por favor, utilize um valor diferente.";
        }

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", message);
        body.put("path", "/api/usuarios/funcionario");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}