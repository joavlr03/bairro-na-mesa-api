package br.com.fiap.bairronamesa.repository;

import br.com.fiap.bairronamesa.entity.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
}
