package br.unifor.feiratipoapi.service;

import br.unifor.feiratipoapi.domain.Feira;
import br.unifor.feiratipoapi.domain.TipoFeira;
import br.unifor.feiratipoapi.dto.FeiraRequest;
import br.unifor.feiratipoapi.exception.RegraNegocioException;
import br.unifor.feiratipoapi.repository.FeiraRepository;
import br.unifor.feiratipoapi.repository.TipoFeiraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Casos de uso de {@link Feira}. Mantém as regras inter-entidades: o tipo
 * informado deve existir e não pode haver duas feiras ativas no mesmo endereço.
 */
@Service
public class FeiraService {

    private final FeiraRepository feiraRepository;
    private final TipoFeiraRepository tipoFeiraRepository;

    public FeiraService(FeiraRepository feiraRepository, TipoFeiraRepository tipoFeiraRepository) {
        this.feiraRepository = feiraRepository;
        this.tipoFeiraRepository = tipoFeiraRepository;
    }

    public Feira cadastrar(FeiraRequest req) {
        TipoFeira tipo = resolverTipo(req.tipoId());
        if (req.ativa()) {
            garantirEnderecoLivre(req.logradouro(), req.bairro(), null);
        }
        Feira feira = new Feira(UUID.randomUUID().toString(),
                req.nome().strip(), req.logradouro().strip(), req.bairro().strip(), req.ativa(), tipo);
        return feiraRepository.save(feira);
    }

    public List<Feira> listar() {
        return feiraRepository.findAll();
    }

    public Feira buscarPorId(String id) {
        return feiraRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Feira não encontrada: " + id));
    }

    public Feira atualizar(String id, FeiraRequest req) {
        Feira feira = buscarPorId(id);
        TipoFeira tipo = resolverTipo(req.tipoId());
        if (req.ativa()) {
            garantirEnderecoLivre(req.logradouro(), req.bairro(), id);
        }
        feira.setNome(req.nome().strip());
        feira.setLogradouro(req.logradouro().strip());
        feira.setBairro(req.bairro().strip());
        feira.setTipo(tipo);
        feira.setAtiva(req.ativa());
        return feiraRepository.save(feira);
    }

    public void remover(String id) {
        buscarPorId(id);
        feiraRepository.deleteById(id);
    }

    private TipoFeira resolverTipo(String tipoId) {
        if (tipoId == null || tipoId.isBlank()) {
            throw new RegraNegocioException("Tipo da feira é obrigatório.");
        }
        return tipoFeiraRepository.findById(tipoId)
                .orElseThrow(() -> new RegraNegocioException("Tipo de feira não encontrado: " + tipoId));
    }

    /** Garante que nenhuma outra feira ativa ocupa o mesmo endereço (case-insensitive). */
    private void garantirEnderecoLivre(String logradouro, String bairro, String idIgnorar) {
        String log = logradouro == null ? "" : logradouro.strip();
        String bai = bairro == null ? "" : bairro.strip();
        boolean colisao = (idIgnorar == null)
                ? feiraRepository.existsByAtivaTrueAndLogradouroIgnoreCaseAndBairroIgnoreCase(log, bai)
                : feiraRepository.existsByAtivaTrueAndLogradouroIgnoreCaseAndBairroIgnoreCaseAndIdNot(log, bai, idIgnorar);
        if (colisao) {
            throw new RegraNegocioException("Já existe uma feira ativa no endereço informado.");
        }
    }
}
