package com.matela.production.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "block")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Block extends Dimension{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    private Double coutProduction;
    @ManyToOne
    @JoinColumn(name = "block_mere")
    private Block blockMere;
    @Column(name = "date_production", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate dateProduction;

    @Column(name = "volume")
    private Double volumeb;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;




}
