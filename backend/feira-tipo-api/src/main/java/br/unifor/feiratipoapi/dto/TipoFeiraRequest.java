package br.unifor.feiratipoapi.dto;

/** Dados de entrada para cadastrar um tipo de feira. Validação de campo na issue #5. */
public record TipoFeiraRequest(String nome) {
}
