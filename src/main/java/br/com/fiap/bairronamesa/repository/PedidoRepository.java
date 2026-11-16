package br.com.fiap.bairronamesa.repository;

import br.com.fiap.bairronamesa.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
