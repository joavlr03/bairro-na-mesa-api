package br.com.fiap.bairronamesa.service;

import br.com.fiap.bairronamesa.dto.request.PedidoRequest;
import br.com.fiap.bairronamesa.dto.response.PedidoResponse;
import br.com.fiap.bairronamesa.entity.Oferta;
import br.com.fiap.bairronamesa.entity.Pedido;
import br.com.fiap.bairronamesa.entity.StatusPedido;
import br.com.fiap.bairronamesa.exception.RecursoNaoEncontradoException;
import br.com.fiap.bairronamesa.exception.RegraNegocioException;
import br.com.fiap.bairronamesa.repository.PedidoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final OfertaService ofertaService;
    private final ModelMapper mapper;

    @Transactional
    public PedidoResponse criar(PedidoRequest request) {
        Oferta oferta = ofertaService.buscarEntidade(request.getIdOferta());
        Pedido pedido = mapper.map(request, Pedido.class);
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setDataPedido(LocalDateTime.now());
        // código curto que o morador apresenta na retirada (vira QR code no app)
        pedido.setCodigoRetirada(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        reservar(pedido, oferta);
        return toResponse(repository.save(pedido));
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscar(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public PedidoResponse atualizar(Long id, PedidoRequest request) {
        Pedido pedido = buscarEntidade(id);
        devolverEstoque(pedido);
        StatusPedido status = request.getStatus() != null ? request.getStatus() : pedido.getStatus();
        Oferta oferta = ofertaService.buscarEntidade(request.getIdOferta());
        mapper.map(request, pedido);
        pedido.setStatus(status);
        reservar(pedido, oferta);
        return toResponse(repository.save(pedido));
    }

    @Transactional
    public void excluir(Long id) {
        Pedido pedido = buscarEntidade(id);
        // pedido já retirado não devolve estoque: o alimento saiu da feira
        if (pedido.getStatus() == StatusPedido.PENDENTE) {
            devolverEstoque(pedido);
        }
        repository.delete(pedido);
    }

    private Pedido buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Pedido", id));
    }

    // Vincula o pedido à oferta, calcula o total e baixa o estoque (pedido cancelado não ocupa estoque)
    private void reservar(Pedido pedido, Oferta oferta) {
        if (pedido.getStatus() != StatusPedido.CANCELADO) {
            if (pedido.getQuantidade() > oferta.getQuantidadeDisponivel()) {
                throw new RegraNegocioException("Quantidade solicitada (" + pedido.getQuantidade()
                        + ") é maior que o estoque disponível (" + oferta.getQuantidadeDisponivel() + ").");
            }
            oferta.setQuantidadeDisponivel(oferta.getQuantidadeDisponivel() - pedido.getQuantidade());
        }
        pedido.setOferta(oferta);
        pedido.setValorTotal(oferta.getPrecoUnitario().multiply(BigDecimal.valueOf(pedido.getQuantidade())));
    }

    private void devolverEstoque(Pedido pedido) {
        if (pedido.getStatus() != StatusPedido.CANCELADO) {
            Oferta oferta = pedido.getOferta();
            oferta.setQuantidadeDisponivel(oferta.getQuantidadeDisponivel() + pedido.getQuantidade());
        }
    }

    private PedidoResponse toResponse(Pedido pedido) {
        return mapper.map(pedido, PedidoResponse.class);
    }
}
