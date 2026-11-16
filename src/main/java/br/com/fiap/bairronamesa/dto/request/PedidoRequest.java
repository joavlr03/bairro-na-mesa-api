package br.com.fiap.bairronamesa.dto.request;

import br.com.fiap.bairronamesa.entity.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoRequest {

    @NotBlank(message = "nomeMorador é obrigatório")
    private String nomeMorador;

    @NotNull(message = "quantidade é obrigatória")
    @Positive(message = "quantidade deve ser maior que zero")
    private Integer quantidade;

    private String telefone;

    @Schema(description = "Ignorado na criação (sempre PENDENTE). No PUT, mantém o atual se omitido.")
    private StatusPedido status;

    @NotNull(message = "idOferta é obrigatório")
    private Long idOferta;
}
