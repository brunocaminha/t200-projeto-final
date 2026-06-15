package br.unifor.feiratipoapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Feira livre realizada em um endereço e classificada por um {@link TipoFeira}.
 *
 * <p>Entidade anêmica: validação de campo no DTO e regras de negócio no service
 * (endereço único entre feiras ativas, tipo existente). O tipo é obrigatório,
 * por isso a FK é {@code nullable = false}.
 */
@Entity
public class Feira {

    @Id
    private String id;

    private String nome;
    private String logradouro;
    private String bairro;
    private boolean ativa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoFeira tipo;

    protected Feira() {
    }

    public Feira(String id, String nome, String logradouro, String bairro,
                 boolean ativa, TipoFeira tipo) {
        this.id = id;
        this.nome = nome;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.ativa = ativa;
        this.tipo = tipo;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public TipoFeira getTipo() {
        return tipo;
    }

    public void setTipo(TipoFeira tipo) {
        this.tipo = tipo;
    }
}
