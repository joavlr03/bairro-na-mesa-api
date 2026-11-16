package br.com.fiap.bairronamesa.service;

import br.com.fiap.bairronamesa.dto.request.OfertaRequest;
import br.com.fiap.bairronamesa.dto.response.OfertaResponse;
import br.com.fiap.bairronamesa.entity.Oferta;
import br.com.fiap.bairronamesa.exception.RecursoNaoEncontradoException;
import br.com.fiap.bairronamesa.repository.OfertaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfertaService {

    private final OfertaRepository repository;
    private final FeiraService feiraService;
    private final ProdutorService produtorService;
    private final ModelMapper mapper;

    @Transactional
    public OfertaResponse criar(OfertaRequest request) {
        return salvar(new Oferta(), request);
    }

    @Transactional(readOnly = true)
    public List<OfertaResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OfertaResponse buscar(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public OfertaResponse atualizar(Long id, OfertaRequest request) {
        return salvar(buscarEntidade(id), request);
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    public Oferta buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Oferta", id));
    }

    private OfertaResponse salvar(Oferta oferta, OfertaRequest request) {
        mapper.map(request, oferta);
        // feira e produtor precisam existir: 404 se o id informado não existir
        oferta.setFeira(feiraService.buscarEntidade(request.getIdFeira()));
        oferta.setProdutor(produtorService.buscarEntidade(request.getIdProdutor()));
        return toResponse(repository.save(oferta));
    }

    private OfertaResponse toResponse(Oferta oferta) {
        return mapper.map(oferta, OfertaResponse.class);
    }
}
