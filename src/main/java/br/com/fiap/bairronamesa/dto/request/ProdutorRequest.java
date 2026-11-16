package br.com.fiap.bairronamesa.dto.request;

import br.com.fiap.bairronamesa.entity.TipoProdutor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutorRequest {

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @NotNull(message = "tipo é obrigatório")
    private TipoProdutor tipo;

    @NotBlank(message = "municipio é obrigatório")
    private String municipio;

    private String telefone;

    @Email(message = "email inválido")
    private String email;
}
