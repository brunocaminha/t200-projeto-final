package br.unifor.feiratipoapi.dto;

import br.unifor.feiratipoapi.domain.Feira;

/** Saída de feira com o tipo achatado (tipoId + tipoNome); evita expor a entidade. */
public record FeiraResponse(
        String id,
        String nome,
        String logradouro,
        String bairro,
        boolean ativa,
        String tipoId,
        String tipoNome
) {
    public static FeiraResponse from(Feira feira) {
        return new FeiraResponse(
                feira.getId(),
                feira.getNome(),
                feira.getLogradouro(),
                feira.getBairro(),
                feira.isAtiva(),
                feira.getTipo().getId(),
                feira.getTipo().getNome());
    }
}
