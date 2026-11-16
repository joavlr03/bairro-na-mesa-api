package br.com.fiap.bairronamesa.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfertaResponse {
    private Long id;
    private String produtoNome;
    private BigDecimal precoUnitario;
    private Integer quantidadeDisponivel;
    private String unidade;
    private String categoria;
    private LocalDate semanaReferencia;
    private Long feiraId;
    private String feiraNome;
    private Long produtorId;
    private String produtorNome;
}
