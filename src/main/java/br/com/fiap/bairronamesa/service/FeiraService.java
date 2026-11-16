package br.com.fiap.bairronamesa.service;

import br.com.fiap.bairronamesa.dto.request.FeiraRequest;
import br.com.fiap.bairronamesa.dto.response.FeiraResponse;
import br.com.fiap.bairronamesa.entity.Feira;
import br.com.fiap.bairronamesa.exception.RecursoNaoEncontradoException;
import br.com.fiap.bairronamesa.repository.FeiraRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeiraService {

    private final FeiraRepository repository;
    private final ModelMapper mapper;

    @Transactional
    public FeiraResponse criar(FeiraRequest request) {
        return toResponse(repository.save(mapper.map(request, Feira.class)));
    }

    @Transactional(readOnly = true)
    public List<FeiraResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public FeiraResponse buscar(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public FeiraResponse atualizar(Long id, FeiraRequest request) {
        Feira entidade = buscarEntidade(id);
        mapper.map(request, entidade);
        return toResponse(repository.save(entidade));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    public Feira buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Feira", id));
    }

    private FeiraResponse toResponse(Feira entidade) {
        return mapper.map(entidade, FeiraResponse.class);
    }
}
