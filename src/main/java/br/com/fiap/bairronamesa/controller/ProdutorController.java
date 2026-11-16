package br.com.fiap.bairronamesa.controller;

import br.com.fiap.bairronamesa.dto.request.ProdutorRequest;
import br.com.fiap.bairronamesa.dto.response.ProdutorResponse;
import br.com.fiap.bairronamesa.service.ProdutorService;
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
@RequestMapping("/api/v1/produtores")
@RequiredArgsConstructor
@Tag(name = "Produtores")
public class ProdutorController {

    private final ProdutorService service;

    @PostMapping
    @Operation(summary = "Cria um produtor", description = "Retorna 201 com o recurso criado; 400 se os dados forem inválidos.")
    public ResponseEntity<ProdutorResponse> criar(@Valid @RequestBody ProdutorRequest request) {
        ProdutorResponse response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/v1/produtores/" + response.getId())).body(response);
    }

    @GetMapping
    @Operation(summary = "Lista produtores")
    public List<ProdutorResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulta um produtor pelo id", description = "Retorna 404 se não existir.")
    public ProdutorResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produtor", description = "Retorna 400 se os dados forem inválidos; 404 se não existir.")
    public ProdutorResponse atualizar(@PathVariable Long id, @Valid @RequestBody ProdutorRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui um produtor", description = "Retorna 204 sem corpo; 404 se não existir; 409 se estiver em uso.")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
