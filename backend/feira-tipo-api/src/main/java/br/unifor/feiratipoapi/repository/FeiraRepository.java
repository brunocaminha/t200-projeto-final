package br.unifor.feiratipoapi.repository;

import br.unifor.feiratipoapi.domain.Feira;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Persistência de {@link Feira}. CRUD básico vem do {@link JpaRepository};
 * as consultas abaixo atendem às regras de negócio do service.
 */
public interface FeiraRepository extends JpaRepository<Feira, String> {

    /** Há alguma feira associada ao tipo? Usado para bloquear a remoção do tipo. */
    boolean existsByTipo_Id(String tipoId);

    /** Endereço já ocupado por uma feira ativa (logradouro + bairro, case-insensitive). */
    boolean existsByAtivaTrueAndLogradouroIgnoreCaseAndBairroIgnoreCase(String logradouro, String bairro);

    /** Mesma checagem ignorando uma feira específica (para não conflitar consigo na edição). */
    boolean existsByAtivaTrueAndLogradouroIgnoreCaseAndBairroIgnoreCaseAndIdNot(
            String logradouro, String bairro, String id);
}
