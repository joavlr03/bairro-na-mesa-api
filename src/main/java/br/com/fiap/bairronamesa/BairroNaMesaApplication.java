package br.com.fiap.bairronamesa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Bairro na Mesa API",
        version = "v1",
        description = "Feiras de agricultores familiares em praças da periferia, com pré-pedido e retirada por código."))
public class BairroNaMesaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BairroNaMesaApplication.class, args);
    }
}
