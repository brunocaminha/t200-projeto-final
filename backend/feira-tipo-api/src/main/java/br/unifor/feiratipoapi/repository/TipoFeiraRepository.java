package br.unifor.feiratipoapi.repository;

import br.unifor.feiratipoapi.domain.TipoFeira;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Persistência de {@link TipoFeira}. CRUD básico vem do {@link JpaRepository}.
 */
public interface TipoFeiraRepository extends JpaRepository<TipoFeira, String> {

    /** Verifica unicidade do nome do tipo (case-insensitive). */
    boolean existsByNomeIgnoreCase(String nome);
}
