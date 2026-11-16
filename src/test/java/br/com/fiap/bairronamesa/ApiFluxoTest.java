package br.com.fiap.bairronamesa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

// Sobe a aplicação inteira e percorre o fluxo principal: feira -> produtor -> oferta -> pedido
@SpringBootTest
@AutoConfigureMockMvc
class ApiFluxoTest {

    @Autowired
    private MockMvc mvc;

    private ResultActions enviar(org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder req,
            String json) throws Exception {
        return mvc.perform(req.contentType(MediaType.APPLICATION_JSON).content(json));
    }

    @Test
    void fluxoCompleto() throws Exception {
        enviar(post("/api/v1/feiras"), """
                {"nome":"Feira da Praça","endereco":"Rua A, 10","diaSemana":"SABADO","horarioInicio":"08:00"}""")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.horarioInicio").value("08:00:00"));

        enviar(post("/api/v1/produtores"), """
                {"nome":"Seu José","tipo":"AGRICULTOR_FAMILIAR","municipio":"Mogi das Cruzes"}""")
                .andExpect(status().isCreated());

        enviar(post("/api/v1/ofertas"), """
                {"produtoNome":"Alface","precoUnitario":4.50,"quantidadeDisponivel":10,"idFeira":1,"idProdutor":1}""")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.feiraNome").value("Feira da Praça"))
                .andExpect(jsonPath("$.produtorNome").value("Seu José"));

        enviar(post("/api/v1/pedidos"), """
                {"nomeMorador":"Dona Maria","quantidade":3,"idOferta":1}""")
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.valorTotal").value(13.5))
                .andExpect(jsonPath("$.status").value("PENDENTE"))
                .andExpect(jsonPath("$.ofertaProdutoNome").value("Alface"));

        mvc.perform(get("/api/v1/ofertas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidadeDisponivel").value(7));

        // acima do estoque: 400
        enviar(post("/api/v1/pedidos"), """
                {"nomeMorador":"João","quantidade":99,"idOferta":1}""")
                .andExpect(status().isBadRequest());

        // PUT mantém o id e os vínculos
        enviar(put("/api/v1/ofertas/1"), """
                {"produtoNome":"Alface crespa","precoUnitario":5.00,"quantidadeDisponivel":7,"idFeira":1,"idProdutor":1}""")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.produtoNome").value("Alface crespa"));

        // dados inválidos: 400 com a lista de campos
        enviar(post("/api/v1/feiras"), "{}")
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erros.nome").exists());

        // JSON malformado e id não numérico: 400
        enviar(post("/api/v1/feiras"), "{isto não é json").andExpect(status().isBadRequest());
        mvc.perform(get("/api/v1/feiras/abc")).andExpect(status().isBadRequest());

        // inexistente: 404 (inclusive vínculo inexistente)
        mvc.perform(get("/api/v1/feiras/999")).andExpect(status().isNotFound());
        enviar(post("/api/v1/pedidos"), """
                {"nomeMorador":"João","quantidade":1,"idOferta":999}""")
                .andExpect(status().isNotFound());

        // feira em uso por uma oferta: 409
        mvc.perform(delete("/api/v1/feiras/1")).andExpect(status().isConflict());

        // exclusão: 204 e o estoque volta
        mvc.perform(delete("/api/v1/pedidos/1")).andExpect(status().isNoContent());
        mvc.perform(get("/api/v1/ofertas/1")).andExpect(jsonPath("$.quantidadeDisponivel").value(10));
        mvc.perform(get("/api/v1/pedidos")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void raizRedirecionaParaSwaggerEDocsExistem() throws Exception {
        mvc.perform(get("/")).andExpect(status().is3xxRedirection());
        mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/v1/pedidos/{id}']").exists());
    }
}
