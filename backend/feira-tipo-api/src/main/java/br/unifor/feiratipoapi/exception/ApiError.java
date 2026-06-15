package br.unifor.feiratipoapi.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Corpo padrão de erro da API. O campo {@code errors} só aparece em respostas
 * de validação (400); nas demais é omitido.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<CampoInvalido> errors
) {
    /** Erro de um campo específico na validação. */
    public record CampoInvalido(String campo, String mensagem) {
    }
}
