package com.tmevolut.ecommerce.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private HttpServletRequest request;

    @Test
    void deveTratarResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Registro não encontrado");
        when(request.getRequestURI()).thenReturn("/api/teste");

        // Tipo corrigido para ResponseEntity<ErrorResponse>
        ResponseEntity<ErrorResponse> response = exceptionHandler.notFound(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deveTratarBusinessException() {
        BusinessException ex = new BusinessException("Regra de negócio violada");
        when(request.getRequestURI()).thenReturn("/api/teste");

        // Tipo corrigido para ResponseEntity<ErrorResponse>
        ResponseEntity<ErrorResponse> response = exceptionHandler.business(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deveTratarExceptionGenerica() {
        Exception ex = new Exception("Erro interno inesperado");
        when(request.getRequestURI()).thenReturn("/api/teste");

        // Tipo corrigido para ResponseEntity<ErrorResponse>
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGeneralException(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deveTratarValidationExceptionEMatarALambda() {
        FieldError fieldError = new FieldError("categoriaRequest", "nome", "O nome é obrigatório");

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        when(request.getRequestURI()).thenReturn("/api/teste");

        // Tipo corrigido para ResponseEntity<ErrorResponse>
        ResponseEntity<ErrorResponse> response = exceptionHandler.validation(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}