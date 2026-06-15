package br.unifor.feiratipoapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Classificador de feiras (ex.: "Orgânica", "Noturna", "Agricultura familiar").
 *
 * <p>Na versão web a entidade é anêmica: a validação de campo fica no DTO
 * (Bean Validation) e as regras de negócio no service. O Hibernate hidrata
 * via construtor sem-arg e setters.
 */
@Entity
public class TipoFeira {

    @Id
    private String id;

    private String nome;

    protected TipoFeira() {
    }

    public TipoFeira(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
