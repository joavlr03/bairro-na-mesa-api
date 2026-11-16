package br.com.fiap.bairronamesa.controller;

import br.com.fiap.bairronamesa.dto.request.FeiraRequest;
import br.com.fiap.bairronamesa.dto.response.FeiraResponse;
import br.com.fiap.bairronamesa.service.FeiraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feiras")
@RequiredArgsConstructor
@Tag(name = "Feiras")
public class FeiraController {

    private final FeiraService service;

    @PostMapping
    @Operation(summary = "Cria uma feira", description = "Retorna 201 com o recurso criado; 400 se os dados forem inválidos.")
    public ResponseEntity<FeiraResponse> criar(@Valid @RequestBody FeiraRequest request) {
        FeiraResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/v1/feiras/" + response.getId())).body(response);
    }

    @GetMapping
    @Operation(summary = "Lista feiras")
    public List<FeiraResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta uma feira pelo id", description = "Retorna 404 se não existir.")
    public FeiraResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma feira", description = "Retorna 400 se os dados forem inválidos; 404 se não existir.")
    public FeiraResponse atualizar(@PathVariable Long id, @Valid @RequestBody FeiraRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma feira", description = "Retorna 204 sem corpo; 404 se não existir; 409 se estiver em uso.")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
