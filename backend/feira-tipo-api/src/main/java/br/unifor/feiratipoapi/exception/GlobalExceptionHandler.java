package br.unifor.feiratipoapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Padroniza as respostas de erro da API: validação de campo vira 400 (com a
 * lista de campos) e regra de negócio vira 422.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiError> tratarRegraNegocio(RegraNegocioException ex, HttpServletRequest req) {
        ApiError corpo = new ApiError(
                OffsetDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Regra de negócio",
                ex.getMessage(),
                req.getRequestURI(),
                null);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(corpo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> tratarValidacao(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ApiError.CampoInvalido> campos = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ApiError.CampoInvalido(fe.getField(), fe.getDefaultMessage()))
                .toList();
        ApiError corpo = new ApiError(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validação",
                "Há campos inválidos na requisição.",
                req.getRequestURI(),
                campos);
        return ResponseEntity.badRequest().body(corpo);
    }
}
