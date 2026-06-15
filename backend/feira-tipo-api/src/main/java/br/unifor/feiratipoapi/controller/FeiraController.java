package br.unifor.feiratipoapi.controller;

import br.unifor.feiratipoapi.dto.FeiraRequest;
import br.unifor.feiratipoapi.dto.FeiraResponse;
import br.unifor.feiratipoapi.service.FeiraService;
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

/** Endpoints REST de feiras. Apenas delega ao service. */
@RestController
@RequestMapping("/api/feiras")
public class FeiraController {

    private final FeiraService service;

    public FeiraController(FeiraService service) {
        this.service = service;
    }

    @GetMapping
    public List<FeiraResponse> listar() {
        return service.listar().stream().map(FeiraResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FeiraResponse cadastrar(@Valid @RequestBody FeiraRequest req) {
        return FeiraResponse.from(service.cadastrar(req));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable String id) {
        service.remover(id);
    }
}
