package br.com.fiap.bairronamesa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ofertas")
@Getter
@Setter
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String produtoNome;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private Integer quantidadeDisponivel;

    private String unidade;
    private String categoria;
    private LocalDate semanaReferencia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "feira_id", nullable = false)
    private Feira feira;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produtor_id", nullable = false)
    private Produtor produtor;
}
