package br.unifor.feiratipoapi.service;

import br.unifor.feiratipoapi.domain.TipoFeira;
import br.unifor.feiratipoapi.dto.TipoFeiraRequest;
import br.unifor.feiratipoapi.exception.RegraNegocioException;
import br.unifor.feiratipoapi.repository.FeiraRepository;
import br.unifor.feiratipoapi.repository.TipoFeiraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Casos de uso de {@link TipoFeira}. Concentra as regras de negócio:
 * nome único e bloqueio de remoção de tipo em uso.
 */
@Service
public class TipoFeiraService {

    private final TipoFeiraRepository tipoFeiraRepository;
    private final FeiraRepository feiraRepository;

    public TipoFeiraService(TipoFeiraRepository tipoFeiraRepository, FeiraRepository feiraRepository) {
        this.tipoFeiraRepository = tipoFeiraRepository;
        this.feiraRepository = feiraRepository;
    }

    public TipoFeira cadastrar(TipoFeiraRequest req) {
        String nome = req.nome() == null ? null : req.nome().strip();
        if (nome != null && tipoFeiraRepository.existsByNomeIgnoreCase(nome)) {
            throw new RegraNegocioException("Já existe um tipo de feira com o nome '" + nome + "'.");
        }
        TipoFeira tipo = new TipoFeira(UUID.randomUUID().toString(), nome);
        return tipoFeiraRepository.save(tipo);
    }

    public List<TipoFeira> listar() {
        return tipoFeiraRepository.findAll();
    }

    public void remover(String id) {
        TipoFeira tipo = tipoFeiraRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Tipo de feira não encontrado: " + id));
        if (feiraRepository.existsByTipo_Id(tipo.getId())) {
            throw new RegraNegocioException(
                    "Não é possível remover o tipo '" + tipo.getNome() + "' pois ele está em uso.");
        }
        tipoFeiraRepository.deleteById(id);
    }
}
