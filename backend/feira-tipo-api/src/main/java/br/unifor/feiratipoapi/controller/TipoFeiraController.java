package br.unifor.feiratipoapi.controller;

import br.unifor.feiratipoapi.dto.TipoFeiraRequest;
import br.unifor.feiratipoapi.dto.TipoFeiraResponse;
import br.unifor.feiratipoapi.service.TipoFeiraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoints REST de tipos de feira. Apenas delega ao service. */
@RestController
@RequestMapping("/api/tipos-feira")
public class TipoFeiraController {

    private final TipoFeiraService service;

    public TipoFeiraController(TipoFeiraService service) {
        this.service = service;
    }

    @GetMapping
    public List<TipoFeiraResponse> listar() {
        return service.listar().stream().map(TipoFeiraResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoFeiraResponse cadastrar(@Valid @RequestBody TipoFeiraRequest req) {
        return TipoFeiraResponse.from(service.cadastrar(req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable String id) {
        service.remover(id);
    }
}
