package br.com.fiap.bairronamesa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfertaRequest {

    @NotBlank(message = "produtoNome é obrigatório")
    private String produtoNome;

    @NotNull(message = "precoUnitario é obrigatório")
    @Positive(message = "precoUnitario deve ser maior que zero")
    private BigDecimal precoUnitario;

    @NotNull(message = "quantidadeDisponivel é obrigatória")
    @PositiveOrZero(message = "quantidadeDisponivel não pode ser negativa")
    private Integer quantidadeDisponivel;

    private String unidade;
    private String categoria;
    private LocalDate semanaReferencia;

    @NotNull(message = "idFeira é obrigatório")
    private Long idFeira;

    @NotNull(message = "idProdutor é obrigatório")
    private Long idProdutor;
}
