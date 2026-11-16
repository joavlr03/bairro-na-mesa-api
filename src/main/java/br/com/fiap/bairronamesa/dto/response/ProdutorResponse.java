package br.com.fiap.bairronamesa.dto.response;

import br.com.fiap.bairronamesa.entity.TipoProdutor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutorResponse {
    private Long id;
    private String nome;
    private TipoProdutor tipo;
    private String municipio;
    private String telefone;
    private String email;
}
