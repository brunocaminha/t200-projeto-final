package br.unifor.feiratipoapi.dto;

import br.unifor.feiratipoapi.domain.TipoFeira;

/** Saída de tipo de feira; evita serializar a entidade diretamente. */
public record TipoFeiraResponse(String id, String nome) {

    public static TipoFeiraResponse from(TipoFeira tipo) {
        return new TipoFeiraResponse(tipo.getId(), tipo.getNome());
    }
}
