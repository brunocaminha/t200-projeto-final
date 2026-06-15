package br.unifor.feiratipoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Dados de entrada para cadastrar/atualizar uma feira. */
public record FeiraRequest(

        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, message = "Nome deve ter pelo menos 3 caracteres.")
        String nome,

        @NotBlank(message = "Logradouro é obrigatório.")
        String logradouro,

        @NotBlank(message = "Bairro é obrigatório.")
        String bairro,

        boolean ativa,

        @NotBlank(message = "Tipo da feira é obrigatório.")
        String tipoId
) {
}
