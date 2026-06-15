package br.unifor.feiratipoapi.dto;

/** Dados de entrada para cadastrar/atualizar uma feira. Validação de campo na issue #5. */
public record FeiraRequest(String nome, String logradouro, String bairro, boolean ativa, String tipoId) {
}
