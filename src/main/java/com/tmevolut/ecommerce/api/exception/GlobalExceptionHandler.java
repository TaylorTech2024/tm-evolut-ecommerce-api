package com.tmevolut.ecommerce.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException ex, HttpServletRequest request) {
             return build(HttpStatus.NOT_FOUND, "Não encontrado", List.of(ex.getMessage()), request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> business(BusinessException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Regra de negócio", List.of(ex.getMessage()), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> msgs = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        return build(HttpStatus.BAD_REQUEST, "Validação", msgs, request);
    }

    // --- NOVO MÉTODO: O "Salvaged" ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGeneralException(Exception ex, HttpServletRequest request) { // Adicionado request
        logger.error("Erro inesperado no sistema" , ex);

        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor",
                List.of("Ocorreu um erro inesperado. Tente novamente mais tarde."),
                request
        );
    }

    // Agora o build espera 4 argumentos fixos + o objeto request
    private ResponseEntity<ErrorResponse>
    build(HttpStatus status, String erro, List<String> mensagens, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                erro,
                mensagens,
                request.getRequestURI() // 5º argumento
        );
        return ResponseEntity.status(status).body(body);
    }
}