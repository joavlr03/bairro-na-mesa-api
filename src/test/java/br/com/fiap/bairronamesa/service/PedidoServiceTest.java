package br.com.fiap.bairronamesa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.fiap.bairronamesa.config.ModelMapperConfig;
import br.com.fiap.bairronamesa.dto.request.PedidoRequest;
import br.com.fiap.bairronamesa.dto.response.PedidoResponse;
import br.com.fiap.bairronamesa.entity.Oferta;
import br.com.fiap.bairronamesa.entity.Pedido;
import br.com.fiap.bairronamesa.entity.StatusPedido;
import br.com.fiap.bairronamesa.exception.RegraNegocioException;
import br.com.fiap.bairronamesa.repository.PedidoRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PedidoServiceTest {

    private final PedidoRepository repository = mock(PedidoRepository.class);
    private final OfertaService ofertaService = mock(OfertaService.class);
    // ModelMapper real, com a mesma configuração da aplicação, para validar o mapeamento de verdade
    private final PedidoService service =
            new PedidoService(repository, ofertaService, new ModelMapperConfig().modelMapper());

    private Oferta oferta;

    @BeforeEach
    void preparar() {
        oferta = new Oferta();
        oferta.setId(1L);
        oferta.setProdutoNome("Alface");
        oferta.setPrecoUnitario(new BigDecimal("4.50"));
        oferta.setQuantidadeDisponivel(10);
        when(ofertaService.buscarEntidade(1L)).thenReturn(oferta);
        when(repository.save(any())).thenAnswer(chamada -> chamada.getArgument(0));
    }

    private PedidoRequest request(int quantidade) {
        PedidoRequest request = new PedidoRequest();
        request.setNomeMorador("Dona Maria");
        request.setQuantidade(quantidade);
        request.setIdOferta(1L);
        return request;
    }

    @Test
    void criarBaixaEstoqueECalculaTotal() {
        PedidoResponse response = service.criar(request(3));

        assertEquals(7, oferta.getQuantidadeDisponivel());
        assertEquals(0, new BigDecimal("13.50").compareTo(response.getValorTotal()));
        assertEquals(StatusPedido.PENDENTE, response.getStatus());
        assertNotNull(response.getCodigoRetirada());
        assertNull(response.getId()); // idOferta do request não pode virar o id do pedido
        assertEquals(1L, response.getOfertaId());
        assertEquals("Alface", response.getOfertaProdutoNome());
    }

    @Test
    void criarAcimaDoEstoqueERecusado() {
        assertThrows(RegraNegocioException.class, () -> service.criar(request(11)));
        assertEquals(10, oferta.getQuantidadeDisponivel());
    }

    @Test
    void atualizarAjustaEstoqueEMantemIdEDadosGerados() {
        Pedido existente = new Pedido();
        existente.setId(5L);
        existente.setNomeMorador("Dona Maria");
        existente.setQuantidade(3);
        existente.setStatus(StatusPedido.PENDENTE);
        existente.setCodigoRetirada("ABC12345");
        existente.setOferta(oferta);
        oferta.setQuantidadeDisponivel(7);
        when(repository.findById(5L)).thenReturn(Optional.of(existente));

        PedidoResponse response = service.atualizar(5L, request(5));

        assertEquals(5, oferta.getQuantidadeDisponivel());
        assertEquals(5L, response.getId());
        assertEquals("ABC12345", response.getCodigoRetirada());
        assertEquals(StatusPedido.PENDENTE, response.getStatus());
    }

    @Test
    void cancelarDevolveEstoque() {
        Pedido existente = new Pedido();
        existente.setId(5L);
        existente.setQuantidade(3);
        existente.setStatus(StatusPedido.PENDENTE);
        existente.setOferta(oferta);
        oferta.setQuantidadeDisponivel(7);
        when(repository.findById(5L)).thenReturn(Optional.of(existente));
        PedidoRequest request = request(3);
        request.setStatus(StatusPedido.CANCELADO);

        service.atualizar(5L, request);

        assertEquals(10, oferta.getQuantidadeDisponivel());
    }
}
