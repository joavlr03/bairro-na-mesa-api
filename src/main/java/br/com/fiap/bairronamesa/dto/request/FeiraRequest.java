package br.com.fiap.bairronamesa.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeiraRequest {

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @NotBlank(message = "endereco é obrigatório")
    private String endereco;

    @NotBlank(message = "diaSemana é obrigatório")
    @Schema(example = "SABADO")
    private String diaSemana;

    private String nomePraca;
    private String bairro;

    @Schema(type = "string", example = "08:00")
    private LocalTime horarioInicio;

    @Schema(type = "string", example = "13:00")
    private LocalTime horarioFim;

    private Double latitude;
    private Double longitude;
}
