package br.com.fiap.bairronamesa.dto.response;

import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeiraResponse {
    private Long id;
    private String nome;
    private String endereco;
    private String diaSemana;
    private String nomePraca;
    private String bairro;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private Double latitude;
    private Double longitude;
}
