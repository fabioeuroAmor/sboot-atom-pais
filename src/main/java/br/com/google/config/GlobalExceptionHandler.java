package br.com.google.config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder("Erro de validação: ");
        ex.getBindingResult().getAllErrors().forEach(error ->
                errors.append(error.getDefaultMessage()).append("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }
}