package br.com.fiap.bairronamesa.service;

import br.com.fiap.bairronamesa.dto.request.ProdutorRequest;
import br.com.fiap.bairronamesa.dto.response.ProdutorResponse;
import br.com.fiap.bairronamesa.entity.Produtor;
import br.com.fiap.bairronamesa.exception.RecursoNaoEncontradoException;
import br.com.fiap.bairronamesa.repository.ProdutorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProdutorService {

    private final ProdutorRepository repository;
    private final ModelMapper mapper;

    @Transactional
    public ProdutorResponse criar(ProdutorRequest request) {
        return toResponse(repository.save(mapper.map(request, Produtor.class)));
    }

    @Transactional(readOnly = true)
    public List<ProdutorResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProdutorResponse buscar(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public ProdutorResponse atualizar(Long id, ProdutorRequest request) {
        Produtor entidade = buscarEntidade(id);
        mapper.map(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    public Produtor buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produtor", id));
    }

    private ProdutorResponse toResponse(Produtor entidade) {
        return mapper.map(entidade, ProdutorResponse.class);
    }
}
