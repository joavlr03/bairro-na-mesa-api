package br.com.fiap.bairronamesa.repository;

import br.com.fiap.bairronamesa.entity.Feira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeiraRepository extends JpaRepository<Feira, Long> {
}
