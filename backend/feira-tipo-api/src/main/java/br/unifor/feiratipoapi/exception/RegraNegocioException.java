package br.unifor.feiratipoapi.exception;

/**
 * Sinaliza violação de regra de negócio. Tratada no GlobalExceptionHandler,
 * que a converte em HTTP 422.
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
