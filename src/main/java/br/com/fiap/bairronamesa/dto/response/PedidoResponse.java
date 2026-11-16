package br.com.fiap.bairronamesa.dto.response;

import br.com.fiap.bairronamesa.entity.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResponse {
    private Long id;
    private String nomeMorador;
    private Integer quantidade;
    private String telefone;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private String codigoRetirada;
    private LocalDateTime dataPedido;
    private Long ofertaId;
    private String ofertaProdutoNome;
}
