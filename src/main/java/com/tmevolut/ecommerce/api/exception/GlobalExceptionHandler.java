package com.tmevolut.ecommerce.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "Não encontrado", List.of(ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> business(BusinessException ex) {
        return build(HttpStatus.BAD_REQUEST, "Regra de negócio", List.of(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex) {
        List<String> msgs = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        return build(HttpStatus.BAD_REQUEST, "Validação", msgs);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String erro, List<String> mensagens) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                erro,
                mensagens
        );

        return ResponseEntity.status(status).body(body);
    }
}