package br.com.fiap.bairronamesa.config;

import br.com.fiap.bairronamesa.dto.response.OfertaResponse;
import br.com.fiap.bairronamesa.dto.response.PedidoResponse;
import br.com.fiap.bairronamesa.entity.Oferta;
import br.com.fiap.bairronamesa.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        // STRICT: impede que campos como "idOferta" do request sobrescrevam o "id" da entidade
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Em STRICT os campos vindos de relacionamentos precisam ser declarados
        mapper.typeMap(Oferta.class, OfertaResponse.class).addMappings(m -> {
            m.map(o -> o.getFeira().getId(), OfertaResponse::setFeiraId);
            m.map(o -> o.getFeira().getNome(), OfertaResponse::setFeiraNome);
            m.map(o -> o.getProdutor().getId(), OfertaResponse::setProdutorId);
            m.map(o -> o.getProdutor().getNome(), OfertaResponse::setProdutorNome);
        });
        mapper.typeMap(Pedido.class, PedidoResponse.class).addMappings(m -> {
            m.map(p -> p.getOferta().getId(), PedidoResponse::setOfertaId);
            m.map(p -> p.getOferta().getProdutoNome(), PedidoResponse::setOfertaProdutoNome);
        });
        return mapper;
    }
}
