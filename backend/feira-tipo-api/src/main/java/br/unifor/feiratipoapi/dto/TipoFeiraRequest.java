package br.unifor.feiratipoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Dados de entrada para cadastrar um tipo de feira. */
public record TipoFeiraRequest(

        @NotBlank(message = "Nome é obrigatório.")
        @Size(min = 3, message = "Nome deve ter pelo menos 3 caracteres.")
        String nome
) {
}
