package com.matela.production.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reste")
public class Reste {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reste_id_gen")
    @SequenceGenerator(name = "reste_id_gen", sequenceName = "reste_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "block_id")
    private Block block;

    @Column(name = "longueur", precision = 10, scale = 2)
    private BigDecimal longueur;

    @Column(name = "largeur", precision = 10, scale = 2)
    private BigDecimal largeur;

    @Column(name = "epaisseur", precision = 10, scale = 2)
    private BigDecimal epaisseur;

    @Column(name = "cout_production", precision = 10, scale = 2)
    private BigDecimal coutProduction;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

}